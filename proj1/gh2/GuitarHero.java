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
        double[] vol = new double[NUM]; //音量，用来控制响否

        for (int i = 0; i < NUM; i++) {
            strings[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
            vol[i] = 0.0;
        }
        //TODO:此行以下要重新考虑
        double sample = 0;
        int index = -1;
        while (true) {
            char key = 'a';
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                index = KEYBOARD.indexOf(key);
                if (index == -1) {
                    continue;
                }
                strings[index].pluck();
            }

            /* compute the superposition of samples */
            /* 计算样本的叠加结果
             * 某次循环是a键第1 sample和c键第2 sample的叠加 */
            for (int i = 0; i < strings.length; i++) {
                if (vol[i] > 0.0) {
                    strings[i].tic();
                }
            }
            sample += strings[index].sample();
            /* play the sample on standard audio */
            StdAudio.play(sample);
            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < NUM; i++) {
                strings[i].tic();
            }
        }
    }
}

