package com.vz.app.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDAO {
	private Long productId;
	private String email;
	private String first_name;
	private String last_name; 
}
