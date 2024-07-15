const uint8_t WATER {A2};
const uint8_t MOTOR {9};

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  pinMode(MOTOR, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  int value=analogRead(WATER);
  if(value>0){
    while(true){
      analogWrite(MOTOR, 256);
      delay(1000UL);
      analogWrite(MOTOR, 0);
      delay(1000UL);
    }
    
  }
}
