package com.bcp.exchangerate.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExchangeRequestDTO implements Serializable{
	
	private static final long serialVersionUID=1L;
	private BigDecimal value;
	@Size(min = 3, max = 5)
	private String currency;
}
