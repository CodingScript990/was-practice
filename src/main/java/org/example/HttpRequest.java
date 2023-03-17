package org.example;

import java.io.BufferedReader;
import java.io.IOException;

// HTTP Request Class
public class HttpRequest {
    // requestLine variable add
    private final RequestLine requestLine;

    // Constructor Http Request Method add
    // parameter 는 BufferedReader Type br variable 로 받아줌
    public HttpRequest(BufferedReader br) throws IOException {
        // requestLine variable 초기값 설정
        // 즉, Protocol First Line 을 받아오겠다는 의미임!
        this.requestLine = new RequestLine(br.readLine());
    }

    // HTTP Request Get Method add
    public boolean isGetRequest() {
        // requestLine 이 get 요청이 맞는지 되묻는 것! -> True 인지 체크 Method 실행
        return requestLine.isGetRequest();
    }

    // mathPath method add
    public boolean matchPath(String requestPath) {
        // requestPath 가 맞는가를 재확인 필요성!
        return requestLine.matchPath(requestPath);
    }

    // getQueryStrings method add
    public QueryStrings getQueryStrings() {
        // QueryStrings 가 일치하면 값을 getQueryStrings method 에게 반환해줘라!
        return requestLine.getQueryStrings();
    }
}
