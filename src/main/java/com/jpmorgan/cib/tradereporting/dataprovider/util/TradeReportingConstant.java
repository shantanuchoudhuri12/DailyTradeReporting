package com.jpmorgan.cib.tradereporting.dataprovider.util;

public interface TradeReportingConstant {
	String DELIMITER = "\\|";
	String USA_CURRENCY = " USD";

	String TRADE_DATE_FORMAT = "dd MMM yyyy";

	String FRIDAY_WEEKEND = "FRIDAY";
	String SATURDAY_WEEKEND = "SATURDAY";
	String SUNDAY_WEEKEND = "SUNDAY";

	String BUY = "B";
	String SELL = "S";

	// Set the currency whose weekend is Friday and Saturday assuming currently
	// only 2 countries
	String[] FRI_SAT_WEEKEND_COUNTRY_CCY = new String[] { "AED", "SAR" };
}
