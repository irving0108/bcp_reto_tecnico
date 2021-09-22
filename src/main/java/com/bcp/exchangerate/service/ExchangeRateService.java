package com.bcp.exchangerate.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.exchangerate.dto.ExchangeDTO;
import com.bcp.exchangerate.dto.ExchangeRequestDTO;
import com.bcp.exchangerate.dto.ExchangeResponseDTO;
import com.bcp.exchangerate.entity.ExchangeRate;
import com.bcp.exchangerate.error.ResourceNotFoundException;
import com.bcp.exchangerate.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeRateRepository exchangeraterepository;
	
	public ExchangeResponseDTO getConvert(String currencyfrom, String currencyto, BigDecimal amount) throws ResourceNotFoundException {
		logger.info("{}-{}-{}", "INFO", "ExchangeRateService.getConvert", "Inicio");
		logger.info("{}-{}-{}", amount, currencyfrom, currencyto);
		ExchangeDTO temp = new ExchangeDTO();
		temp.setAmount(amount);
		temp.setCurrencyfrom(currencyfrom);
		temp.setCurrencyto(currencyto);
		BigDecimal excRate = BigDecimal.ONE;
		BigDecimal excRateAmount = BigDecimal.ONE;
		if(currencyfrom.equalsIgnoreCase("PEN")) {
			ExchangeRate exchangeRate = exchangeraterepository.findByCurrency(currencyto).orElseThrow(() -> new ResourceNotFoundException("No se encontró el tipo de moneda:: " + currencyto));
			excRate = exchangeRate.getRate();
			excRateAmount = (excRate.multiply(amount)).setScale(2, RoundingMode.HALF_EVEN);
		} else {
			ExchangeRate exchangeRate = exchangeraterepository.findByCurrency(currencyfrom).orElseThrow(() -> new ResourceNotFoundException("No se encontró el tipo de moneda:: " + currencyfrom));
			excRate = exchangeRate.getRate();
			excRateAmount = amount.divide(excRate, 2, RoundingMode.HALF_EVEN);
			if(!currencyto.equalsIgnoreCase("PEN")) {
				ExchangeRate exchangeRateTmp = exchangeraterepository.findByCurrency(currencyto).orElseThrow(() -> new ResourceNotFoundException("No se encontró el tipo de moneda:: " + currencyto));
				excRate = exchangeRateTmp.getRate();
				excRateAmount = (excRate.multiply(excRateAmount)).setScale(2, RoundingMode.HALF_EVEN);
			}
		}
		ExchangeResponseDTO result = new ExchangeResponseDTO();
		result.setResult(temp);
		result.setAmountExchangeRate(excRateAmount);
		result.setExchangerate(excRate);
		return result;
	}
	
	public ExchangeRate updateExchangeRate(String currency, ExchangeRequestDTO exchangeRateRequestDTO) throws ResourceNotFoundException {
		ExchangeRate exchangeRate = exchangeraterepository.findByCurrency(currency).orElseThrow(() -> new ResourceNotFoundException("No se encontró el tipo de moneda:: " + currency));
		exchangeRate.setRate(exchangeRateRequestDTO.getValue());
		return exchangeraterepository.save(exchangeRate);
	}
	
	public List<ExchangeRate> getListCurrencys(){
		List<ExchangeRate> exchangeRate = new ArrayList<>();
		exchangeraterepository.findAll().iterator().forEachRemaining(exchangeRate::add);
		return exchangeRate;
	}
	
	public ExchangeRate createCurrency(ExchangeRequestDTO exchangeRateRequestDTO){
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setRate(exchangeRateRequestDTO.getValue());
		exchangeRate.setCurrency(exchangeRateRequestDTO.getCurrency());
		exchangeRate.setDate(new Date());
		return exchangeraterepository.save(exchangeRate);
	}
}
