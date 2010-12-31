package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Read an ARC dump file and create an index file.
 * 
 * Format of the index is a stream of long values (8 bytes each). Each value is
 * the offset at which the corresponding record ends in the original ARC file.
 * 
 * First record contains meta information about the data set, so it can be
 * ignored. Thus, the first value in the index file indicates the start of the
 * useful data in the ARC dump.
 * 
 */
public class ArcIndexBuilder {

	public static final int MAX_HEADER_LINE_SIZE = 1024;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.format("Usage: java %s <dump.arc> <dump.idx>\n",
					ArcIndexBuilder.class.getName());
			return;
		}
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					args[0]));
			DataOutputStream out = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(args[1])));
			byte[] buf = new byte[MAX_HEADER_LINE_SIZE];
			int id = -1; // We don't count first record
			long offset = 0;
			while (true) {
				// Read record header (terminated by newline)
				int n = 0, tmp;
				while ((tmp = in.read()) != -1 && tmp != '\n')
					buf[n++] = (byte) tmp;
				if (tmp == -1) {
					if (n != 0)
						System.err.format("Warning: "
								+ "Incomplete header for file %d, offset=%d\n",
								id, offset);
					break;
				}
				ArcRecord rec = ArcRecord.parseArcRecord(new String(buf, 0, n));
				long length = rec.getLength();
				System.out.format("File %6d: offset=%08x size=%08x "
						+ "ip=%-15s date=%s mime=%s url=%s\n", id, offset,
						length, rec.getIpAddress(), dateFormat.format(rec
								.getArchiveDate()), rec.getContentType(), rec
								.getUrl());
				// Skip length + 1 bytes (records are followed by one newline)
				for (long total = 0, skipped; total < length + 1; total += skipped)
					skipped = in.skip(length + 1 - total);
				id++;
				offset += n + 1; // Record header and newline
				offset += length + 1; // Record data and newline
				out.writeLong(offset);
			}
			System.out.format("Success! Index written to %s (%d records).\n",
					args[1], id);
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
