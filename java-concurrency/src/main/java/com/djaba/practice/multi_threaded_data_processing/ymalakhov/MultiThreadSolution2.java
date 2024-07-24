package com.djaba.practice.multi_threaded_data_processing.ymalakhov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadSolution2 {

    public void run() throws FileNotFoundException, InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();

        File file = new File(Constants.FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lines = bufferedReader.lines().toList();
        int numberOfThreads = 4;
        int chunkSize = lines.size() / numberOfThreads; //to read by each thread

        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int start = chunkSize * i;
            int end = (i == numberOfThreads - 1) ? lines.size() : (i + 1) * chunkSize;
            List<String> sublist = lines.subList(start, end);
            tasks.add(new FileReadTask(sublist));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Integer>> results = executorService.invokeAll(tasks);
        executorService.shutdown();

        int wordCounter = 0;
        for (Future<Integer> result : results) {
            wordCounter += result.get();
        }
        long endTime = System.currentTimeMillis();

        System.out.println(wordCounter);
        System.out.println("Time spent: " + (endTime - startTime) + " milliseconds");
    }

    static class FileReadTask implements Callable<Integer> {

        private List<String> lines;

        public FileReadTask(List<String> lines) {
            this.lines = lines;
        }

        @Override
        public Integer call() throws Exception {
            int wordCounter = 0;
            for (String line : lines) {
                wordCounter += line.split(" ").length;
            }
            return wordCounter;
        }
    }
}
