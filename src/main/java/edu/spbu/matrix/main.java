package edu.spbu.matrix;

public class main {
    public static void main(String[] args) {
        SparseMatrix m1 = new SparseMatrix("m1.txt");
        SparseMatrix m2 = new SparseMatrix("m2.txt");
        Matrix res = m1.mulSS(m2);
    }
}
