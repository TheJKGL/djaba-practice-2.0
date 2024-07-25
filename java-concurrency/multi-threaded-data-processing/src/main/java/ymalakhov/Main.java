package ymalakhov;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        MultiThreadSolution2 multiThreadSolution2 = new MultiThreadSolution2();
        multiThreadSolution2.run();

        SingleThreadSolution singleThreadSolution = new SingleThreadSolution();
        singleThreadSolution.run();
    }
}
