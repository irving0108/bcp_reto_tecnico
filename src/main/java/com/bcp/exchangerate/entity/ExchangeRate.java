package com.bcp.exchangerate.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXCHANGE_RATE", uniqueConstraints = @UniqueConstraint(columnNames = { "currency", "date" }))

@Getter
@Setter
public class ExchangeRate implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EXCH_RATE")
	private Long id;

	@Column(name = "CURRENCY", nullable = false)
	private String currency;

	@Column(name = "RATE", nullable = false)
	private BigDecimal rate;

	@Column(name = "DATE", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public ExchangeRate() {

	}
}
