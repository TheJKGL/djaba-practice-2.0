package ymalakhov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SingleThreadSolution {
    public static void main(String[] args) throws IOException {
        SingleThreadSolution singleThreadSolution = new SingleThreadSolution();
        singleThreadSolution.run();
    }

    public void run() throws IOException {
        long startTime = System.currentTimeMillis();

        File file = new File(Constants.FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        long wordCounter = 0;
        while (bufferedReader.ready()) {
            String readLine = bufferedReader.readLine();
            wordCounter += readLine.split(" ").length;
        }
        bufferedReader.close();
        long endTime = System.currentTimeMillis();

        System.out.println(wordCounter);
        System.out.println("Time spent: " + (endTime - startTime) + " milliseconds");

    }
}
