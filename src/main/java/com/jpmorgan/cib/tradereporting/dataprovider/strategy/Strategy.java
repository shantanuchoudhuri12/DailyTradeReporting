package com.jpmorgan.cib.tradereporting.dataprovider.strategy;

import java.util.List;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

public interface Strategy {

	List<Trade> retrieveTrade() throws TradeMapperException;

}
