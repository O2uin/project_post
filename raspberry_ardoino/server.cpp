 #include <iostream> //#inlcude <stdio.h> : c
 #include <cstring> //#include <string.h> : c
 #include <vector> //vector -> python : list
 #include <sys/socket.h> //socket(), bind(), listen(), accept()
 #include <arpa/inet.h> //192.168.0.x -> network 값들로 변환해주는 함수 존재
 #include <pthread.h> //thread 를 사용하려는 함수가 있음
 #include <array> //c언어의 배열을 클래스화 -> C++ Library
 #include <semaphore.h> //mutex 사용
 #include "SerialPort.cpp"

 void* task(void*);// main thread
 void* taskSerial(void*); //Serial로 넘어오는 값들을 이 thread가 처리
 static pthread_mutex_t mutex; //키가 mutex : binary semaphore
 static std::vector<int> client_socket_fds; //여러개의 클라이언트를 벡터로 처리
 static int serial_port = 0; //시리얼 포트 저장용 변수
 static std::array<char, BUFSIZ> tcp_message;
 static std::array<char, BUFSIZ> serial_message;

 int main(int argc, const char* argv[]){
     int server_sock_fd{0}; //c++ uniform initializer
     int client_sock_fd{0}; //c++ uniform initializer
     struct sockaddr_in server_addr;
     struct sockaddr_in client_addr;
     pthread_t pid{0UL};
     if(argc!=2){
         std::cout<<"./SERVER 9999" << std::endl;
         exit(EXIT_FAILURE); //exit(1)
     }
     serial_port=serialport_init("/dev/ttyACM0",115200,nullptr);//시리얼 포트
     if(serial_port==-1){
         std::cout<<"Serial Port error()"<<std::endl;
         exit(EXIT_FAILURE);
     }

     std::cout<<"Serial port is connected ..."<<std::endl;

     //mutex 초기화
     pthread_mutex_init(&mutex, nullptr); //mutex

     server_sock_fd=socket(PF_INET/*IPv4*/,SOCK_STREAM/*TCP protocol*/,0);
     if(server_sock_fd==-1){
         std::cout<<"socket() error" <<std::endl;
         exit(EXIT_FAILURE);
     }
     memset(&server_addr, 0, sizeof server_addr);
     memset(&client_addr, 0, sizeof client_addr);
     //서버 주소 및 프로토콜 등 값 대입
     server_addr.sin_family=AF_INET;
     server_addr.sin_addr.s_addr=htonl(INADDR_ANY);
     server_addr.sin_port=htons(atoi(argv[1])); //"9999"-> 9999
     const int bind_state=bind(server_sock_fd, (const struct sockaddr*)&server_addr,
             sizeof server_addr);
     if(bind_state==-1){
         std::cout<<"bind() error"<<std::endl;
         exit(EXIT_FAILURE);
     }

     const int listen_state=listen(server_sock_fd, 5);
     if(listen_state==-1){
         std::cout<<"listen() error"<<std::endl;
         exit(EXIT_FAILURE);
     }

     //온도 습도 값들을 출력하는 thread 생성
     pthread_create(&pid, nullptr, taskSerial, static_cast<void*>(nullptr));
     pthread_detach(pid); //taskSerial join()
     socklen_t client_sock_addr_size {0ul};
     while(true){
         client_sock_addr_size=sizeof client_addr;
         client_sock_fd=accept(server_sock_fd, (struct sockaddr*)&client_addr,
                 (socklen_t*)&client_sock_addr_size);
         if(client_sock_fd==-1){
             std::cout<<"accept() error"<<std::endl;
             exit(EXIT_FAILURE);
         }
         pthread_mutex_lock(&mutex);//lock
         client_socket_fds.push_back(client_sock_fd); //vector 자료형에 정숫값 저장
         pthread_mutex_unlock(&mutex); //unlock

         pthread_create(&pid, nullptr, task, static_cast<void*>(&client_sock_fd));
         pthread_detach(pid);//join()
         std::cout<<"Connected from client IP: "<<inet_ntoa(client_addr.sin_addr)
             <<std::endl;
     }             

     serialport_close(serial_port);
     close(server_sock_fd);
     pthread_mutex_destroy(&mutex);//destroy mutex 리소스 반납
     return 0;

 }

 void* taskSerial(void *arg){
     while(true){
         const int serial_state{serialport_read_until(serial_port,
                     serial_message.data(), '\n')};
         if(!serial_state){//0이면 성공
             //저장되어 있는 모든 클라이언트들에게 데이터(온도, 습도값) 전송
             for(auto fd:client_socket_fds){
                 //vector에 저장된 client socket fd
                 pthread_mutex_lock(&mutex);
                 write(fd,serial_message.data(), strlen(serial_message.data()));
                 pthread_mutex_unlock(&mutex);
             }
         }
     }

     return nullptr;
 }

 void* task(void* arg){
     const int clnt_sock_fd = *(static_cast<int*>(arg));// (int*)
     int tcp_string_length{0};
     while((tcp_string_length = read(clnt_sock_fd,tcp_message.data(),BUFSIZ))){

         pthread_mutex_lock(&mutex);
         serialport_write(serial_port, tcp_message.data());
         pthread_mutex_unlock(&mutex);
     }
     //클라이언트가 종료했을 때
     pthread_mutex_lock(&mutex);
     for(auto it {client_socket_fds.cbegin()}; it != client_socket_fds.cend(); ++it){
         if(*it==clnt_sock_fd){
             client_socket_fds.erase(it);
             break;
         }
     }
     pthread_mutex_unlock(&mutex);
     std::cout<<"A client has lefted !"<<std::endl;
     std::cout<<"client socket has been close!"<<std::endl;
     close(clnt_sock_fd);

     return nullptr;
 }
//서버 cpp 실행: 파일 생성 후 g++ server.cpp -o SERVER -lpthread
//서버 연결 -> ./SERVER 9999
//라즈베리파이 종료 sudo poweroff
