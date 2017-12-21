package edu.spbu.matrix;

public interface Matrix {
    Matrix mul(Matrix o);

    Matrix dmul(Matrix o) throws Throwable;
}
