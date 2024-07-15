const uint8_t LEDS[] {21U, 22U, 23U, 24U, 25U, 26U, 27U, 28U};

void setup() {//하나씩 깜박거리는 코드
  // put your setup code here, to run once:
  for(const uint8_t PIN:LEDS){//const uint8_t=>AUTO 가능
    pinMode(PIN, OUTPUT);
  }

}

void loop() {
  // put your main code here, to run repeatedly:
  for(auto PIN:LEDS){
    digitalWrite(PIN, HIGH);
    delay(100UL);
  }
  delay(500UL);
  for(int i=7;i>=0;i--){
    digitalWrite(LEDS[i], LOW);
    delay(100UL);
  }
  delay(500UL);

}
