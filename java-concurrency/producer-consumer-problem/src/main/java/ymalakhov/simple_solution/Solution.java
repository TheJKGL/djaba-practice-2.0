package ymalakhov.simple_solution;

import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.run();
    }

    public void run() {
        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }

    private class Producer extends Thread {
        private final Buffer buffer;
        int counter = 0;

        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (buffer) {
                    if (buffer.isFull()) {
                        try {
                            buffer.wait();
                            System.out.println("Producer is waiting");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    String newMessage = buffer.push("New message №" + counter++);
                    System.out.println("Produce message: " + newMessage);

                    buffer.notify();
                }
            }
        }
    }

    private class Consumer extends Thread {
        private final Buffer buffer;

        public Consumer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (buffer) {
                    if (buffer.isEmpty()) {
                        try {
                            buffer.wait();
                            System.out.println("Consumer is waiting");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    String message = buffer.pop();
                    System.out.println("Consume message: " + message);
                    buffer.notify();
                }
            }
        }
    }

    private class Buffer {
        private final LinkedList<String> messageBuffer;
        private final int maxSize;

        public Buffer(int size) {
            this.maxSize = size;
            this.messageBuffer = new LinkedList<>();
        }

        public boolean isFull() {
            return messageBuffer.size() == maxSize;
        }

        public boolean isEmpty() {
            return messageBuffer.isEmpty();
        }

        public String pop() {
            return messageBuffer.removeLast();
        }

        public String push(String message) {
            messageBuffer.add(message);
            return message;
        }
    }
}
