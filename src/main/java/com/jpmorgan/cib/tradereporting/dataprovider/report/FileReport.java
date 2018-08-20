package com.jpmorgan.cib.tradereporting.dataprovider.report;

import com.jpmorgan.cib.tradereporting.dataprovider.vo.Report;

/*
 * @author Shantanu Choudhuri
 */

public class FileReport extends ReportBridge {
	Report reportBean;

	ReportCreator reportCreator;

	public FileReport(Report reportBean, ReportCreator reportCreator) {
		super(reportCreator);
		this.reportBean = reportBean;
		this.reportCreator = reportCreator;
	}

	@Override
	public void createReport() {
		reportCreator.createReport(reportBean);
	}

}
