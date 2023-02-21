package com.ex.backend.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {
	private String name;
	private String memberId;
	private String address;
	private String payment;
	private String cardNumber;
	private String items;

}
