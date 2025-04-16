package gh2;

import deque.Deque;
import deque.LinkedListDeque;

public class Harp {

    private static final int SR = 44100 * 2;      // Sampling Rate（每秒钟的采样次数）
    private static final double DECAY = .997; // energy decay factor

    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    // 持续时间=1/频率
    public Harp(double frequency) {
        buffer = new LinkedListDeque<>();
        // capacity(容量)=SR / frequency 采样频率/声音频率=每个周期采样点数
        int capacity = (int) Math.round(SR / frequency);
        System.out.println("capacity:" + capacity); //
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    /* 填满随机数，相当于声音初始化，变成能发声的状态 */
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

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    /* 出声应该是tic()->play(sample)持续循环，循环次数=SR */

    /* 更新一次当前deque */
    public void tic() {
        buffer.addLast(-0.5 * (buffer.removeFirst() + buffer.get(0)) * DECAY);
    }

    /* Return the double at the front of the buffer. */
    /* 取样，获取当前该播放的声音 */
    public double sample() {
        return buffer.get(0);
    }
}
