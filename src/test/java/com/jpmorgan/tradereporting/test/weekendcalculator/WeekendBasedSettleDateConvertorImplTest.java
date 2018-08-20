package com.jpmorgan.tradereporting.test.weekendcalculator;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor.WeekendBasedSettleDateConvertorImpl;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

public class WeekendBasedSettleDateConvertorImplTest {
	
	WeekendBasedSettleDateConvertorImpl weekendDateConverter;
	Trade trade;
	
	@Before
	public void setUp() throws Exception {
//		fileStrategy = new FileStrategy();
//		dataPopulator = new DataPopulator();
		weekendDateConverter = new WeekendBasedSettleDateConvertorImpl();
		trade = new Trade();
		
		
	}
	
	@Test
	public void testForValidDateWithOutWeekend() throws TradeMapperException{
		trade.setSettlementDate("07 Feb 2018");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.settlementDateCalculator(trade);
		assertEquals(trade.getSettlementDate(), tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForFridayWeekendCcy() throws TradeMapperException{
		trade.setSettlementDate("09 Feb 2018");
		trade.setCurrency("AED");
		Trade tradeAfterChange = weekendDateConverter.settlementDateCalculator(trade);
		assertEquals("11 Feb 2018", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendCcy() throws TradeMapperException{
		trade.setSettlementDate("10 Feb 2018");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.settlementDateCalculator(trade);
		assertEquals("12 Feb 2018", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfMonthDateCcy() throws TradeMapperException{
		trade.setSettlementDate("30 Dec 2017");
		trade.setCurrency("GBP");
		Trade tradeAfterChange = weekendDateConverter.settlementDateCalculator(trade);
		assertEquals("01 Jan 2018", tradeAfterChange.getSettlementDate());
	}
	
	@Test
	public void testForSundayWeekendEndOfYearDateCcy() throws TradeMapperException{
		trade.setSettlementDate("31 Dec 2016");
		trade.setCurrency("USD");
		Trade tradeAfterChange = weekendDateConverter.settlementDateCalculator(trade);
		assertEquals("02 Jan 2017", tradeAfterChange.getSettlementDate());
	}
	
	//Negative test cases
	
	@Test
	public void testForDateFormatException() {
		try{
			trade.setSettlementDate("16 Feb 18");
			trade.setCurrency("GBP");
			weekendDateConverter.settlementDateCalculator(trade);
		}catch (TradeMapperException e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForInvalidDateException() {
		try{
			trade.setSettlementDate("2018-02-07");
			trade.setCurrency("GBP");
			weekendDateConverter.settlementDateCalculator(trade);
		}catch (TradeMapperException e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForDateWithoutYear() {
		try{
			trade.setSettlementDate("04 Feb");
			trade.setCurrency("GBP");
			weekendDateConverter.settlementDateCalculator(trade);
		}catch (TradeMapperException e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@Test
	public void testForEmptyDate() {
		try{
			trade.setSettlementDate("");
			trade.setCurrency("GBP");
			weekendDateConverter.settlementDateCalculator(trade);
		}catch (TradeMapperException e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
		
	}
	
	@After
	public void tearDown() throws Exception {
		trade = null;
		weekendDateConverter = null;
	}

}
