package edu.spbu.matrix;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DenseMatrix implements Matrix {
    private int arr[][];
    private int line;
    private int column;

    public DenseMatrix(String name) {
        arr = Extra.readFile(name);
        line = arr.length;
        column = (arr[0]).length;
    }

    public DenseMatrix(int[][] x) {
        arr = x;
        line = arr.length;
        column = (arr[0]).length;
    }

    public Matrix mul(Matrix matrix) {
        if (matrix instanceof DenseMatrix) {
            if (column != ((DenseMatrix) matrix).getLine()) {
                System.out.println("Неверный размер матриц!");
                System.exit(-1);
            } else
                return mulDD((DenseMatrix) matrix);
        }
        return matrix;
    }

    private Matrix mulDD(DenseMatrix matrix) {
        int m = getLine();
        int n = getColumn();
        int q = matrix.getColumn();
        int[][] C = new int[m][q];
        int[][] A = getArr();
        int[][] B = matrix.getArr();

        for (int i = 0; i < m; i++)
            for (int j = 0; j < q; j++) {
                C[i][j] = 0;
                for (int k = 0; k < n; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        Extra.printMatrixFile(C, "result.txt");
        return new DenseMatrix(C);
    }


    public boolean equals(Object obj) {
        if (obj instanceof DenseMatrix) {
            DenseMatrix otherObj = (DenseMatrix) obj;
            if ((getColumn() != otherObj.getColumn()) | (getLine() != otherObj.getLine()))
                return false;
            for (int i = 0; i < getLine(); i++)
                for (int j = 0; j < getColumn(); j++)
                    if (arr[i][j] != otherObj.getArr(i, j))
                        return false;
        }
        return true;
    }

    //
    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public int getArr(int x, int y) {
        return arr[x][y];
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setLine(int line) {
        this.line = line;
    }

}