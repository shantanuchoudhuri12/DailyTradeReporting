package com.jpmorgan.cib.tradereporting.dataprovider.strategy.impl;

import java.util.Optional;

import com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor.WeekendBasedSettleDateConvertor;
import com.jpmorgan.cib.tradereporting.dataprovider.dateconvertor.WeekendBasedSettleDateConvertorImpl;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;

/*
 * @author Shantanu Choudhuri
 */

public class DataPopulator {

	public Trade populateTrade(String[] tradeArray) throws TradeMapperException {
		Trade trade = new Trade();
		try {

			// Iterate the tradeArray, validate and populate trade
			if (tradeArray != null && tradeArray.length == 8) {

				Optional<String> optEntity = Optional.ofNullable(tradeArray[0]);
				if (optEntity.isPresent() && !optEntity.get().trim().isEmpty()) {
					trade.setEntity(optEntity.get());
				} else {
					throw new TradeMapperException("Entity field is invalid");
				}

				Optional<String> optInstruction = Optional.ofNullable(tradeArray[1]);
				if (optInstruction.isPresent() && !optInstruction.get().trim().isEmpty()) {
					trade.setInstruction(optInstruction.get());
				} else {
					throw new TradeMapperException("Instruction Field is invalid");
				}

				Optional<String> optAgreedFX = Optional.ofNullable(tradeArray[2]);
				if (optAgreedFX.isPresent() && !optAgreedFX.get().trim().isEmpty()) {
					try {
						trade.setAgreedFx(Double.parseDouble(optAgreedFX.get()));
					} catch (Exception e) {
						throw new TradeMapperException("Agreed Foreign Exchange is invalid");
					}
				} else {
					throw new TradeMapperException("Agreed Foreign Exchange is invalid");
				}

				Optional<String> optCurrency = Optional.ofNullable(tradeArray[3]);
				if (optCurrency.isPresent() && !optCurrency.get().trim().isEmpty()) {
					String currency = optCurrency.get();
					if (currency.length() == 3) {
						trade.setCurrency(currency);
					} else {
						throw new TradeMapperException("Currency Field is Invalid");
					}
				} else {
					throw new TradeMapperException("Currency Field is Invalid");
				}

				Optional<String> optInstructionDate = Optional.ofNullable(tradeArray[4]);
				if (optInstructionDate.isPresent() && !optInstructionDate.get().trim().isEmpty()) {
					trade.setInstructionDate(optInstructionDate.get());
				} else {
					throw new TradeMapperException("Instruction Date is invalid");
				}

				Optional<String> optSettlementDate = Optional.ofNullable(tradeArray[5]);
				if (optSettlementDate.isPresent() && !optSettlementDate.get().trim().isEmpty()) {
					trade.setSettlementDate(optSettlementDate.get());
				} else {
					throw new TradeMapperException("Settlement Date is invalid");
				}

				Optional<String> optUnits = Optional.ofNullable(tradeArray[6]);
				if (optUnits.isPresent() && !optUnits.get().trim().isEmpty()) {
					try {
						trade.setUnits(Integer.parseInt(optUnits.get()));
					} catch (Exception e) {
						throw new TradeMapperException("Units Field is invalid");
					}
				} else {
					throw new TradeMapperException("Units Field is invalid");
				}

				Optional<String> optPricePerUnit = Optional.ofNullable(tradeArray[7]);
				if (optPricePerUnit.isPresent() && !optPricePerUnit.get().trim().isEmpty()) {
					try {
						trade.setPricePerUnit(Double.parseDouble(optPricePerUnit.get()));
					} catch (Exception e) {
						throw new TradeMapperException("Price Per Unit is invalid");
					}
				} else {
					throw new TradeMapperException("Price Per Unit is invalid");
				}

				// Calling method to calculate the total trade amount
				trade.setTotalTradeAmount(calculateTotalTradeAmt(trade));

				// Calling method to check for weekend and change accordingly
				WeekendBasedSettleDateConvertor weekendBasedSettleDateConvertor = new WeekendBasedSettleDateConvertorImpl();
				trade = weekendBasedSettleDateConvertor.settlementDateCalculator(trade);

			} else {
				throw new TradeMapperException("Invalid Trade Input Line");
			}
		} catch (NumberFormatException ex) {
			throw new TradeMapperException("Error While Parsing String to Number", ex);
		}

		return trade;
	}

	/*
	 * Calculate the trade amount based on per unit * total unit * agreed fx
	 */
	private double calculateTotalTradeAmt(Trade trade) {
		double totalTradeAmt = 0;
		if (trade.getPricePerUnit() > 0 && trade.getAgreedFx() > 0 && trade.getUnits() > 0) {
			totalTradeAmt = trade.getPricePerUnit() * trade.getAgreedFx() * trade.getUnits();
		}
		return totalTradeAmt;
	}

}