#include <Stepper.h>
volatile int STEP_PER_REVOLUTION = 2048; //32 * 64=2048
const uint8_t EMERGENCY_BUTTON {0};
volatile int button_count;

//IN4 IN2 IN3 IN1
class Stepper stepping=Stepper(2048,8,10,9,11);
void setup() {
  // put your setup code here, to run once:
  stepping.setSpeed(14);//RPM 14 : 1분에 14번
  Serial.begin(115200UL);
  attachInterrupt(EMERGENCY_BUTTON, stop, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println(F("시계방향 회전"));
  stepping.step(STEP_PER_REVOLUTION / 2);
  delay(1000UL);
  Serial.println(F("반시계방향 회전"));
  stepping.step(-STEP_PER_REVOLUTION);
  delay(1000UL);
  Serial.println(button_count);
}

void stop(){
  //delay안먹힘
  //전역변수 사용하려면 volatile 설정 해야함
  //코드 길게 ㄴㄴ
  STEP_PER_REVOLUTION=0;
  //digitalWrite(8,LOW);
  //digitalWrite(9,LOW);
  //digitalWrite(10,LOW);
  //digitalWrite(11,LOW);
  ++button_count;
}
