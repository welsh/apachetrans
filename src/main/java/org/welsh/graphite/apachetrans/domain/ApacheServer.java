package org.welsh.graphite.apachetrans.domain;

public class ApacheServer {

	private String apacheUrl;
	private String metricPath;

	private boolean reportTotalAccesses;
	private boolean reportTotalkBytes;
	private boolean reportUptime;
	private boolean reportCpuLoad;
	private boolean reportReqPerSec;
	private boolean reportBytesPerSec;
	private boolean reportBytesPerReq;
	private boolean reportBusyWorkers;
	private boolean reportIdleWorkers;

	public ApacheServer() {
	}

	public ApacheServer(String apacheUrl, String metricPath) {
		this.apacheUrl = apacheUrl;
		this.metricPath = metricPath;
	}
	
	public void setAllReportingValues(boolean value) {
		reportTotalAccesses = value;
		reportTotalkBytes = value;
		reportUptime = value;
		reportCpuLoad = value;
		reportReqPerSec = value;
		reportBytesPerSec = value;
		reportBytesPerReq = value;
		reportBusyWorkers = value;
		reportIdleWorkers = value;
	}

	public String getApacheUrl() {
		return apacheUrl;
	}

	public void setApacheUrl(String apacheUrl) {
		this.apacheUrl = apacheUrl;
	}

	public String getMetricPath() {
		return metricPath;
	}

	public void setMetricPath(String metricPath) {
		this.metricPath = metricPath;
	}

	public boolean isReportTotalAccesses() {
		return reportTotalAccesses;
	}

	public void setReportTotalAccesses(boolean reportTotalAccesses) {
		this.reportTotalAccesses = reportTotalAccesses;
	}

	public boolean isReportTotalkBytes() {
		return reportTotalkBytes;
	}

	public void setReportTotalkBytes(boolean reportTotalkBytes) {
		this.reportTotalkBytes = reportTotalkBytes;
	}

	public boolean isReportUptime() {
		return reportUptime;
	}

	public void setReportUptime(boolean reportUptime) {
		this.reportUptime = reportUptime;
	}

	public boolean isReportCpuLoad() {
		return reportCpuLoad;
	}

	public void setReportCpuLoad(boolean reportCpuLoad) {
		this.reportCpuLoad = reportCpuLoad;
	}

	public boolean isReportReqPerSec() {
		return reportReqPerSec;
	}

	public void setReportReqPerSec(boolean reportReqPerSec) {
		this.reportReqPerSec = reportReqPerSec;
	}

	public boolean isReportBytesPerSec() {
		return reportBytesPerSec;
	}

	public void setReportBytesPerSec(boolean reportBytesPerSec) {
		this.reportBytesPerSec = reportBytesPerSec;
	}

	public boolean isReportBytesPerReq() {
		return reportBytesPerReq;
	}

	public void setReportBytesPerReq(boolean reportBytesPerReq) {
		this.reportBytesPerReq = reportBytesPerReq;
	}

	public boolean isReportBusyWorkers() {
		return reportBusyWorkers;
	}

	public void setReportBusyWorkers(boolean reportBusyWorkers) {
		this.reportBusyWorkers = reportBusyWorkers;
	}

	public boolean isReportIdleWorkers() {
		return reportIdleWorkers;
	}

	public void setReportIdleWorkers(boolean reportIdleWorkers) {
		this.reportIdleWorkers = reportIdleWorkers;
	}

	@Override
	public String toString() {
		return "ApacheServer [apacheUrl=" + apacheUrl + ", metricPath="
				+ metricPath + ", reportTotalAccesses=" + reportTotalAccesses
				+ ", reportTotalkBytes=" + reportTotalkBytes
				+ ", reportUptime=" + reportUptime + ", reportCpuLoad="
				+ reportCpuLoad + ", reportReqPerSec=" + reportReqPerSec
				+ ", reportBytesPerSec=" + reportBytesPerSec
				+ ", reportBytesPerReq=" + reportBytesPerReq
				+ ", reportBusyWorkers=" + reportBusyWorkers
				+ ", reportIdleWorkers=" + reportIdleWorkers + "]";
	}
}
