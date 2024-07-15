#include <IRremote.hpp>
constexpr uint8_t IR_PIN {8U}; //상수 정의, const: 메소드에도 사용할 수 있음

class IRrecv irrecv(IR_PIN); 
class decode_results result;

void setup() {
  // put your setup code here, to run once:
  pinMode(LED_BUILTIN, OUTPUT);//13번 핀 아웃풋
  Serial.begin(115200UL);
  irrecv.begin(IR_PIN, LED_BUILTIN);//13핀 라이트 켜기
}

void loop() {
  // put your main code here, to run repeatedly:
  uint16_t value=irrecv.decodedIRData.command;
  if(irrecv.decode()){// 버튼 눌리면 true
    //Serial.println(irrecv.decodedIRData.command, HEX);//16진수로

    switch(value){
      case 0xC:
        Serial.println("1번 눌림");
        break;
      case 0x18:
        Serial.println("2번 눌림");
        break;
      default:
        digitalWrite(LED_BUILTIN, LOW);
    }
    irrecv.resume();
    irrecv.start(10);
  }

}
