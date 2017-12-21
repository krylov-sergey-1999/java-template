package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest {
    @Test
    public void mulDD() {
        Matrix m1 = new DenseMatrix("m1.txt");
        Matrix m2 = new DenseMatrix("m2.txt");
        Matrix expected = new DenseMatrix("result.txt");
        assertEquals(expected, m1.mul(m2));
    }

    @Test
    public void dmul() throws Throwable{
        Matrix m1 = new DenseMatrix("m1.txt");
        Matrix m2 = new DenseMatrix("m2.txt");
        Matrix expected = new DenseMatrix("result.txt");
        assertEquals(expected, m1.dmul(m2));
    }

}