package edu.spbu.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SparseMatrix implements Matrix {
    private int arr[][];
    private int line;
    private int column;
    /*
    one = CSR - разряженный строковой формат
    two = CSC - разряженный столбцовой формат
     */
    private ArrayList<Integer> args_one = new ArrayList<Integer>(); // элементы
    private ArrayList<Integer> one_line = new ArrayList<Integer>();  // строки
    private ArrayList<Integer> one_column = new ArrayList<Integer>();  // столбцы

    private ArrayList<Integer> args_two = new ArrayList<Integer>(); // элементы
    private ArrayList<Integer> two_line = new ArrayList<Integer>();  // строки
    private ArrayList<Integer> two_column = new ArrayList<Integer>();  // столбцы

    public SparseMatrix(String name) {
        arr = Extra.readFile(name);
        line = arr.length;
        column = (arr[0]).length;
        Extra.convCRS(this);
        Extra.convCCS(this);
    }

    public SparseMatrix(ArrayList<Integer> x, ArrayList<Integer> y, ArrayList<Integer> z) {
        args_one = x;
        one_line = y;
        one_column = z;
        Extra.printInfoSparceMatrix(this);
    }

    public Matrix mul(Matrix m) {
        if (m instanceof SparseMatrix) {
            if (column != ((SparseMatrix) m).getLine()) {
                System.out.println("Неверный размер матриц!");
                System.exit(-1);
            } else {
                return mulSS((SparseMatrix) m);
            }
        }
        return m;
    }

    public SparseMatrix mulSS(SparseMatrix matrix) {
        ArrayList<Integer> args_one_f = new ArrayList<Integer>(); // элементы
        ArrayList<Integer> one_line_f = new ArrayList<Integer>();  // строки
        ArrayList<Integer> one_column_f = new ArrayList<Integer>();  // столбцы
        one_line_f.add(0);

        int n = getLine();
        int m = matrix.getColumn();
        for (int i = 1; i <= n; i++) {
            int k = 0;
            Map<Integer, Integer> hashMap = new HashMap<>();
            for (int h = one_line.get(i - 1); h < one_line.get(i); h++)
                hashMap.put(one_column.get(h), args_one.get(h));

            for (int j = 1; j <= m; j++) {
                int s = 0;
                int l = matrix.two_column.get(j - 1);
                int r = matrix.two_column.get(j);
                for (int h = l; h < r; h++) {
                    if (hashMap.containsKey(matrix.two_line.get(h))) {
                        s = s + matrix.args_two.get(h)*hashMap.get(matrix.two_line.get(h));
                    }
                }
                if (s != 0) {
                    k++;
                    args_one_f.add(s);
                    one_column_f.add(j);
                }
            }
            one_line_f.add(k);
        }

        return new SparseMatrix(args_one_f, one_line_f, one_column_f);
    }

    public ArrayList<Integer> getArgs_one() {
        return args_one;
    }

    public ArrayList<Integer> getArgs_two() {
        return args_two;
    }

    public ArrayList<Integer> getLI_one_line() {
        return one_line;
    }

    public ArrayList<Integer> getOne_column() {
        return one_column;
    }

    public ArrayList<Integer> getOne_line() {
        return one_line;
    }

    public ArrayList<Integer> getTwo_column() {
        return two_column;
    }

    public void setArgs_two(ArrayList<Integer> args_two) {
        this.args_two = args_two;
    }

    public void setArgs_one(ArrayList<Integer> args_one) {
        this.args_one = args_one;
    }

    public void setOne_column(ArrayList<Integer> one_column) {
        this.one_column = one_column;
    }

    public ArrayList<Integer> getTwo_line() {
        return two_line;
    }

    public void setTwo_line(ArrayList<Integer> two_line) {
        this.two_line = two_line;
    }

    public void setOne_line(ArrayList<Integer> one_line) {
        this.one_line = one_line;
    }

    public void setTwo_column(ArrayList<Integer> two_column) {
        this.two_column = two_column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public int[][] getArr() {
        return arr;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
