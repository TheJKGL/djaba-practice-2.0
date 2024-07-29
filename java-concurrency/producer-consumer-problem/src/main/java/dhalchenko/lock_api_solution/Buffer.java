package dhalchenko.lock_api_solution;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.*;

public class Buffer<T> {
    private Queue<T> values = new LinkedList<>();
    private int maxBufferSize = 10;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public Buffer() {
    }

    public Buffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    @SneakyThrows
    public void put(T value) {
//        while (isFull()) {
//        }

        writeLock.lock();
        values.add(value);
//        isFull.signal();
        writeLock.unlock();
    }

    @SneakyThrows
    public T get() {
        while (isEmpty()) {
//            isEmpty.await();
        }
        readLock.lock();
        T value = values.poll();
//        isEmpty.signal();
        return value;
    }

    private boolean isFull() {
        return values.size() == maxBufferSize;
    }

    private boolean isEmpty() {
        return values.size() == 0;
    }
}
