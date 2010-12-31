package util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Read an ARC index file built by ArcIndexBuilder and answer queries.
 * 
 */
public class ArcIndexReader {

	private long[] buffer;

	/**
	 * Constructor. Reads the ARC index file in memory.
	 * 
	 * @param idxFile
	 * @throws IOException
	 */
	public ArcIndexReader(String idxFile) throws IOException {
		File f = new File(idxFile);
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream(f)));
		long fileSize = f.length();
		int numFiles = (int) (fileSize / 8);
		buffer = new long[numFiles];
		for (int i = 0; i < numFiles; i++)
			buffer[i] = in.readLong();
		in.close();
	}

	/**
	 * Get the number of actual records in the corresponding ARC dump file
	 * (ignores the first record, which contains only meta information).
	 * 
	 * @return
	 */
	public int getNumRecords() {
		return buffer.length - 1;
	}

	/**
	 * Get the offset, in bytes, at which the id-th record starts in the ARC
	 * dump.
	 * 
	 * @param id
	 * @return
	 */
	public long getRecordOffset(int id) {
		return buffer[id];
	}

	/**
	 * Get the size, in bytes, of the id-th record from the ARC dump.
	 * 
	 * @param id
	 * @return
	 */
	public int getRecordSize(int id) {
		return (int) (buffer[id + 1] - buffer[id]);
	}

	/**
	 * Main program for testing ArcIndexReader. Queries the ARC index for the
	 * records given as arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.format("Usage: java %s <dump.idx> [<id>...]\n",
					ArcIndexReader.class.getName());
			return;
		}

		try {
			ArcIndexReader idxReader = new ArcIndexReader(args[0]);
			System.out.format("Number of files: %d\n",
					idxReader.getNumRecords());
			for (int i = 1; i < args.length; i++) {
				int id = Integer.parseInt(args[i]);
				System.out.format("File %6d: offset=%08x size=%08x\n", i,
						idxReader.getRecordOffset(id),
						idxReader.getRecordSize(id));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
