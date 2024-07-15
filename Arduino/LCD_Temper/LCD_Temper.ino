#include <LiquidCrystal_I2C.h>
#include "DHT.h"

class LiquidCrystal_I2C lcd(0x27, 16,2);
class DHT dht(A0, 11);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  dht.begin();
  lcd.init();
  lcd.backlight();
  lcd.home();
}

void loop() {
  // put your main code here, to run repeatedly:
  lcd.clear();
  if(dht.read()){
    float temperature=dht.readTemperature();
    float percentHumidity {dht.readHumidity()};
    Serial.print(F("온도 : "));
    Serial.println(temperature);
    Serial.print(F("습도 : "));
    Serial.println(percentHumidity);
    lcd.setCursor(0,0);
    lcd.print("TEMPER :");
    lcd.print(temperature);
    lcd.setCursor(0,1);
    lcd.print("HUMID :");
    lcd.print(percentHumidity);
    delay(100UL);
  }
}
