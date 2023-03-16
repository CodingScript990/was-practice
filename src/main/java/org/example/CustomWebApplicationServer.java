package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


// Web Application Server add -> Tomcat(Server) 을 만들고 있음
public class CustomWebApplicationServer {

    // port number 초기화 해주는 작업
    private final int port;
    // Logger 를 선언하여, CustomWebApplicationServer 에서 일어나는 것을 logger 를 찍어서 체크하기 위함임
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    // Server 가 실행될 때 port number 가 add 가 되었는가를 체크
    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    // Web application 이 Start 하게 만드는 Method add
    /**
     *  [예외처리]
        1. throws IOException : 익셉션(예외)가 발생하면 해당 Class 에서 벗어난다는 의미임
        2. throw 는 던져버린다는 말로, 즉 말일 감싸는 try ~ catch 문이 있다면 튕겨져 나온 예외를 핸들링 할 수 있다는 의미임!
        3. try ~ catch : try ~ catch 를 이용하기 위해서는 throws Exception Type 으로 선언된 함수는 Exception 이 깐깐할 경우에 해당 예외를 처리할 수 있는 try ~ catch 영역 안에서 사용되야함
        4. Exception : 걸리는 부분은 컴파일 부분에서 부터 에러가 남, 다른 에러 함수는 컴파일은 되지만, 실행 부분에서 에러가 남을 알 수 있음
        5. FileNotFoundException : IOException 으로 바꿔도 에러가 안남 즉, IOException 이 조상이기 때문에 자손이 에러를 잡는 부분을 부모가 다 잡아 내기 때문임, 조상의 포인터로 자손의 Instance 를 가리킬 수 있어야 함
        6. throw e; 를 선언하면 Error 를 발생 시켜 main() 호출한 쪽에서 책임지게 만듬

     * [Checked vs Unchecked 예외처리]
        1. Checked Exception
         - 반드시 예외를 처리해야함
         - 컴파일 단계를 거침
         - Roll-Back 하지 않음
         - Exception 의 상속받는 하위 Class 중 Runtime Exception 을 제외한 모든 예외를 말함
          -> IOException, SQLException
        2. Unchecked Exception
         - 명시적인 처리를 강제하지 않음
         - 실행단계를 거침
         - Roll-Back 함
         - Runtime Exception 하위 Class 예외임
          -> NullPointerException, IllegalArgumentException, IndexOutOfBoundException, SystemException
     */
    public void start() throws IOException {
        // Server Socket add => check!(port number)
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            // 제대로 CustomWebApplicationServer 가 실행되고 있고, 실행 port number 까지 logger 로 찍어서 Check
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            // Client Socket add -> client 가 접속할때
            Socket clientSocket;

            // Client 를 기다리는 작업 -> client 가 접속을 하였는가? 즉, 로그인 하였는가?를 체크(logger 로 찍어서 client 접속을 체크!)
            // server socket 에서 client 가 접속했는지 안했는지 체크하는 작업
            logger.info("[CustomWebApplicationServer] waiting for client.");

            // client socket 이 생성되어 server socket 도 비교했을때, 값이 null 인지 아닌지 체크!
            while ((clientSocket = serverSocket.accept()) != null) {
                // 여기까지 진행되었다면? -> client 가 제대로 생성되고 잘 접속이 되었다는 의미임!
                // logger 로 client 접속이 문제 없음을 알림

                /**
                 * [accept method]

                   1. Server 에서도 Server Socket 을 이용하여 Port 를 열어놓고 Client 와의 Connect 되는 것을 기다리는데까지만 하는 역하을 말함!
                   2. Client 와 Connect 가 되는 순간 Server 에서 Client Socket 을 add 해서 Client 측의 Client Socket 과 통신을 한다는 의미!
                 */
                // client 가 잘 접속했다면, logger 에 client connected! 메세지가 찍힘!
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step 1 - User 요청을 Main Thread 가 처리하도록 함
                 * 프로토콜을 어떻게 파싱하고 어떻게 처리할지를 학습하는 거라고 생각하면 됨!
                */

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
                    String line;

                    // While 문에서 String Type variable line 이 Buffer 를 사용하여 readLine method 를 이용하여 "" 값이 아닌지 비교하여?
                    while ((line = br.readLine()) != "") {
                        // line 의 값을 출력해줘라! -> info 를 줄력해줘!
                        System.out.println(line);
                    }
                }
            }
        }
    }
}
