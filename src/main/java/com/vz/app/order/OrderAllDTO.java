package com.vz.app.order;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class OrderAllDTO {
	private Long orderID;
	private String email;
	private String first_name;
	private String last_name;
	private Long productID;
}
