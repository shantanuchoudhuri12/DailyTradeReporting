package com.jpmorgan.cib.tradereporting.dataprovider.report;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jpmorgan.cib.tradereporting.dataprovider.util.GenerateReportHelper;
import com.jpmorgan.cib.tradereporting.dataprovider.util.TradeReportingConstant;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;

/*
 * @author Shantanu Choudhuri
 */

public class TextFileReportCreator implements ReportCreator {

	private static Logger logger = Logger.getLogger("TextFileReportCreator");

	@Override
	public void createReport(Report reportBean) {
		Map<String, Double> buySettlementDateMap = reportBean.getBuySettlementDateMap();
		Map<String, Double> sellSettlementDateMap = reportBean.getSellSettlementDateMap();
		Map<String, Double> buyEntityMap = reportBean.getBuyEntityMap();
		Map<String, Double> sellEntityMap = reportBean.getSellEntityMap();

		if (buySettlementDateMap != null && buySettlementDateMap.size() > 0) {

			logger.debug("****** Total Settlement Amount for Outgoing(Buy) Trades based on settlement date********");

			// Looping map to display the value
			buySettlementDateMap.forEach((k,
					v) -> logger.debug("Settlement Date: " + k + ", TotalAmount: "
							+ new BigDecimal(v, MathContext.DECIMAL64).setScale(2, RoundingMode.DOWN)
							+ TradeReportingConstant.USA_CURRENCY));
		}
		if (sellSettlementDateMap != null && sellSettlementDateMap.size() > 0) {

			logger.debug("****** Settlement Amount for Incoming(Sell) Trades based on settlement date********");

			// Looping map to display the value
			sellSettlementDateMap.forEach((k,
					v) -> logger.debug("Settlement Date: " + k + ", TotalAmount: "
							+ new BigDecimal(v, MathContext.DECIMAL64).setScale(2, RoundingMode.DOWN)
							+ TradeReportingConstant.USA_CURRENCY));
		}
		if (buyEntityMap != null && buyEntityMap.size() > 0) {

			// calling sort method for outgoing entities based on the
			// totaltradeAmount in descending
			buyEntityMap = GenerateReportHelper.sortByValue(buyEntityMap);

			logger.debug(
					"****** Total Settlement Amount for Buy(Outgoing) Trades Based On Entity In Descending Order********");

			// Looping map to display the value
			buyEntityMap.forEach((k,
					v) -> logger.debug("Entity Name: " + k + ", TotalAmount: "
							+ new BigDecimal(v, MathContext.DECIMAL64).setScale(2, RoundingMode.DOWN)
							+ TradeReportingConstant.USA_CURRENCY));
		}

		if (sellEntityMap != null && sellEntityMap.size() > 0) {

			// calling sort method for incoming entities based on the
			// totaltradeAmount in descending
			sellEntityMap = GenerateReportHelper.sortByValue(sellEntityMap);

			logger.debug(
					"****** Total Settlement Amount for Sell(Incoming) Trades Based On Entity In Descending Order********");

			// Looping map to display the value
			sellEntityMap.forEach((k,
					v) -> logger.debug("Entity Name: " + k + ", TotalAmount: "
							+ new BigDecimal(v, MathContext.DECIMAL64).setScale(2, RoundingMode.DOWN)
							+ TradeReportingConstant.USA_CURRENCY));
		}
	}

}
