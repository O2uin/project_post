#include "HCSR04.h"
#include "DHT.h"
DHT dht{A0, 11};
UltraSonicDistanceSensor ultra{6U,7U};

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  dht.begin();

}

void loop() {
  // put your main code here, to run repeatedly:
  if(dht.read()){
    float temperature=dht.readTemperature();
    float distance=ultra.measureDistanceCm(temperature);
    if(distance>400.0F or distance<0.0F){
      //error skip
    }else{
      Serial.print(F("거리: "));
      Serial.println(distance);
    }
  }
  delay(500UL);
}
