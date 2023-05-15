/*
 * package com.greedy.mingle.employee.service;
 * 
 * import java.util.stream.Collectors;
 * 
 * 
 * 
 * import org.hibernate.Hibernate; import org.modelmapper.ModelMapper; import
 * org.springframework.security.core.authority.SimpleGrantedAuthority; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional; import
 * org.springframework.transaction.support.TransactionSynchronizationManager;
 * 
 * import com.greedy.mingle.employee.dto.EmployeeDTO; import
 * com.greedy.mingle.employee.entity.Employee; import
 * com.greedy.mingle.employee.repository.EmployeeRepository;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Service
 * 
 * @Slf4j
 * 
 * @Transactional(readOnly = true) // 메서드에 트랜잭션 설정 추가 public class
 * CustomUserDetailService implements UserDetailsService{
 * 
 * private final EmployeeRepository employeeRepository; private final
 * ModelMapper modelMapper;
 * 
 * public CustomUserDetailService (EmployeeRepository employeeRepository,
 * ModelMapper modelMapper) { this.employeeRepository = employeeRepository;
 * this.modelMapper = modelMapper; }
 * 
 * @Override public UserDetails loadUserByUsername(String userId) throws
 * UsernameNotFoundException {
 * 
 * log.info("[customUserDetailService] loadUserByUsername start =======");
 * log.info("[customUserDetailService] userId : {}", userId);
 * 
 * if (!TransactionSynchronizationManager.isActualTransactionActive()) { throw
 * new RuntimeException("No active transaction found"); }
 * 
 * return employeeRepository.findByEmpCode(userId) .map(user ->
 * addAuthorities(user)) .orElseThrow(() -> new RuntimeException(userId +
 * "를 찾을 수 없습니다."));
 * 
 * 
 * 
 * }
 * 
 * private EmployeeDTO addAuthorities(Employee employee) {
 * 
 * Hibernate.initialize(employee.getEmpRole());
 * 
 * EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
 * employeeDTO.setAuthorities(employee.getEmpRole().stream() .map(role -> new
 * SimpleGrantedAuthority(role.getAuth().getAuthName()))
 * .collect(Collectors.toList()));
 * 
 * return employeeDTO; }
 * 
 * 
 * 
 * }
 */