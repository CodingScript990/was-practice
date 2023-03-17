package org.example;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;

// HTTP Response Class[Object add]
public class HttpResponse {
    // logger 값 출력하는 것
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    // DataOutputStream Type 으로 outputStream 의 Data 를 받아오기 위한 것
    private final DataOutputStream dos;

    // outputStream 생성자를 받아오는 작업을 위한 것
    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    // Response Header 값 Setting 하는 부분
    public void response200Header(String contentType, int lengthOfBodyContent) {
        //
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n"); // Header Line
            dos.writeBytes("Content-Type: " + contentType + "\r\n"); // Header Line
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n"); // Header Line
            dos.writeBytes("\r\n"); // Blank Line
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    // Response Body 값 Setting 하는 부분
    public void responseBody(byte[] body) {
        //
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
