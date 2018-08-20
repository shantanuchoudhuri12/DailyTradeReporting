package com.jpmorgan.cib.tradereporting.dataprovider.strategy;

import java.util.List;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

public class Context {
	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public List<Trade> executeStrategy() throws TradeMapperException {
		return strategy.retrieveTrade();
	}
}
