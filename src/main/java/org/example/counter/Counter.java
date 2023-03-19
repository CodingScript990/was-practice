package org.example.counter;

// Counter -> Thread add
public class Counter implements Runnable {
    // 상태값
    private int count = 0;
    // 상태값 증감식 method add
    public void increment() {
        count++;
    }
    // 상태값 감소식 method add
    public void decrement() {
        count--;
    }
    // 상태값을 받아와 주는 method -> 현재 상태값을 말함
    public int getValue() {
        return count;
    }
    // Run Method add
    @Override
    public void run() {
        // 동기화 방식으로 Thread Condition 해결
        synchronized (this) {
            // 증감식 method call
            this.increment();
            // value 값이 증감할때, 최신 Thread 의 이름과 Value 를 불러오고 출력해줌! => increment counter
            System.out.println("Value for Thread After increment " + Thread.currentThread().getName() + " " + this.getValue()); // result = 1;
            // 감소식 method call
            this.decrement();
            // value 값이 감소할때, 최신 Thread 의 이름과 Value 를 불러오고 출력해줌! => decrement counter
            System.out.println("Value for Thread at last " + Thread.currentThread().getName() + " " + this.getValue()); // result = 0;
        }
    }
}
