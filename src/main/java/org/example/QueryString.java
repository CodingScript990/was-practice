package org.example;

// Query String Class -> 피연산자, 연산자를 추출하기 위한 작업
public class QueryString {
    // key variable
    private final String key;
    // value variable
    private final String value;

    // QueryString Method add -> key, value
    public QueryString(String key, String value) {
        // key variable value 대입
        this.key = key;
        // value variable value 대입
        this.value = value;
    }

    // key 값이 있는지를 Check 해주는 method
    public boolean exists(String key) {
        // 이 key value 와 비교할려는 key value 가 동일한 값인지 확인 후 값을 반환!
        return this.key.equals(key);
    }

    // value 값을 반환해주는 method add
    public String getValue() {
        // value 값을 반환 해줌
        return this.value;
    }
}
