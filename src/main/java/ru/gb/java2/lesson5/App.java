package ru.gb.java2.lesson5;

public class App {

    static final int size = 10000000;
    static final int h = size / 2;
    final float[] arr = new float[size];
    float[] arr1 = new float[h];
    float[] arr2 = new float[h];

    public static void main(String[] args) {
        App app = new App();
        app.first();
        app.second();
    }

    public void first() {
        for (int i = 0; i < arr.length; i++) {
            arr[i]++;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - a + " msec Время работы одного потока");

    }

    public void second() {
        for (int i = 0; i < arr.length; i++) {
            arr[i]++;
        }

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1 = new float[h], 0, h);
        System.arraycopy(arr, h, arr2 = new float[h], 0, h);

        Thread firstThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread secondThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        System.out.println(System.currentTimeMillis() - a + " msec Время работы двух потоков");
    }
}
