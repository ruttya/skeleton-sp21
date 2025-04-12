package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 * 选做内容，键盘37按键对应37个音符
 * 有声音,初始频率赋值都是对的
 * TODO:嵌套关系有问题，目前无法实时反馈.
 */
public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int NUM = 37;

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString[] strings = new GuitarString[NUM];
        System.out.println(keyboard.indexOf("c"));
        for (int i = 0; i < NUM; i++) {
            strings[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
        }
        char key = 'a';
        double sample = 0;
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
            }
            int index = keyboard.indexOf(key);
            if (keyboard.indexOf(key) == -1) continue;
            strings[index].pluck();
            /* compute the superposition of samples */
            /*计算样本的叠加结果 同时按多个键时要计算合成声音？*/
            sample += strings[index].sample();
            for (int i = 0; i < 50000; i += 1) {
                /* play the sample on standard audio */
                StdAudio.play(sample + strings[index].sample());
                /* advance the simulation of each guitar string by one step */
                strings[index].tic();
            }
        }
    }
}

