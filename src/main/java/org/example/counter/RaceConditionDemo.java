package org.example.counter;

// RaceConditionDemo Class -> Thread Test
public class RaceConditionDemo {
    public static void main(String[] args) {
        /**
         * Servlet Object 를 싱글톤으로 관리하면 발생하는 문제들을 보여줌 그중 stateful 하게 설계하면 안되는 것을 보여주는 예시임!
         */
        // Counter Constructor add -> 싱글톤에서 하나의 Object 를 Thread 에 공유할때 문제 발생 예시!
        // Thread Condition 을 해결하기 위해서는 동기화 처리를 해주는 작업을 하면 됨 -> run method 에서 수정
        Counter counter = new Counter();
        // Thread Constructor add Thread 1
        Thread t1 = new Thread(counter, "Thread-1");
        // Thread Constructor add Thread 2
        Thread t2 = new Thread(counter, "Thread-2");
        // Thread Constructor add Thread 3
        Thread t3 = new Thread(counter, "Thread-3");

        // Thread's Start method
        t1.start();
        t2.start();
        t3.start();

        /**
         * 레이스 컨디션(Race Condition)
         * - 공유 자원에 대해 여러 개의 프로세스가 동시에 접근하기 위해 경쟁하는 상태를 말함 -> 즉 원치 않는 결과물을 보게 된다는 의미임!
         * - 프로세스들이 경쟁하는 것을 이용하여 관리자 권한을 얻는 공격을 Race Condition Attack 이라 함
         * - Thread 가 안전하지 않음
         
         * 레이스 컨디션 공격(Race Condition Attack)
         * - 소유자가 root 이고, SetUID 가 설정된, 임시 File 을 생성하는 Program 임
         */
    }
}
