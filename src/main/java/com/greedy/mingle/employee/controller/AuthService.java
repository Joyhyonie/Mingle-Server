package com.greedy.mingle.employee.controller;

import java.util.Optional;

import javax.transaction.Transactional;


import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.mingle.configuration.exception.DuplicatedUserEmailException;
import com.greedy.mingle.configuration.exception.IdsearchFailedException;
import com.greedy.mingle.configuration.exception.LoginFailedException;
import com.greedy.mingle.configuration.exception.UserNotFoundException;
import com.greedy.mingle.employee.dto.EmployeeDTO;
import com.greedy.mingle.employee.dto.TokenDTO;
import com.greedy.mingle.employee.entity.Employee;
import com.greedy.mingle.employee.entity.EmployeeRole;
import com.greedy.mingle.employee.jwt.TokenProvider;
import com.greedy.mingle.employee.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider; 
	
	public AuthService(EmployeeRepository employeeRepository, ModelMapper modelMapper,PasswordEncoder passwordEncoder,TokenProvider tokenProvider) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider =tokenProvider;
		
		
		   modelMapper.createTypeMap(Employee.class, EmployeeDTO.class)
           .addMappings(mapper -> mapper.skip(EmployeeDTO::setEmpRole));
		   
		   	this.modelMapper = modelMapper;
	}
	/*
	 * @Transactional public void signup(EmployeeDto employeeDto) {
	 * 
	 * log.info("[AuthService] sign start ==========================");
	 * log.info("[AuthService] memberDto : {}", employeeDto);
	 * 
	 * 이메일 중복 시 가입 불가 처리
	 * if(employeeRepository.findByEmpEmail(employeeDto.getEmpEmail()) !=null) {
	 * log.info("[AuthService] 이메일이 중복 됩니다. =========================="); throw new
	 * DuplicatedUserEmailException("이메일이 중복 됩니다."); }
	 * 
	 * log.info("[AuthService] sign end ==========================");
	 * 
	 * }
	 */
	
	public Object login(EmployeeDTO employeeDto) {
	  log.info("[AuthService] login start ==========================");
	  log.info("[AuthService] employeeDto : {}", employeeDto);
	  
	  // 1, 아이디로 DB에서 해당 유저가 있는지 조회
	  Employee employee = employeeRepository.findByEmpId(employeeDto.getEmpId()) 
			  .orElseThrow(()-> new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다.")); 
	  
	  // 2. 비밀번호 매칭 확인	  
	  if(!passwordEncoder.matches(employeeDto.getEmpPwd(), employee.getEmpPwd())) {
	  throw new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다.");
	  }
	  // 토큰 발급
	  TokenDTO tokenDto = tokenProvider.generateTokenDto(modelMapper.map(employee, EmployeeDTO.class));
	  log.info("[AuthService] tokenDto : {}", tokenDto);
	  
	  log.info("[AuthService] login end ==========================");
	   return tokenDto;
	
	  }


	

	   /* 아이디 찾기 */
	   public EmployeeDTO idSearch(EmployeeDTO employeeDTO) {
	      
	      Employee employee = employeeRepository.findByEmpNameAndEmpEmail(employeeDTO.getEmpName(), employeeDTO.getEmpEmail())
	            .orElseThrow(() -> new IdsearchFailedException("입력하신 정보와 일치하는 아이디가 존재하지 않습니다."));
	      
	            
	      return modelMapper.map(employee, EmployeeDTO.class);
	   }

	   /* 비밀번호 찾기 */
	   public EmployeeDTO findById(EmployeeDTO employeeDTO) {

	      Employee employee = employeeRepository.findByEmpId(employeeDTO.getEmpId())
	            .orElseThrow(() -> new UserNotFoundException("해당 아이디와 일치하는 사용자가 없습니다."));

	      if (employee.getEmpEmail().equals(employeeDTO.getEmpEmail())) {
	         return modelMapper.map(employee, EmployeeDTO.class);
	      } else {
	         return null;
	      }

	   }

	   public void updateEmployee(EmployeeDTO existingEmployee) {
		    try {
		        Employee employee = employeeRepository.findByEmpId(existingEmployee.getEmpId())
		                .orElseThrow(() -> new UserNotFoundException("해당 아이디와 일치하는 사용자가 없습니다."));

		        employee.setEmpPwd(existingEmployee.getEmpPwd());

		        employeeRepository.save(employee);

		        System.out.println("직원 정보가 업데이트되었습니다.");
		    } catch (Exception e) {
		        System.out.println("직원 정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
		    }
		}
	public EmployeeDTO findByEmpId(String empId) {
		
		Optional<Employee> employeeOptional = employeeRepository.findByEmpId(empId);
		    if (employeeOptional.isPresent()) {
		        Employee employee = employeeOptional.get();
		        return modelMapper.map(employee, EmployeeDTO.class);
		    } else {
		        return null;
		    }
		
	}


	
	   
}