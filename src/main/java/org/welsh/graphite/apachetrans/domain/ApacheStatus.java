package org.welsh.graphite.apachetrans.domain;

public class ApacheStatus {

	private String totalAccesses;
	private String totalkBytes;
	private String uptime;
	private String cpuLoad;
	private String reqPerSec;
	private String bytesPerSec;
	private String bytesPerReq;
	private String busyWorkers;
	private String idleWorkers;

	public ApacheStatus() {
	}

	public ApacheStatus(String totalAccesses, String totalkBytes,
			String uptime, String cpuLoad, String reqPerSec,
			String bytesPerSec, String bytesPerReq, String busyWorkers,
			String idleWorkers) {
		this.totalAccesses = totalAccesses;
		this.totalkBytes = totalkBytes;
		this.uptime = uptime;
		this.cpuLoad = cpuLoad;
		this.reqPerSec = reqPerSec;
		this.bytesPerSec = bytesPerSec;
		this.bytesPerReq = bytesPerReq;
		this.busyWorkers = busyWorkers;
		this.idleWorkers = idleWorkers;
	}

	public String getTotalAccesses() {
		return totalAccesses;
	}

	public void setTotalAccesses(String totalAccesses) {
		this.totalAccesses = totalAccesses;
	}

	public String getTotalkBytes() {
		return totalkBytes;
	}

	public void setTotalkBytes(String totalkBytes) {
		this.totalkBytes = totalkBytes;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(String cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	public String getReqPerSec() {
		return reqPerSec;
	}

	public void setReqPerSec(String reqPerSec) {
		this.reqPerSec = reqPerSec;
	}

	public String getBytesPerSec() {
		return bytesPerSec;
	}

	public void setBytesPerSec(String bytesPerSec) {
		this.bytesPerSec = bytesPerSec;
	}

	public String getBytesPerReq() {
		return bytesPerReq;
	}

	public void setBytesPerReq(String bytesPerReq) {
		this.bytesPerReq = bytesPerReq;
	}

	public String getBusyWorkers() {
		return busyWorkers;
	}

	public void setBusyWorkers(String busyWorkers) {
		this.busyWorkers = busyWorkers;
	}

	public String getIdleWorkers() {
		return idleWorkers;
	}

	public void setIdleWorkers(String idleWorkers) {
		this.idleWorkers = idleWorkers;
	}
	
	@Override
	public String toString() {
		return "ApacheStatus [totalAccesses=" + totalAccesses
				+ ", totalkBytes=" + totalkBytes + ", uptime=" + uptime
				+ ", cpuLoad=" + cpuLoad + ", reqPerSec=" + reqPerSec
				+ ", bytesPerSec=" + bytesPerSec + ", bytesPerReq="
				+ bytesPerReq + ", busyWorkers=" + busyWorkers
				+ ", idleWorkers=" + idleWorkers + "]";
	}
}
