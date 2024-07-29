package ymalakhov.lock_solution;

import lombok.SneakyThrows;

public class Consumer extends Thread {
    private final Buffer<String> buffer;

    public Consumer(Buffer<String> buffer) {
        this.buffer = buffer;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            sleep(1000);
            System.out.println("Consumer {%S} consume new message: %s".formatted(Thread.currentThread().getName(), buffer.get()));
        }
    }
}
