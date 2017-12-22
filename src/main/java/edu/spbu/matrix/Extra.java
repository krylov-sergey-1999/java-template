package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

public class Extra {
    public static String getPath() {
        File file = new File("example.txt");
        String path = file.getAbsolutePath();  // получаем абсолютный путь
        // Костыль
        path = path.substring(0, path.lastIndexOf("\\java-template"));
        path = path + "\\java-template";
        //
        file.delete();
        return path;
    }  // Получаем абсолютный путь к нашему проекту

    public static String buildPathResources(String name) {
        String path = getPath(); // получаем абсолютный путь к нашему проекту
        path += "\\src\\main\\java\\edu\\spbu\\resources\\" + name; // дополняем его
        return path; // отдаем
    }  // Строим путь к файлу в директории resources по его имени

    public static int[][] readFile(String name) {
        ArrayList<int[]> myList = new ArrayList<int[]>();
        String path = buildPathResources(name);
        File file = new File(path); // связываем файл матрицы с объектом file
        int n = 0;  // строки
        int m = 0;  // столбцы
        try {
            Scanner myScan = new Scanner(file);
            while (myScan.hasNextLine()) {
                String line = myScan.nextLine();
                int[] numArr = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray(); // Эта строчка магия. Надо разобраться.
                myList.add(numArr);
                n++;
            }
            if ((n == 0) && (name != "result.txt")){
                    System.out.println("Нулевой массив! Введите заново! (" +name+")");
                    System.exit(-1);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("\nПо такому пути : " + path);
            System.out.println("Не удается найти указанный файл : " + name);
            System.out.println("Из-за этого программа завершена.");
            System.exit(-1);
        }
        m = (myList.get(0)).length; // столбцы
        int[][] arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            if ((myList.get(i)).length != m) {
                System.out.println("Массив введен неверно! Элементов в строке " + (i+1) + "не столько сколько надо.");
                System.exit(-1);
            }
            for (int j = 0; j < m; j++) {

                arr[i][j] = (myList.get(i))[j];
            }
        }
        return arr;
    }  //Считывание массива по имени файла

    public static void printMatrixConsol(int[][] arr) {
        int n = arr.length;
        int m = arr[n - 1].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println("");
        }
    }  // вывод массива в консоль

    public static void printMatrixFile(int[][] arr, String name) {
        String path = buildPathResources(name);
        try{
            Formatter myFor = new Formatter(path);
            int n = arr.length;
            int m = arr[n - 1].length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    myFor.format("%d ",arr[i][j]);
                myFor.format("%s","\r\n");
            }
            myFor.close();
        }
        catch (Exception e){
            System.out.println("Возникла проблема с выводом массива в :" + name);
        }
    } // вывод массива в файл

}
