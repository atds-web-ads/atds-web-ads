package hbasemath;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.conf.*;
import hbasemath.*;


public class Algo {
    
    /*
     * Compute v' = beta*M*v + (1-beta)Unit/n
     * where:
     * - Unit = unit vector
     * - (n*n) is the size of M
     */
    public static Vector mulAdd(Vector v, Matrix m, double beta)  throws Exception
    {
	int n = m.getRows();
	if (n != m.getColumns())
	    throw new Exception("columns != rows for matrix");
	double residue = (1-beta)/n;

	Vector ret = new SparseVector();


	for (int i = 0; i < n; i++) {
	    double vi = v.get(i);
	    if (vi == 0)
		continue;

	    for (int j = 0; j < n; j++) {
		if (m.get(i, j) != 0)
		    ret.add(j, vi * m.get(i, j));
	    }
	}

	for (int i = 0; i < n; i++)
	    ret.add(i, residue);
	return ret;
    }

    public static Vector unitVector(int n)  throws Exception
    {
	Vector v = new SparseVector();
	for(int i = 0; i < n; i++)
	    v.add(i, 1);
	return v;
    }

    public static Matrix open_matrix(String name) throws Exception
    {
	HamaAdmin hamaAdmin = new HamaAdmin(HBaseConfiguration.create());
	Configuration conf = HBaseConfiguration.create();
	return new SparseMatrix(conf, hamaAdmin.getPath(name));
    }

    public static void print_vector(Vector v, int n) throws Exception
    {
	String s = "";
	for (int i = 0; i < n; i++) {
	    s += v.get(i) + " ";
	}
	s += "\n";
	System.out.println(s);
    }

    public static void main(String args[])  throws Exception
    {
	int n = 10;
	Vector unit = unitVector(n);
	Matrix m = open_matrix("mumu");
	Vector v = unit;
	for(int i = 0; i < 50; i++) {
	    v = mulAdd(v, m, 0.85);
	    print_vector(v, 10);
	}
    }
}