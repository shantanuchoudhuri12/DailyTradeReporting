package com.jpmorgan.cib.tradereporting.dataprovider.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/*
 * This class helps in generating the reports for the trade details we get 
 * everyday total buy and sell amount based on settlement date and also  
 * company ranking for buy and sell amount.
 * 
 * @author Shantanu Choudhuri
 */

public class GenerateReportHelper {

	private static Logger logger = Logger.getLogger("GenerateReportHelper");

	/*
	 * This method helps to load the map based on the given Parameters
	 * 
	 * @param key
	 * 
	 * @param tradeAmount
	 * 
	 * @param keyValMap
	 * 
	 * @return map
	 */
	public static Map<String, Double> populateMapDetails(String key, double tradeAmount,
			Map<String, Double> keyValMap) {
		if (keyValMap.size() > 0) {
			if (keyValMap.get(key) != null) {
				tradeAmount = tradeAmount + keyValMap.get(key);
			}
		}
		keyValMap.put(key, tradeAmount);
		return keyValMap;
	}

	/*
	 * This method helps to sort the map based on the given value descending
	 * order
	 * 
	 * @param map
	 * 
	 * @return map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

		map = map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return map;
	}

}
