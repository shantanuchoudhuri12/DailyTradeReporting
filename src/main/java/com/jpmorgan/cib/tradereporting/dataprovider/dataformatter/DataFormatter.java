package com.jpmorgan.cib.tradereporting.dataprovider.dataformatter;

import java.util.List;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;
import com.jpmorgan.cib.tradereporting.dataprovider.vo.Trade;

public interface DataFormatter {

	Report formatData(List<Trade> tradeList, String formatterType);

}
