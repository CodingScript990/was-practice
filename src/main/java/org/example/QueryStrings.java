package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// QueryStrings Class -> 여러개의 key, value 를 생성하주는 Class
public class QueryStrings {
    // List<QueryString> 인 new ArrayList 의 variable add
    private List<QueryString> queryStrings = new ArrayList<>();

    // QueryStrings Constructor add
    public QueryStrings(String queryStringLine) {
        // QueryStringLine 을 해줄건데, 나누는 기준은? -> 어떤 String Type?
        // 기준을 나눠서 key, value 를 추출하는 것 -> 각 key,value 묶음으로 나눠줌!
        // operand1[key]=11[value] &[추출] operator=* &[추출] operand2=55
        // 1. QueryStringLine 이 들어오면? 일단 각각의 Key, value 값을 묶음으로 나눠주고
        String[] queryStringTokens =  queryStringLine.split("&");
        // key 값과 value 값을 이제 추출하는 작업!
        // Arrays 를 이용하여 queryStringTokens 를 불러옴 -> 추출하기 위함임
        // 2-1. 1차 QueryStringLine 에서 특정 문자열을 추출하였고 2차로 각 key 값과 value 들이 묶음으로 되어있는 상태가 아닌, key 값, value 값이 따로 분리가 되도록 작업해줌
        Arrays.stream(queryStringTokens)
                // forEach 반복문을 사용함
                .forEach(queryString -> {
                    // queryString 에서 =(String)을 추출해냄!
                    // operand1[key] =[추출] 11[value] operator =[추출] * operand2 =[추출] 55
                    // operand1 11 operator * operand2 55 이렇게 [key:value]가 나옴
                    // 2-2. QueryStringTokens 에서 2차 마지막으로 특정 문자열을 추출하여 key, value 값이 각각 나눠지도록 만듬
                    String[] values = queryString.split("=");

                    // if 문으로 values 의 값 길이가 2가 아니면?
                    if (values.length != 2) {
                        // throw new IllegalArgumentException 을 사용하여, Error Message 남김
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열 입니다.");
                    }
                    // 해당 QueryStrings 에게 index[0],index[1]을 초기값을 설정해줌!
                    // 3. 2차로 추출된 것을 기반으로 index[0], index[1]로 queryStrings 에게 값을 추가해줌
                    queryStrings.add(new QueryString(values[0], values[1]));
                });
    }

    // queryStrings getValue method -> key value 를 queryString 에서 추출하는 작업[operand1]
    public String getValue(String key) {
        // queryStrings 의 값을 불러오는데, filter 를 할거야! filter 대상은 QueryString 인데? QueryString 에서 key 값만 가져올려해! 근데 해당 key value 가 있니? 그리고 가져온 것을 mapping 할건데? Mapping 을 QueryString 에서 key 값이 있으면 getValue 에 value 값을 넣어줄거야! 그리고 찾을건데 첫번째 object 를 들고 올것이고, 값을 비교를 했을때, null 이되면 안돼!
        return this.queryStrings.stream()
                .filter(queryString -> queryString.exists(key))
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }
}
