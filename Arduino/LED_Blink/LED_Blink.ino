#include <Arduino.h>//안써도 컴파일에서 자동으로 추가됨
const uint8_t LED_PIN = 13U; // unsigned int 8bit = 13 핀번호 설정 (13번 핀을 led 핀으로 사용)

void setup() {
  // put your setup code here, to run once:
  pinMode(LED_PIN, OUTPUT); //13번 LED핀을 출력으로 사용(전류 5v 보냄)
}

void loop() {
  // put your main code here, to run repeatedly:
  //digitalWrite(LED_PIN, HIGH);
  //delay(500); //1초 대기
  //digitalWrite(LED_PIN, LOW);
  //delay(500UL);// UL = Unsigend Long
  digitalWrite(LED_PIN, HIGH);
  //delay(500); //1초 대기
}
