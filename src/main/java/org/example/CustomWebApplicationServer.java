package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Web Application Server add -> Tomcat(Server) 을 만들고 있음
public class CustomWebApplicationServer {

    // port number 초기화 해주는 작업
    private final int port;
    // Thread Pool 을 사용하므로써 조금 더 안정적인 상태를 만들 수 있는 작업을 하기 위한 작업임
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
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

                /**
                 * Step 2 - User 요청이 들어올때마다 Thread 를 새로 생성해서 User 요청을 처리하도록 함
                 */
                // new Thread Constructor add -> start method(clientSocket)
                // Memory 생성이 계속되기에 별로 좋지 않는 로직임, 그래서 따로 보완해주는 작업을 실시해줘야함
                // new Thread(new ClientRequestHandler(clientSocket)).start(); // thread-1

                /**
                 * Step 3 - Thread Pool 을 적용해 안정적인 Service 가 가능하도록 함
                 */
                // Thread Pool 의 여유가 있을 때 Task 를 수행하기 위한 작업 -> pool-1-thread-1
                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        }
    }
}
