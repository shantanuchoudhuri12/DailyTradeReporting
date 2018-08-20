package com.jpmorgan.cib.tradereporting.dataprovider.strategy.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.jpmorgan.cib.tradereporting.dataprovider.strategy.Strategy;
import com.jpmorgan.cib.tradereporting.dataprovider.util.TradeReportingConstant;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;
import com.jpmorgan.cib.tradereporting.exception.TradeMapperException;
/*
 * @author Shantanu Choudhuri
 */

public class FileStrategy extends DataPopulator implements Strategy {

	private static Logger logger = Logger.getLogger("FileStrategy");

	@Override
	public List<Trade> retrieveTrade() throws TradeMapperException {
		return retrieveTradeFrmFile("/trade.txt");
	}

	public List<Trade> retrieveTradeFrmFile(String fileName) throws TradeMapperException {

		Optional<String> optFileName = Optional.ofNullable(fileName);
		if (optFileName.isPresent()) {

			String fileNameToRefer = optFileName.get();
			List<Trade> trades = new ArrayList<>();
			try {

				InputStream in = getClass().getResourceAsStream(fileNameToRefer);
				if (in != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					while (br != null) {
						// Read each line from the text file
						String line = br.readLine();

						if (line != null) {

							String[] tradeArray = line.split(TradeReportingConstant.DELIMITER);

							try {
								// Calling method to fill tradeBean Object
								Trade trade = populateTrade(tradeArray);
								trades.add(trade);

							} catch (TradeMapperException ex) {
								logger.error(line, ex);
							}

						} else {
							break;
						}
					}
				} else {
					throw new TradeMapperException("Issue in Reading The Input File");
				}

			} catch (IOException ex) {
				throw new TradeMapperException("Issue in Reading The Input File", ex);
			}

			return trades;
		} else {
			throw new TradeMapperException("Null file has been passed");
		}

	}

}
