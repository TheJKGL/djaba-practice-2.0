package dhalchenko;

import dhalchenko.fork.Fork;
import dhalchenko.philosophy.Philosophy;

import java.util.Arrays;

public class Demo {

    public static void main(String[] args) {
        Fork[] fork = new Fork[5];
        Philosophy[] philosophies = new Philosophy[5];

        for (int i = 0; i < fork.length; i++) {
            fork[i] = new Fork();
        }

        for (int i = 0; i < philosophies.length; i++) {
            if (i == philosophies.length - 1) {
                philosophies[i] = new Philosophy(fork[(i + 1) % 5], fork[i]);
            } else {
                philosophies[i] = new Philosophy(fork[i], fork[(i + 1) % 5]);
            }
        }

        Arrays.stream(philosophies).forEach((philosophy -> new Thread(philosophy).start()));
    }
}
