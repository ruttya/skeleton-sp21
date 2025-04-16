package gh2;

import deque.Deque;
import deque.LinkedListDeque;

public class Drum {

    private static final int SR = 44100;      // Sampling Rate（每秒钟的采样次数）
    private static final double DECAY = 1; // energy decay factor

    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    // 持续时间=1/频率
    public Drum(double frequency) {
        buffer = new LinkedListDeque<>();
        // capacity(容量)=SR / frequency 采样频率/声音频率=每个周期采样点数
        int capacity = (int) Math.round(SR / frequency);
        System.out.println("capacity:" + capacity); //
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    public void pluck() {
        double r = Math.random() - 0.5;
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addLast(r);
            r = Math.random() - 0.5;
            System.out.printf("%.3f ", r); //
        }
        System.out.println(""); //
    }

    public void tic() {
        Double n = Math.random();
        if (n > 0.5) {
            buffer.addLast(0.5 * (buffer.removeFirst() + buffer.get(0)) * DECAY);
        } else {
            buffer.addLast(-0.5 * (buffer.removeFirst() + buffer.get(0)) * DECAY);
        }
    }

    public double sample() {
        return buffer.get(0);
    }
}
