package com.bcp.exchangerate.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private BigDecimal amount;
	private String currencyfrom;
	private String currencyto;
}
