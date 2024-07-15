enum RGB {//5 6 7에 대입
  RED = 5U,
  GREEN,
  BLUE
};

void setup() {
  // put your setup code here, to run once:
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  Serial.begin(115200UL); //시리얼 통신 Baud 115200
}

void loop() {
  // put your main code here, to run repeatedly:
  while(Serial.available() > 0){
    uint8_t red_value {Serial.parseInt()};
    uint8_t green_value {Serial.parseInt()};
    uint8_t blue_value {Serial.parseInt()};
    if(Serial.read()=='\n'){
      analogWrite(RED, red_value);
      analogWrite(GREEN, green_value);
      analogWrite(BLUE, blue_value);
    }
  }
  delay(500UL);

}
