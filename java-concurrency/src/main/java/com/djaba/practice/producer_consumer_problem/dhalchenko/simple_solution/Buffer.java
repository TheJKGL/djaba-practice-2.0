package com.djaba.practice.producer_consumer_problem.dhalchenko.simple_solution;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer<T> {
    private Queue<T> values = new LinkedList<>();
    private int maxBufferSize = 10;

    public Buffer() {
    }

    public Buffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public synchronized void put(T value) {
        if (isFull()) {
            try {
                System.out.println("Producer waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        values.add(value);
        notify();
    }

    public synchronized T get() {
        if (isEmpty()) {
            try {
                System.out.println("Consumer waiting...");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        T value = values.poll();
        notify();
        return value;
    }

    private boolean isFull() {
        return values.size() == maxBufferSize;
    }

    private boolean isEmpty() {
        return values.size() == 0;
    }
}
