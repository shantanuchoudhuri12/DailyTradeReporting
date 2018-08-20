package com.jpmorgan.tradereporting.test.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor.WeekendBasedSettleDateConvertor;
import com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor.WeekendBasedSettleDateConvertorImpl;
import com.jpmorgan.cib.tradereporting.dataprovider.strategy.impl.DataPopulator;
import com.jpmorgan.cib.tradereporting.dataprovider.strategy.impl.FileStrategy;
import com.jpmorgan.cib.tradereporting.dataprovider.util.TradeReportingConstant;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;


public class TextFileProcesserImplTest {

	FileStrategy fileStrategy;
	DataPopulator dataPopulator;
	WeekendBasedSettleDateConvertor weekendDateConverter ;
	
	
	@Before
	public void setUp() throws Exception {
		
		fileStrategy = new FileStrategy();
		dataPopulator = new DataPopulator();
		weekendDateConverter = new WeekendBasedSettleDateConvertorImpl();
	}

	@Test(expected = TradeMapperException.class)
	public void testForNullPointerException() throws Exception{
		fileStrategy.retrieveTradeFrmFile(null);
		
	}
	
	@Test(expected = TradeMapperException.class)
	public void testForInvalidFileName() throws TradeMapperException{
		fileStrategy.retrieveTradeFrmFile("/trade1.txt");
	}
	
	
	@Test
	public void testForValidFileName() throws TradeMapperException{
		List<Trade> trades = fileStrategy.retrieveTrade();
		assertNotNull(trades);
	}
	

	
	@Test(expected=TradeMapperException.class)
	public void testForNullCurrencyFieldException() throws TradeMapperException{
		String[] tradeArray = {"JP","","0.50","","04 Apr 2017","04 Apr 2017","100","200"}; //Weekdat
		dataPopulator.populateTrade(tradeArray);
	}
	
	@Test
	public void testForEmptyInput(){
		String[] tradeArray = {};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForInvalidInputLengthGraterThan8(){
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200","45535","sscc"};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForInvalidInputLengthGraterLessThan8(){
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017"};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@Test
	public void testForCurrencyEmptyErrorMessage(){
		String[] tradeArray = {"JP","B","0.50","","04 Apr 2017","04 Apr 2017","100","200"};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyEntityField()  {
		String[] tradeArray = {"","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Entity field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyInstrutionField()  {
		String[] tradeArray = {"JP","","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Instruction Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyAgreedFxField()  {
		String[] tradeArray = {"JP","B","","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Agreed Foreign Exchange is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyInstrutionDateField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Instruction Date is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptySettlementDateField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Settlement Date is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyUnitsField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Units Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEmptyPricePerUnitField()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100",""};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Price Per Unit is invalid",e.getMessage());
		}
	}
	

	@Test
	public void testForCurrencyPassingSpace(){
		String[] tradeArray = {"JP","B","0.50"," ","04 Apr 2017","04 Apr 2017","100","200"};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForEntityFieldPassingSpace()  {
		String[] tradeArray = {" ","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Entity field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForInstrutionFieldPassingSpace()  {
		String[] tradeArray = {"JP"," ","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Instruction Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForAgreedFxFieldPassingSpace()  {
		String[] tradeArray = {"JP","B"," ","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Agreed Foreign Exchange is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForInstrutionDateFieldPassingSpace()  {
		String[] tradeArray = {"JP","B","0.50","GBP"," ","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Instruction Date is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForSettlementDateFieldPassingSpace()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017"," ","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Settlement Date is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForUnitsFieldPassingSpace()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017"," ","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Units Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForPricePerUnitFieldPassingSpace()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100",""};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Price Per Unit is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForCurrencyPassingNull(){
		String[] tradeArray = {"JP","B","0.50","null","04 Apr 2017","04 Apr 2017","100","200"};
		try{
			dataPopulator.populateTrade(tradeArray);
		}catch (TradeMapperException e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	
	@Test
	public void testForInstrutionFieldPassingNull()  {
		String[] tradeArray = {"JP","null","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Instruction Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForAgreedFxFieldPassingNull()  {
		String[] tradeArray = {"JP","B","null","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Agreed Foreign Exchange is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForInstrutionDateFieldPassingNull()  {
		String[] tradeArray = {"JP","B","0.50","GBP","null","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("InstructionDate Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForSettlementDateFieldPassingNull()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","null","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Error While Parsing Settlement Date",e.getMessage());
		}
	}
	
	@Test
	public void testForUnitsFieldPassingNull()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","null","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Units Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForPricePerUnitFieldPassingNull()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","null"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Price Per Unit is invalid",e.getMessage());
		}
	}
	
	
	@Test
	public void testForPopulateTradeWithInvalidCurrency()  {
		String[] tradeArray = {"JP","B","0.50","GBPS","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Currency Field is Invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForPopulateTradeWithValidCurrency() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		Trade trade =dataPopulator.populateTrade(tradeArray);
		assertEquals("GBP", trade.getCurrency());
	}
	
	
	@Test
	public void testForPopulateTradeTotalAmtInUSD() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(10000, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreedFxIsZero() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	
	@Test
	public void testWhenUnitIsZero() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","0","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenPriceIsZero() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","0"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreedFxIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","100","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenUnitIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","-100","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenPriceIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","100","-200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxAndUnitIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","-100","200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxAndPriceIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","-0.50","AED","04 Apr 2017","04 Apr 2017","100","-200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenUnitAndPriceIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","0.50","AED","04 Apr 2017","04 Apr 2017","-100","-200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void testWhenAgreeFxUnitAndPriceIsNegative() throws TradeMapperException {
		String[] tradeArray = {"JP","B","-1","AED","04 Apr 2017","04 Apr 2017","-100","-200"};
		Trade tarde =dataPopulator.populateTrade(tradeArray);
		assertEquals(0, tarde.getTotalTradeAmount(),.0002);
	}
	
	@Test
	public void tesWhenAgreedFxIsAlphaNumeric()  {
		String[] tradeArray = {"JP","B","0.50AB","GBP","04 Apr 2017","04 Apr 2017","100","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Agreed Foreign Exchange is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testWhenUnitIsAlphaNumeric()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100AB","200"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Units Field is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testWhenPricePerUnitIsAlphaNumeric()  {
		String[] tradeArray = {"JP","B","0.50","GBP","04 Apr 2017","04 Apr 2017","100","200AB"};
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Price Per Unit is invalid",e.getMessage());
		}
	}
	
	@Test
	public void testForInvalidDelimiter()  {
		String inpputLine = "MS-B-0.0-GBP-05 Apr 2017-06 Apr 2017-200-100.22";
		String[] tradeArray = inpputLine.split(TradeReportingConstant.DELIMITER);
		try {
			dataPopulator.populateTrade(tradeArray);
		} catch (TradeMapperException e) {
			assertEquals("Invalid Trade Input Line",e.getMessage());
		}
	}
	
	@After
	public void tearDown() throws Exception {
		
		dataPopulator = null;
		weekendDateConverter = null;
		fileStrategy = null;
	
	}

}
