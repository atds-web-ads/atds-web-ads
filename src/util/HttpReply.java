package util;

import java.util.List;
import java.util.Map;

public class HttpReply {

	private String version;
	private int statusCode;
	private String statusLine;
	private Map<String, List<String>> headers;
	private byte[] data;

	public HttpReply() {
	}

	public String getVersion() {
		return version;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public List<String> getHeaders(String key) {
		return headers.get(key);
	}

	public String getHeader(String key) {
		return getHeaders(key).get(0);
	}

	public byte[] getData() {
		return data;
	}

	public HttpReply parseHttpReply(byte[] rawData) {
		return new HttpReply();
	}

}
