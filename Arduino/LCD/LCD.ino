#include <LiquidCrystal_I2C.h>
//0x27 0x3F //기본적으로 i2c 핀에 연결되어 잇어야 함
LiquidCrystal_I2C lcd(0x27, 16, 2); //디스플레이 사이즈 = 16x2

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  lcd.init();//display 초기화
  lcd.backlight();// 백라이트 켜기
  lcd.home();//(0,0) 0colum, 0row
  lcd.print("Welcome");
  lcd.setCursor(0,1);//0colum, 1row
  lcd.print("yujin");
}

void loop() {
  // put your main code here, to run repeatedly:

}
