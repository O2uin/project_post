#include <MFRC522Constants.h>
#include <MFRC522Debug.h>
#include <MFRC522Driver.h>
#include <MFRC522DriverI2C.h>
#include <MFRC522DriverPin.h>
#include <MFRC522DriverPinSimple.h>
#include <MFRC522DriverSPI.h>
#include <MFRC522Hack.h>
#include <MFRC522v2.h>
#include <deprecated.h>
#include <require_cpp11.h>

MFRC522DriverPinSimple sda_pin(53);
MFRC522DriverSPI driver(sda_pin);
MFRC522 mfrc522(driver);
byte read_card_uid[4];
const String MASTERCARD_UID{String("F14C751B")};
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  mfrc522.PCD_Init();
}

void loop() {
  // put your main code here, to run repeatedly:
  if(!mfrc522.PICC_IsNewCardPresent()) return;
  if(!mfrc522.PICC_ReadCardSerial()) return;

  String tagID {String("")};// 빈 문자열 String 클래스 객체
  for(uint8_t i=0u; i<4;i++){
    tagID +=String(mfrc522.uid.uidByte[i], HEX);
  }

  tagID.toUpperCase(); //대문자 변환
  if(tagID.equals(MASTERCARD_UID)){
    Serial.println(F("카드 일치"));
    //뭐 알아서 쓰라는 거임?
  }else{
    Serial.println(F("카드 x"));
  }
}
