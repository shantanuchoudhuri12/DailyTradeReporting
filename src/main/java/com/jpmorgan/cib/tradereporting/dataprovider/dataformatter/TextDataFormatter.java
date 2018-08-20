package com.jpmorgan.cib.tradereporting.dataprovider.dataformatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.jpmorgan.cib.tradereporting.dataprovider.util.DateComparator;
import com.jpmorgan.cib.tradereporting.dataprovider.util.GenerateReportHelper;
import com.jpmorgan.cib.tradereporting.dataprovider.util.TradeReportingConstant;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;

/*
 * @author Shantanu Choudhuri
 */

public class TextDataFormatter implements AdvanceDataFormatter {

	private static Logger logger = Logger.getLogger("TextDataFormatter");

	@Override
	public Report textDataFormatter(List<Trade> tradeList) {
		Report reportBean = new Report();

		// Using TreeMap to sort settlement date which is the key.
		Map<String, Double> buySettlementDateMap = new TreeMap<>(new DateComparator());
		Map<String, Double> sellSettlementDateMap = new TreeMap<>(new DateComparator());

		// Using HashMap and then we can sort based on value in the map using
		// comparator
		Map<String, Double> buyEntityMap = new HashMap<>();
		Map<String, Double> sellEntityMap = new HashMap<>();

		// Loop through tradeList
		for (Trade trade : tradeList) {

			String entity = trade.getEntity();
			String settlDate = trade.getSettlementDate();
			Double tradeAmount = trade.getTotalTradeAmount();
			Double entityBasedAmt = trade.getTotalTradeAmount();

			// if total trade amount is 0 or negative value then skipping that
			// particular trade
			if (tradeAmount <= 0) {
				continue;
			}

			// Check if its outgoing(Buy) or Incoming(sell) trade
			if (trade.getInstruction().equalsIgnoreCase(TradeReportingConstant.BUY)) {

				// Populate outgoing totalAmount for each settlement date
				buySettlementDateMap = GenerateReportHelper.populateMapDetails(settlDate, tradeAmount,
						buySettlementDateMap);

				// Populate outgoing totalAmount for each entity
				buyEntityMap = GenerateReportHelper.populateMapDetails(entity, entityBasedAmt, buyEntityMap);

			} else if (trade.getInstruction().equalsIgnoreCase(TradeReportingConstant.SELL)) {

				// Populate incoming totalAmount for each settlement date
				sellSettlementDateMap = GenerateReportHelper.populateMapDetails(settlDate, tradeAmount,
						sellSettlementDateMap);

				// Populate incoming totalAmount for each entity
				sellEntityMap = GenerateReportHelper.populateMapDetails(entity, entityBasedAmt, sellEntityMap);
			} else {
				logger.debug("Instruction is not in proper format");
				logger.debug(trade.getInstruction());
			}
		}

		// Setting the values to report bean
		reportBean.setBuySettlementDateMap(buySettlementDateMap);
		reportBean.setSellSettlementDateMap(sellSettlementDateMap);
		reportBean.setBuyEntityMap(buyEntityMap);
		reportBean.setSellEntityMap(sellEntityMap);

		// Returning the report bean object
		return reportBean;
	}

}
