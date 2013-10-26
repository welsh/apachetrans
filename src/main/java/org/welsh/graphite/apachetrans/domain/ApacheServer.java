package org.welsh.graphite.apachetrans.domain;

public class ApacheServer {

	private String apacheUrl;
	private String metricPath;

	public ApacheServer() {
	}

	public ApacheServer(String apacheUrl, String metricPath) {
		this.apacheUrl = apacheUrl;
		this.metricPath = metricPath;
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

	@Override
	public String toString() {
		return "ApacheServer [apacheUrl=" + apacheUrl + ", metricPath="
				+ metricPath + "]";
	}
}
