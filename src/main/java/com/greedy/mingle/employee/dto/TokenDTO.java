package com.greedy.mingle.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

	private String grantType;
	private String empName;
	private String accessToken;
	private Long accessTokenExpiresIn;

}
