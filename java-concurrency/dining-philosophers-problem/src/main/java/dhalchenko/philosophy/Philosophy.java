package dhalchenko.philosophy;

import dhalchenko.fork.Fork;
import lombok.SneakyThrows;

public class Philosophy implements Runnable {
    private Fork leftFork;
    private Fork rightFork;
    private int eatCounter = 0;

    public Philosophy(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            doAction("thinking");
            leftFork.acquire();
            Thread.sleep(100);
            rightFork.acquire();
            eatCounter++;
            doAction("eating times: " + eatCounter + " ");
            leftFork.release();
            rightFork.release();
        }
    }

    @SneakyThrows
    private void doAction(String action) {
        System.out.println(Thread.currentThread().getName() + " - " + action + "...");
        Thread.sleep(1000);
    }
}
