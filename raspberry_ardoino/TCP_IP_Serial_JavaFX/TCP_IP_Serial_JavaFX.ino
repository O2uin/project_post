#include "DHT.h"

enum CONTROL_PINS{
  TEMPER_HUMID =A0,
  RED_LED=8U,
  BLUE_LED
};

DHT dht(TEMPER_HUMID, 11);
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  //Serial.begin(9600UL);
  dht.begin();
  
  pinMode(TEMPER_HUMID, INPUT);
  pinMode(RED_LED, OUTPUT);
  pinMode(BLUE_LED, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(dht.read()){
    const float temperature{dht.readTemperature()};
    const float humidity {dht.readHumidity()};
    
    if(Serial.available()){
      String in_comming_string{Serial.readStringUntil('\n')};
      in_comming_string.trim(); //\r \n 잘라냄
      if(in_comming_string.equals("RED_LED_ON")){
        digitalWrite(RED_LED, HIGH);
      }else if(in_comming_string.equals("RED_LED_OFF")){
        digitalWrite(RED_LED,LOW);
      }else if(in_comming_string.equals("BLUE_LED_ON")){
        digitalWrite(BLUE_LED, HIGH);
      }else if(in_comming_string.equals("BLUE_LED_OFF")){
        digitalWrite(BLUE_LED, LOW);
      }else {}
    }
    const String sending_data (String(temperature) + ',' + String(humidity));
    Serial.println(sending_data);
    delay(500UL);
  }
}
