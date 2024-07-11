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
