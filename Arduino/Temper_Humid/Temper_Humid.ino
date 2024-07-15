#include "DHT.h"
const uint8_t TEMPER_PIN {A0};
const uint8_t RELAY_PIN {50U};
DHT dht(TEMPER_PIN, 11);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  dht.begin();//dht 센서 초기화
  pinMode(RELAY_PIN, OUTPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  if(dht.read()){
    float temperature = dht.readTemperature();
    float percentHumidity = dht.readHumidity();
    float heat = dht.computeHeatIndex(temperature, percentHumidity);

    Serial.print(F("TEMPERATURE : "));
    Serial.println(temperature);
    Serial.print(F("HUMIDITY : "));
    Serial.println(percentHumidity);


    if(temperature>25.0F){
      digitalWrite(RELAY_PIN, HIGH);
    }else{
      digitalWrite(RELAY_PIN, LOW);
    }

    if(heat>40.0F){
      Serial.println("fire");
    }
  }
  delay(1000UL);
}
