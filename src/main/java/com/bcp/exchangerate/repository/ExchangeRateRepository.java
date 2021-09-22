package com.bcp.exchangerate.repository;

import org.springframework.stereotype.Repository;

import com.bcp.exchangerate.entity.ExchangeRate;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long>{
	Optional<ExchangeRate> findByCurrency(String currency);	
}
