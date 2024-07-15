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

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  mfrc522.PCD_Init();//초기화
  MFRC522Debug::PCD_DumpVersionToSerial(mfrc522, Serial);
  Serial.println(F("Scan PICC to see UID, SAK, type and data"));
}

void loop() {
  // put your main code here, to run repeatedly:
  if(!mfrc522.PICC_IsNewCardPresent()) return;
  if(!mfrc522.PICC_ReadCardSerial()) return;
  MFRC522Debug::PICC_DumpToSerial(mfrc522, Serial, &(mfrc522.uid));
}
