/**
 * Copyright 2007 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hbasemath;

import java.io.IOException;

/**
 * Basic matrix interface.
 */
public interface Matrix {

  /**
   * Gets the double value of (i, j)
   * 
   * @param i ith row of the matrix
   * @param j jth column of the matrix
   * @return the value of entry
   * @throws IOException
   */
  public double get(int i, int j) throws IOException;

  /**
   * Gets the vector of row
   * 
   * @param i the row index of the matrix
   * @return the vector of row
   * @throws IOException
   */
  public Vector getRow(int i) throws IOException;

  /**
   * Gets the vector of column
   * 
   * @param j the column index of the matrix
   * @return the vector of column
   * @throws IOException
   */
  public Vector getColumn(int j) throws IOException;

  /**
   * Get the number of row of the matrix from the meta-data column
   * 
   * @return a number of rows of the matrix
   * @throws IOException
   */
  public int getRows() throws IOException;

  /**
   * Get the number of column of the matrix from the meta-data column
   * 
   * @return a number of columns of the matrix
   * @throws IOException
   */
  public int getColumns() throws IOException;

  /**
   * Gets the label of the row
   * 
   * @throws IOException
   */
  public String getRowLabel(int i) throws IOException;

  /**
   * Gets the label of the column
   * 
   * @throws IOException
   */
  public String getColumnLabel(int j) throws IOException;

  /**
   * Return the matrix path. 
   * (in hbase, path is the tablename. in filesystem, path may be a file path.)
   * 
   * @return the name of the matrix
   */
  public String getPath();

  /**
   * Sets the label of the row
   * 
   * @param i
   * @param name
   * @throws IOException
   */
  public void setRowLabel(int i, String name) throws IOException;

  /**
   * Sets the label of the column
   * 
   * @param j
   * @param name
   * @throws IOException
   */
  public void setColumnLabel(int j, String name) throws IOException;

  /**
   * Sets the double value of (i, j)
   * 
   * @param i ith row of the matrix
   * @param j jth column of the matrix
   * @param value the value of entry
   * @throws IOException
   */
  public void set(int i, int j, double value) throws IOException;

  /**
   * A=alpha*B
   * 
   * @param alpha
   * @param B
   * @return A
   * @throws IOException
   */
  public Matrix set(double alpha, Matrix B) throws IOException;

  /**
   * A=B
   * 
   * @param B
   * @return A
   * @throws IOException
   */
  public Matrix set(Matrix B) throws IOException;

  /**
   * Set the row of a matrix to a given vector
   * 
   * @param row
   * @param vector
   * @throws IOException
   */
  public void setRow(int row, Vector vector) throws IOException;


  /**
   * Sets the dimension of matrix
   * 
   * @param rows the number of rows
   * @param columns the number of columns
   * @throws IOException
   */
  public void setDimension(int rows, int columns) throws IOException;

  /**
   * A(i, j) += value
   * 
   * @param i
   * @param j
   * @param value
   * @throws IOException
   */
  public void add(int i, int j, double value) throws IOException;



  /**
   * Save to a table or file
   * 
   * @param path
   * @return true if saved
   * @throws IOException
   */
  public boolean save(String path) throws IOException;


  /**
   * Close current matrix.
   * 
   * @throws Exception
   */
  public void close() throws IOException;
}
