package org.example;

import java.util.Objects;

// RequestLine Class
// [First Line] 에 Method, urlPath, queryString 을 추출하는 작업을 함
public class RequestLine {
    // method Variable -> Method(방식)
    private final String method; // GET
    // urlPath Variable -> url(경로) part
    private final String urlPath; // /calculate
    // queryString Variable -> query String
    private String queryString; // operand1=11&operand1=11&operator=*&operand2=55

    // RequestLine Constructor add
    public RequestLine(String method, String urlPath, String queryString) {
        // method, urlPath, queryString Variable 대입
        this.method = method;
        this.urlPath = urlPath;
        this.queryString = queryString;
    }

    // Parameter 에서 인자를 받아주는 Method add
    public RequestLine(String requestLine) {
        // split() : 입력받은 정규표현식 or 특정문자를 기준으로 문자열을 나눠 Array 에 저장하여 Return 함
        // String Type Array 에 tokens Variable Naming 하고 requestLine 안에 String 중 특정 문자를 기준으로 나눠 줌
        String[] tokens = requestLine.split(" ");
        // method = tokens 의 index 0
        this.method = tokens[0];

        // String Array split Variable 로 urlPathTokens 에서 특정 문자를 나눠 준 것을 반환 받음 -> "?" 기준
        String[] urlPathTokens = tokens[1].split("\\?");
        // urlPath = tokens 의 index 1
        this.urlPath = urlPathTokens[0];

        // 만약 urlPathTokens 의 길이가 2면? queryString Variable 은 urlPathTokens 의 index 1 의 value 를 반환해라!
        if(urlPathTokens.length == 2) {
            this.queryString = urlPathTokens[1];
        }
    }

    // Equals 로 String Type 비교
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryString, that.queryString);
    }

    // HashCode 로 정확하게 String Type 이 True 인지 판별
    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryString);
    }
}
