package com.jpmorgan.cib.tradereporting.dataprovider.report;

public abstract class ReportBridge {

	private ReportCreator reportCreator;

	public ReportBridge(ReportCreator reportCreator) {
		this.reportCreator = reportCreator;
	};

	public abstract void createReport();

}
