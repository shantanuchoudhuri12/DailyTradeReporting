package com.jpmorgan.cib.tradereporting.dataprovider.dataformatter;

import java.util.List;

import com.jpmorgan.cib.tradereporting.dataprovider.report.ReportType;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;

/*
 * @author Shantanu Choudhuri
 */

public class DataAdapter implements DataFormatter {

	public AdvanceDataFormatter dataFormatter;

	@Override
	public Report formatData(List<Trade> tradeList, String formatterType) {
		if (formatterType.equals(ReportType.TEXTFILE.toString())) {
			dataFormatter = new TextDataFormatter();
			return dataFormatter.textDataFormatter(tradeList);
		}

		return null;
	}

}
