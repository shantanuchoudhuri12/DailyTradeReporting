package com.jpmorgan.cib.tradereporting.dataprovider.report;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;

/*
 * @author Shantanu Choudhuri
 */

public interface ReportCreator {

	void createReport(Report reportBean);

}
