package com.greedy.mingle.employee.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.greedy.mingle.employee.dto.MailDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendEmailService {

   private final EmployeeRepository employeeRepository;
   private final JavaMailSender mailSender;
   private final PasswordEncoder passwordEncoder;
   private static final String FROM_ADDRESS = "qwe030223@naver.com";
   private final TemplateEngine templateEngine;

   public SendEmailService(EmployeeRepository employeeRepository, JavaMailSender mailSender,
                           PasswordEncoder passwordEncoder, TemplateEngine templateEngine) {
      this.employeeRepository = employeeRepository;
      this.mailSender = mailSender;
      this.passwordEncoder = passwordEncoder;
      this.templateEngine = templateEngine;
   }
   
   @Transactional
   public MailDTO createMailAndChangePassword(String empId, String empEmail, String empName) {
      log.info("[SendEmailService] empName : {}", empName);
      
      String str = getTempPassword();
      MailDTO dto = new MailDTO();
      dto.setAddress(empEmail);
      dto.setTitle(empName + "님의 Mingle 임시비밀번호 안내 이메일 입니다.");
      
      String pwd = passwordEncoder.encode(str);
      Employee employee = employeeRepository.findByEmpId(empId)
               .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
      employee.setEmpPwd(pwd);
      
      try {
         String emailContent = generateEmailContent(empName, str);
         dto.setMessage(emailContent);
      } catch (Exception e) {
         log.error("Failed to generate email content: {}", e.getMessage());
      }
      
      return dto;
   }

   public String getTempPassword() {
      char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

      StringBuilder str = new StringBuilder();

      int idx = 0;
      for (int i = 0; i < 10; i++) {
         idx = (int) (charSet.length * Math.random());
         str.append(charSet[idx]);
      }
      
      return str.toString();
   }
   
   public String generateEmailContent(String name, String password) {
      Context context = new Context();
      context.setVariable("name", name);
      context.setVariable("password", password);
      
      return templateEngine.process("email-template", context);
   }
   
   public void mailSend(MailDTO mailDTO) {
      log.info("임시 비밀번호 발급되었습니다.");
      MimeMessage message = mailSender.createMimeMessage();
      
      try {
         MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
               "UTF-8");
         helper.setTo(mailDTO.getAddress());
         helper.setFrom(SendEmailService.FROM_ADDRESS);
         helper.setSubject(mailDTO.getTitle());
         helper.setText(mailDTO.getMessage(), true);
         
         mailSender.send(message);
      } catch (MessagingException e) {
         log.error("Failed to send email: {}", e.getMessage());
      }
   }
}
