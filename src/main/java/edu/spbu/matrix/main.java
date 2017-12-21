package edu.spbu.matrix;

public class main {
    public static void main(String[] args) throws Throwable {
        Matrix m1 = new DenseMatrix("m1.txt");
        Matrix m2 = new DenseMatrix("m2.txt");
        Matrix expected = new DenseMatrix("result.txt");
        m1.dmul(m2);
    }
}
