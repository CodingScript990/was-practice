package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// Client Request Handler Class -> Thread Add
public class ClientRequestHandler implements Runnable{
    // logger 를통해서 ClientRequestHandler class 가 잘 동작하는지 log 찍어서 체크하기 위함임
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);
    // ClientSocket add
    private final Socket clientSocket;

    // Constructor add -> socket client
    public ClientRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        /**
         * Step 2 - User 요청이 들어올때마다 Thread 를 새로 생성해서 User 요청을 처리하도록 함
         * -> 단점은 run 할때마다, Thread 생성되어 효율이 떨어짐 -> Step 3에서 보완해줌
         */

        // logger 를 통해서 현재 실행중인 class 가 동작한다는 것을 output 으로 체크
        logger.info("[ClientRequestHandler] new client {} started.", Thread.currentThread().getName());
        // Client Input & Output Connect 작업
        // try ~ catch 문으로 calling
        try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
            /**
             * [BufferedReader]
             * - Buffer 를 사용하여 read & write 를 하는 Function 임
             * - Buffer 를 사용하지 않는 입력은, 키보드의 입력이 키를 누르는 즉시 바로 프로그램에 전달됨
             * - Buffer 를 사용하는 입력은, 키보드의 입력이 있을 때마다 한 문자씩 Buffer 로 전송함, Buffer 가 가득 차거나 or 개행 문자가 나타나면 Buffer 의 내용을 한 번에 프로그램에 전달함

             * [BufferedReader Class 의 Main Function's]
             * void - close(), 입력 Stream 을 닫고, 사용하던 자원을 해제
             * void - mark(int, readAheadLimit), Stream 의 현재 위치를 마킹
             * int - read(), 한 글자만 읽어 정수형으로 반환
             * String - readLine(), 한 줄을 읽음
             * boolean - ready(), 입력 Stream 이 사용할 준비가 되었는지 확인

             * [Stream]
             * - 자료의 입출력을 도와주는 중간 매개체 임
             * - 응용 프로그램과 입출력 장치를 연결하는 S/W Module
             * - 단방향 => 1. 입력 & 출력 다 안됨
             * -          2. 둘중 하나만 가능함
             * -          3. 선입선출 구조임

             * [입력 Stream]
             * - 입력장치로부터 java program 으로 Data 를 전달 하는 것을 말함
             * - 입력 String Stream 은 Reader 가 붙어있음
             * - 입력 Byte Stream 은 InputStream 이 붙어 있음
             * - 컴퓨터와 키보드를 연결하는 것은 System.in

             * [출력 Stream]
             * - 출력 장치로 Data 출력을 말함
             * - 출력 String Stream 은 Writer 가 붙어 있음
             * - 출력 Byte Stream 은 OutPutStream 이 붙어 있음
             * - 컴퓨터의 정보를 모니터로 출력하는 것은 System.out

             * [Buffer]
             * - 임시 저장 공간을 의미함
             * - A와 B가 서로 입출력을 수행할려는데, 속도차이를 극복하기 위해 사용하는 임시 저장 공간이라는 의미임

             * [Scanner]
             * - 띄어쓰기와 개행문자를 경계로 하여 입력 값을 인식함
             * - 따로 가공할 필요가 없어 편리함
             * - 단점 : 많은 입력을 필요로 할 경우 성능상 좋지 못한 결과를 초래함!(주의)

             * [BufferedWriter]
             * - 일반적으로 출력을 할 때, System.out.println(""); 을 사용함
             * - 적은 양의 출력에서는 편리함, 큰성능 차이 없이 사용가능
             * - 많은 양의 출력을 할 때는 입력과 동일하게 Buffer 를 사용하는 것이 좋음

             * [BufferedWriter Class 의 Main Function's]
             * void - close(), Stream 닫음, 닫기 전 flush()
             * void - flush(), Stream 을 비움
             * void - newLine(), 개행 문자 역할
             * void - write(char[] buf, int offset, int length), Buffer offset 위치부터 length Size 만큼 write
             * void - write(int c), 한 글자 쓰기
             * void - write(String s, int offset, int length), 문자열에서 offset 에서부터 일정 길이만큼 write

             * [String]
             * - String 은 불변 속성을 갖고 있음을 말하고, StringBuffer/StringBuilder 는 그렇지 않다는 의미임
             * - String 이 불변성을 갖는다는 의미는 concat 이나 + 연산을 통해 값을 변경하면, 원래 기존의 String memory 에서 value 가 바뀌는 것이 아니라, 기존의 String 에 들어있던 Value 를 버리고 New Value 를 재할당하게 됨, 처음 할당한 String 의 memory 영역은 Garbage(쓰레기) 로 남아있다가 GarbageCollection 에 의해 없어짐

             * [StringBuffer]
             * - 동기화를 지원하여 Multi Thread 환경에서 안전함

             * [StringBuilder]
             * - StringBuffer&StringBuilder 는 가변성을 가지기 때문에, append(), delete() 등 동일 Object 내에서 String 을 변경하는 것이 가능함, String 의 추가, 수정, 삭제가 빈번하게 발생할 경우 사용해야함
             * - 동기화를 지원하지 않아 Multi Thread 환경에 사용하기 적합하지 않음
             * - 동기화를 지원하지 않기에 단일 Thread 에서는 성능이 좋음

             * [StringBuilder 주요 Method]
             * StringBuilder append(String s) : StringBuilder 뒤에 값을 붙임
             * StringBuilder delete(int start, int end) : 특정 index 부터 index 까지를 삭제
             * StringBuilder insert(int offset, any primitive of a char[]) : 문자를 삽입함
             * StringBuilder replace(int start, int end, String s) : 일부를 String Object 로 치환
             * StringBuilder reverse() : 순서를 뒤집음
             * StringBuilder setCharAt(int index, char ch) : 주어진 문자를 치환
             * StringBuilder indexOf(String s) : 값이 어느 index 에 들어있는지 확인
             * StringBuilder subString(int start, int end) : start 와 end 사이의 값을 잘라옴
             */
            // Buffer 를 읽어오는 생성자를 사용하는데? 변수를 br 로 설정하고,
            // new 생성자를 호출해서, 그안에 new 생성자에서 입력 StreamReader 를 활용하여 in(입력)
            // 문자열을 입력 Stream 으로 변환 시킨다는 의미
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            // 출력 Data Stream 생성자 Value 값을 dos variable 에 반환
            DataOutputStream dos = new DataOutputStream(out);

            // HTTP 의 값을 체크 해보는 작업
            // String Type line variable 초기화 작업
//                    String line;

            // While 문에서 String Type variable line 이 Buffer 를 사용하여 readLine method 를 이용하여 "" 값이 아닌지 비교하여?
            // queryString Test 라서 이미 QueryStringLine 으로 작업을 하였기에 제거
//                    while ((line = br.readLine()) != "") {
//                        // line 의 값을 출력해줘라! -> info 를 줄력해줘!
//                        System.out.println(line);
//                    }
            // HTTP Request Constructor 에 br 를 받아오는 작업! -> First Line call
            HttpRequest httpRequest = new HttpRequest(br);

            // if 조건문으로 httpRequest 의 값과 calculate path 의 값이 True 인가를 비교
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                // httpRequest 에서 getQueryStrings method 를 가져오는 작업!
                QueryStrings queryStrings = httpRequest.getQueryStrings();

                // 피연산자 1의 값을 가진 variable add -> Integer Type 으로 변환
                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                // 피연산자 2의 값을 가진 variable add -> Integer Type 으로 변환
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));
                // 연산자의 값을 가진 variable add
                String operator = queryStrings.getValue("operator");

                // calculator 생성자에서 calculate 속성을 이용하여 new positiveNumber 생성자 Type 을 이용해 operand1, operator, operand2 를 구할 수 있도록 셋팅해줌!
                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                // byte Array 는 result 값이 getBytes method 로 부터 전달 받아 온다는 것
                // string type -> byte type 으로 값을 변환 해준다는 의미
                byte[] body = String.valueOf(result).getBytes();

                // HTTP Response 생성자에서 DataOutputStream 을 calling 하는 작업
                HttpResponse response = new HttpResponse(dos);

                // Header 를 통해서 Content 의 length 를 작업해줌 -> Response
                // Client 와 통신을 하기 위함
                response.response200Header("application/json", body.length);

                response.responseBody(body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
