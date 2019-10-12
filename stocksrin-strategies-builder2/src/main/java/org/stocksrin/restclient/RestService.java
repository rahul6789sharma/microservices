package org.stocksrin.restclient;

import java.util.SortedSet;

import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.strategy.db.model.DerivativeType;

public interface RestService {

	public SortedSet<String> getAllExpiry() throws Exception;

	public OptionModles getOptionModel(String expiry) throws Exception;

	public Double getFuturePrice() throws Exception;

	public Double getSpotPrice() throws Exception;

	public Double getOptionLtp(String expiry, double strike, DerivativeType derivativeType) throws Exception;

	public Double getIV(String expiry, double strike, OptionType optionType) throws Exception;

	public String getLastDataUpdated(String expiry) throws Exception;
}
