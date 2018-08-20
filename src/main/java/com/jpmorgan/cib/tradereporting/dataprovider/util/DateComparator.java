package com.jpmorgan.cib.tradereporting.dataprovider.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
/*
 * @author Shantanu Choudhuri
 */

public class DateComparator implements Comparator<String> {
	public int compare(String date1, String date2) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TradeReportingConstant.TRADE_DATE_FORMAT);
		// converting the settlement date to localDate object
		LocalDate d1 = LocalDate.parse(date1, formatter);
		LocalDate d2 = LocalDate.parse(date2, formatter);

		return d1.compareTo(d2);
	}
}