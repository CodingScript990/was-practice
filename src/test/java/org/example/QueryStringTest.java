package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// Query String Test Class
// Key : Value 로 각각 info 를 추출하기 위함임! -> 피연산자, 연산자 만 추출해서 계산을 해줘야 하기 때문!
public class QueryStringTest {
    // Create Test Method add
    // operand1=11&operator=*&operand2=55
    @Test
    void createTest() {
        // QueryString Type 의 variable 을 add 하는데, new QueryString 생성자의 value 를 대입 해줌
        // key : value 의 값을 추출 하기 위함임
        // 단일 QueryString 으로 추출할때는 가능함 -> 여러개 일경우는 List Type 을 활용해야함!
        // QueryString 은 key, value 가 하나일때 가지는 Object 임!
        // List<QueryString> 을 가지면 결국 Object's 들을 여러개 들고 온다는 의미임
        QueryString queryString = new QueryString("operand1", "11");

        // queryString value 의 값이 Null 이 아닌지 Null 인지 비교
        assertThat(queryString).isNotNull();
    }
}
