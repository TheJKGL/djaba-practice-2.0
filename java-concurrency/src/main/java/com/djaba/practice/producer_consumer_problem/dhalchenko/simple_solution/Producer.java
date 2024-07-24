package com.djaba.practice.producer_consumer_problem.dhalchenko.simple_solution;

import lombok.SneakyThrows;

public class Producer extends Thread {
    private final Buffer<String> buffer;

    public Producer(Buffer<String> buffer) {
        this.buffer = buffer;
    }

    @SneakyThrows
    @Override
    public void run() {
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            sleep(1000);
            String message = "New message №" + counter++;
            System.out.println("Produce new message: " + message);
            buffer.put(message);
        }
    }
}
