package com.greedy.mingle.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.greedy.mingle.employee.jwt.JwtAccessDeniedHandler;
import com.greedy.mingle.employee.jwt.JwtAuthenticationEntryPoint;
import com.greedy.mingle.employee.jwt.JwtFilter;



@EnableWebSecurity
public class SecurityConfig {
	
	// 인증 실패 핸들러
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	private final JwtFilter jwtFilter;
	
	public SecurityConfig (JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler,
			JwtFilter jwtFilter) {
		
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtFilter = jwtFilter;
	}
	
	
	// 외부에서 이미지 파일에 접근 가능 하도록 설정
		@Bean
		public WebSecurityCustomizer configure() {
			return (web) -> web.ignoring().antMatchers("/profileImgs/**");
		}
	
	// 비밀번호 암호화를 위한 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 return http
		         // CSRF 설정 Disable
		         .csrf()
		         	.disable()
		         // exception handling 설정 추가
		         .exceptionHandling()
		         	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		         	.accessDeniedHandler(jwtAccessDeniedHandler)
		         .and()
		         // 시큐리티는 기본적으로 세션을 사용하지만 API 서버에선 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
		         .sessionManagement()
		             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		         .and()
		         	 // 요청에 대한 권한 체크
		             .authorizeRequests()
		             /* 클라이언트가 외부 도메인을 요청하는 경우 웹 브라우저에서 자체적으로 사전 요청(preflight)이 일어남 
		              * 이 때 OPTIONS 메서드로 서버에 사전 요청을 보내 요청 권한이 있는지 확인 */
		             .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		             .antMatchers("/auth/**").permitAll()
		             .antMatchers("/attendance-employee/**", 
		            		 	  "/leave-doc-applied/**",
		            		 	  "/certi-doc-applied/**",
		            		 	  "/lecture-student-admin/**",
		            		 	  "/lecture-regist-admin/**",
		            		 	  "/management-employee/**",
		            		 	  "/regist-employee/**",
		            		 	  "/modify-employee/**",		            		 	  
		            		 	  "/search-employee/**",
		            		 	  "/management-student/**",
		            		 	  "/regist-student/**",
		            		 	  "/modify-student/**",
		            		 	  "/search-student/**",
		            		 	  "/schedule-academic/**",
		            		 	  "/subject/**").hasRole("ADMIN")
		             .antMatchers("/lecture-student-prof/**",
		            		 	  "/lecture-regist-prof/**").hasRole("PROF")
		             .antMatchers(HttpMethod.GET, "/employee/putmypage/**").permitAll()
		         .and()
		         	.cors()
		         .and()
		         	.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		        
		         	.build();
		 
	}
	
	/* CORS(cross-origin-resource-sharing) : 교차 출처 자원 공유 
	 * 예전에는 자원 저장 서버와 웹 페이지가 하나의 서버에서 만들어졌기 때문에 해당 서버의 자원을 해당 도메인에서만 요청함
	 * 보안상 웹 브라우저는 다른 도메인에서 서버의 자원을 요청할 경우 막아 놓음
	 * 점점 자원과 웹 페이지를 분리하는 경우, 다른 서버의 자원을 요청하는 경우가 많아지면서 웹 브라우저는 외부 도메인과 통신하기 위한
	 * 표준인 CORS를 만듦
	 * 기본적으로 서버에서 클라이언트를 대상으로 리소스의 허용 여부를 결정함.
	 * */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        // 로컬 React에서 오는 요청은 CORS 허용해준다.
//        configuration.setAllowedOrigins(Arrays.asList("http://3.39.60.205"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-Type", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}