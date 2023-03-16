package org.example;

import java.io.IOException;

// Web Application
// GET /calculate?operand1=11&operator=*&operand2=55
public class Main {
    // throws IOException 을 사용하여 예외처리 발생시키는 작업까지 설정해줌
    public static void main(String[] args) throws IOException {
        // Client Server Check
        // Server start
        new CustomWebApplicationServer(8080).start();
    }
}