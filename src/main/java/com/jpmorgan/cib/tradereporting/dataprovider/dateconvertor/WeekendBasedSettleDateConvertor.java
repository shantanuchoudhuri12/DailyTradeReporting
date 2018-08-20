package com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

public interface WeekendBasedSettleDateConvertor {

	Trade settlementDateCalculator(Trade trade) throws TradeMapperException;

}
