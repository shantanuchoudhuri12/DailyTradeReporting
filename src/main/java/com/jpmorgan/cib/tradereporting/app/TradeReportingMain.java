package com.jpmorgan.cib.tradereporting.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.jpmorgan.cib.tradereporting.dataprovider.dataformatter.DataAdapter;
import com.jpmorgan.cib.tradereporting.dataprovider.report.FileReport;
import com.jpmorgan.cib.tradereporting.dataprovider.report.ReportBridge;
import com.jpmorgan.cib.tradereporting.dataprovider.report.ReportType;
import com.jpmorgan.cib.tradereporting.dataprovider.report.TextFileReportCreator;
import com.jpmorgan.cib.tradereporting.dataprovider.strategy.Context;
import com.jpmorgan.cib.tradereporting.dataprovider.strategy.impl.FileStrategy;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

/*
 * @author Shantanu Choudhuri
 */

public class TradeReportingMain {

	private static Logger logger = Logger.getLogger("TradeReportingMain");

	public static void main(String[] args) throws TradeMapperException {
		logger.debug("**************************************************************");
		logger.debug("**********Trade reporting app started ************************");
		logger.debug("**************************************************************");
		/*
		 * Reads file based on file strategy and returns list of trades based
		 * upon applicable settlement date and trade cost based on per unit cost
		 * * total unit * agreed fx
		 */
		Context context = new Context(new FileStrategy());
		List<Trade> tradeList = context.executeStrategy();

		if (tradeList != null && tradeList.size() > 0) {

			// Calling method to sort and populate
			DataAdapter dataAdapter = new DataAdapter();
			Report reportBean = dataAdapter.formatData(tradeList, ReportType.TEXTFILE.toString());

			// Calling display method to to show the final report to user
			ReportBridge reportBridge = new FileReport(reportBean, new TextFileReportCreator());
			reportBridge.createReport();

		} else {
			logger.debug("Issue While Processing the txt file");
		}

		logger.debug("********** Report has been created at logs/tradereporting.log************************");
	}

}
