package util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ArcRecordReader {

	private RandomAccessFile arcDump;
	private ArcIndexReader idxReader;

	public ArcRecordReader(String arcFile, String idxFile) throws IOException {
		arcDump = new RandomAccessFile(arcFile, "r");
		idxReader = new ArcIndexReader(idxFile);
	}

	public byte[] getRecord(int id) throws IOException {
		arcDump.seek(idxReader.getRecordOffset(id));
		byte[] data = new byte[idxReader.getRecordSize(id)];
		arcDump.read(data);
		return data;
	}
}
