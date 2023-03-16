package org.example;

import org.example.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// HTTP Request Test Class -> 요청 Test
public class RequestLineTest {

    // TDD 방식
    // Request Create method
    @Test
    void create() {
        // new 생성자에서 requestLine 의 value 값을 추출하여 org.example.RequestLine variable 에 반환해줌!
        // red line 이 뜨는 이유는 생성자에서 받아주는 parameter(인자) 값이 설정 되어 있지 않기 때문에 Error 남
        // parameter(인자) 값을 받아주는 Method 생성 후 출력 잘됨!
        RequestLine requestLine = new RequestLine("GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1");

        // requestLine 의 값을 비교하는데? 비교 대상은 Null 인지 아닌지를 물음!
        assertThat(requestLine).isNotNull();

        // requestLine 의 값을 비교하는데? new RequestLine 생성자 안에 문자열을 가지고 True 인지 False 인지 체크함
        // Error 남! 이유는 Object 와 Object 를 비교할때는 Equals, HashCode 로 비교를 해야함!
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/calculate", "operand1=11&operator=*&operand2=55"));
    }
}
