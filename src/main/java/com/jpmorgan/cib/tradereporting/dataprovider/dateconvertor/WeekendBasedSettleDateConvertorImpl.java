package com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import com.jpmorgan.cib.tradereporting.dataprovider.util.TradeReportingConstant;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

/*
 * @author Shantanu Choudhuri
 */

public class WeekendBasedSettleDateConvertorImpl implements WeekendBasedSettleDateConvertor {

	@Override
	public Trade settlementDateCalculator(Trade trade) throws TradeMapperException {
		try {
			// Creating the formatter based on the settlement date format type
			// in the input file
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

			// converting the settlement date to localDate object
			LocalDate date = LocalDate.parse(trade.getSettlementDate(), formatter);

			// Calling the method to check if date falls on the weekend or not
			while (!isWorkingDay(date, trade.getCurrency())) {

				// Increment the date by 1 if it's falls on the weekend and
				// check again
				date = date.plusDays(1);
			}
			// Converting and setting the settlement date object after the
			// weekend check is done
			trade.setSettlementDate(date.format(formatter));

		} catch (DateTimeParseException ex) {
			throw new TradeMapperException("Error While Parsing Settlement Date", ex);
		}
		// Returning trade object
		return trade;
	}

	/*
	 * 
	 * @param settlementDate
	 * 
	 * @param currency
	 * 
	 * @return boolean
	 */
	private boolean isWorkingDay(LocalDate settlementDate, String currency) {

		// Converting arrays to List
		List<String> friSatWeekEndList = Arrays.asList(TradeReportingConstant.FRI_SAT_WEEKEND_COUNTRY_CCY);

		// Fetch the day of the week from the settlement date object
		String dayOFWeek = settlementDate.getDayOfWeek().name();

		// Check if it falls on the Friday and Saturday weekend country list
		if (friSatWeekEndList.contains(currency)) {
			if ((dayOFWeek.equalsIgnoreCase(TradeReportingConstant.FRIDAY_WEEKEND))
					|| (dayOFWeek.equalsIgnoreCase(TradeReportingConstant.SATURDAY_WEEKEND))) {
				return false;
			}
		} else {
			if ((dayOFWeek.equalsIgnoreCase(TradeReportingConstant.SATURDAY_WEEKEND))
					|| (dayOFWeek.equalsIgnoreCase(TradeReportingConstant.SUNDAY_WEEKEND))) {
				return false;
			}
		}
		return true;
	}

}
