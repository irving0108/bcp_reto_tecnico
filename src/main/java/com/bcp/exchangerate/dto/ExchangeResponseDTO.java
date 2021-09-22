package com.bcp.exchangerate.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private ExchangeDTO result;
	private BigDecimal exchangerate;
	private BigDecimal amountExchangeRate;
}
