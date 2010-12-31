package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ArcRecord {

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private String url;
	private String ipAddress;
	private Date archiveDate;
	private String contentType;
	private int length;
	private byte[] data;

	public ArcRecord(String url, String ipAddress, Date archiveDate,
			String contentType, int length) {
		this.url = url;
		this.ipAddress = ipAddress;
		this.archiveDate = archiveDate;
		this.contentType = contentType;
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public String getContentType() {
		return contentType;
	}

	public int getLength() {
		return length;
	}

	public byte[] getData() {
		return data;
	}

	void setData(byte[] data) {
		this.data = data;
	}

	public static ArcRecord parseArcRecord(String urlRecord)
			throws ParseException {
		StringTokenizer tokens = new StringTokenizer(urlRecord, " ");
		String url = tokens.nextToken();
		String ipAddress = tokens.nextToken();
		Date archiveDate = dateFormat.parse(tokens.nextToken());
		String contentType = tokens.nextToken();
		int length = Integer.parseInt(tokens.nextToken());
		return new ArcRecord(url, ipAddress, archiveDate, contentType, length);
	}

	public static ArcRecord parseArcRecord(byte[] rawData)
			throws ParseException {
		int i, n = rawData.length;
		for (i = 0; i < n && rawData[i] != '\n'; i++) ;
		return ArcRecord.parseArcRecord(new String(rawData, 0, n));
	}

}
