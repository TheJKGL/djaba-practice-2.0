package ymalakhov;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadSolution {

    public static void main(String[] args) throws Exception {
        File file = new File(Constants.FILE_PATH);
        int chunk = 4;
        long chunkSize = file.length() / 4;

        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < chunk; i++) {
            tasks.add(new FileReadTask(file, chunkSize * i, chunkSize));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(chunk);
        var results = executorService.invokeAll(tasks);
        int wordCounter = 0;
        for (Future<Integer> task : results) {
            wordCounter += task.get();
        }
        System.out.println(wordCounter);
    }

    static class FileReadTask implements Callable<Integer> {

        public FileReadTask(File file, long startLine, long lengthToRead) {
            this.file = file;
            this.startLine = startLine;
            this.lengthToRead = lengthToRead;
        }

        private File file;
        private long startLine;
        private long lengthToRead;

        @Override
        public Integer call() throws Exception {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            //randomAccessFile.seek(startLine);
            byte[] bytes = new byte[(int) lengthToRead + 1];
            randomAccessFile.readFully(bytes, (int) startLine, (int) lengthToRead);
            return new String(bytes).split(" ").length;
        }
    }
}
