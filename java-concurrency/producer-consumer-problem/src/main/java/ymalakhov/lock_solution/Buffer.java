package ymalakhov.lock_solution;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer<T> {
    private Queue<T> values = new LinkedList<>();
    private int maxBufferSize = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition isFull = lock.newCondition();
    private final Condition isEmpty = lock.newCondition();


    public Buffer() {
    }

    public Buffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public void put(T value) {
        lock.lock();
        System.out.println("put lock");
        try {
            while (isFull()) {
                System.out.println("Producer waiting...");
                isFull.await();
            }
            values.add(value);
            isEmpty.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            System.out.println("put unlock");
        }
    }

    public T get() {
        lock.lock();
        try {
            while (isEmpty()) {
                System.out.println("Consumer waiting...");
                isEmpty.await();
            }
            T value = values.poll();
            isFull.signal();
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return values.size() == maxBufferSize;
    }

    private boolean isEmpty() {
        return values.size() == 0;
    }
}
