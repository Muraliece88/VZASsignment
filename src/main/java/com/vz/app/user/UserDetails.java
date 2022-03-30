package com.vz.app.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {
	private Long id;
	private String email;
	private String first_name;
	private String last_name;
	private String avatar;
	
}

