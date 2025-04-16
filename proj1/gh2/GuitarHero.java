package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 * 选做内容，键盘37按键对应37个音符
 * 有声音,初始频率赋值都是对的
 */
public class GuitarHero {
    public static final double CONCERT_A = 440.0; // means frequency
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int NUM = KEYBOARD.length(); //37

    public static void main(String[] args) {
        /* create 37 guitar strings */
        GuitarString[] strings = new GuitarString[NUM];

        //创建所有琴弦
        for (int i = 0; i < NUM; i++) {
            strings[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            /* 按过的键变成新的白噪声状态 */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
            }
            /* compute the superposition of samples */
            /* 计算样本的叠加结果
             * 因为循环末尾会tic()，所以所有位置叠加一遍，涉及以下三种情况：
             *  1.此次循环刚按键，琴弦刚pluck()
             *  2.前几次循环曾按过键，琴弦不停play(sample)-tic()中
             *  3.从未按过的键，值==0，不停play(sample)-tic()但不会出声
             * 之前想复杂了，循环次数和按键时机没有任何关系，循环速度>>按键速度，因此只考虑声音不停推进即可。
             * 直接照抄GuitarHeroLite.java其实够了 */
            double sample = 0;
            for (int i = 0; i < strings.length; i++) {
                sample += strings[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);
            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < NUM; i++) {
                strings[i].tic();
            }
        }
    }
}

