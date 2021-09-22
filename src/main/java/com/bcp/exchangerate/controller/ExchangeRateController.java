package com.bcp.exchangerate.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.exchangerate.dto.ExchangeRequestDTO;
import com.bcp.exchangerate.dto.ExchangeResponseDTO;
import com.bcp.exchangerate.entity.ExchangeRate;
import com.bcp.exchangerate.error.ResourceNotFoundException;
import com.bcp.exchangerate.service.ExchangeRateService;

@RestController
@RequestMapping("/api/exchangerate")
public class ExchangeRateController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@GetMapping("/convert")
	public ResponseEntity<ExchangeResponseDTO> getConvert(
			@RequestParam(value = "monto") BigDecimal amount,
			@RequestParam(value = "monedaorigen") String currencyfrom,
			@RequestParam(value = "monedadestino") String currencyto) throws ResourceNotFoundException {
		logger.info("{}-{}-{}", "INFO", "ExchangeRateController.getConvert", "Inicio");
		ExchangeResponseDTO exchange = exchangeRateService.getConvert(currencyfrom, currencyto, amount);
		ResponseEntity<ExchangeResponseDTO> responseEntity = new ResponseEntity<>(exchange, HttpStatus.OK);
		return responseEntity;
	}
	
	@PatchMapping(value = "/{moneda}")
	public ResponseEntity<ExchangeRate> updateExchangeRate(@PathVariable("moneda") String currency,
			@Valid @RequestBody ExchangeRequestDTO exchangeRateRequestDTO) throws ResourceNotFoundException {
		logger.info("{}-{}-{}", "INFO", "ExchangeRateController.updateExchangeRate", "Inicio");
		logger.info("{}-{}-{}", "INFO", exchangeRateRequestDTO.toString());
		return ResponseEntity.ok(exchangeRateService.updateExchangeRate(currency, exchangeRateRequestDTO));
	}
	
	@GetMapping(value = "/list")
	public ResponseEntity<List<ExchangeRate>> getCurrencys() throws ResourceNotFoundException {
		logger.info("{}-{}-{}", "INFO", "ExchangeRateController.getCurrencys", "Inicio");
		return ResponseEntity.ok(exchangeRateService.getListCurrencys());
	}
	
	@PostMapping(value = "/add/currency")
	public ResponseEntity<ExchangeRate> createCurrency(@Valid @RequestBody ExchangeRequestDTO exchangeRateRequestDTO){
		logger.info("{}-{}-{}", "INFO", "ExchangeRateController.createCurrency", "Inicio");
		logger.info("{}-{}-{}", "INFO", exchangeRateRequestDTO.toString());
		return ResponseEntity.ok(exchangeRateService.createCurrency(exchangeRateRequestDTO));
	}
}
