package edu.spbu.matrix;

public class DenseMatrix implements Matrix {
    private int arr[][];
    private int line;
    private int column;
    private int processor = 16;

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

    public DenseMatrix(int line, int column) {
        this.arr = new int[line][column];
        this.line = line;
        this.column = column;
    }

    //  MatrixOneThread
    public Matrix mul(Matrix obj) {
        if (obj instanceof DenseMatrix) {
            DenseMatrix matrix = (DenseMatrix) obj;
            if (column != matrix.line) {
                System.out.println("Неверный размер матриц, введите заново.");
                System.exit(-1);
            } else {
                return mulDD(matrix);
            }
        }
        return obj;
    } // Распределяет далее по функциям и отсеивает неправильные матрицы

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
        //Extra.printMatrixFile(C, "result.txt");
        return new DenseMatrix(C);
    }

    // MatixMultiThread
    int args[][], res[][];
    int line_v2;
    int column_v2;
    private int flag = -line / processor;

    public Matrix dmul(Matrix obj) throws Throwable {
        DenseMatrix matrix = (DenseMatrix) obj;
        args = matrix.arr;
        line_v2 = matrix.getLine();
        column_v2 = matrix.getColumn();
        res = new int[line][column_v2];
        Thread[] t = new Thread[processor];
        for (int i = 0; i < processor; i++) {
            t[i] = new Thread(new MyRun());
            t[i].setName("Поток " + i);
            t[i].start();
        }
        for (int i = 0; i < processor; ++i)
            t[i].join();
        //Extra.printMatrixConsol(res);
        return new DenseMatrix(res);
    }

    class MyRun implements Runnable {
        public void run() {
            Frame f = getFrame();
            int start = f.getStart(), end = f.getEnd();
            for (int i = start; i < end; i++) {
                for (int j = 0; j < column_v2; j++) {
                    res[i][j] = 0;
                    for (int k = 0; k < column; k++)
                        res[i][j] += arr[i][k] * args[k][j];
                }
            }
            System.out.println(Thread.currentThread().getName() + " закончен.");
        }
    }

    private synchronized Frame getFrame() {
        Frame f = new Frame();
        f.setStart(flag);
        flag += line / processor;
        if (flag < line)
            f.setEnd(flag);
        else
            f.setEnd(line);
        System.out.println("Начало = " + f.getStart() + ", Конец = " + f.getEnd() + "; Thread = " + Thread.currentThread().getName());
        return f;
    }

    class Frame {
        private int start = 0, end = 0;

        public void setStart(int x) {
            start = x;
        }

        public void setEnd(int x) {
            end = x;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    } // Границы

    //
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