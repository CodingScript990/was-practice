package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// QueryStringsTest Test Class -> 여러개의 Object[Key, Value]를 Test
public class QueryStringsTest {
    //
    @Test
    void createTest() {
        // QueryStrings 생성자에서 QueryString 의 key,value 가 여러개 인 형태를 가짐
        // List<QueryString>
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55");

        // queryStrings 가 Null 값이 아닌지 Test
        assertThat(queryStrings).isNotNull();
    }
}
