const uint8_t SERVO_PIN {9};
const uint8_t JOYSTICK {A8};
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  pinMode(JOYSTICK, INPUT);
  pinMode(SERVO_PIN, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  int position_value=analogRead(JOYSTICK);
  int mapped_value=map(position_value, 0,1023,0,255);
  Serial.print("VALUE : ");
  Serial.println(mapped_value);
  analogWrite(SERVO_PIN, mapped_value);
  delay(50UL);

  // for(int i=0;i<256;i+=10){
  //   analogWrite(SERVO_PIN, i);
  //   Serial.print(F("Angle : "));
  //   Serial.println(i);
  //   delay(100UL);
  // }//반바퀴 회전
  // delay(1000UL);

  // for(int i=255;i>=0;i-=10){
  //   analogWrite(SERVO_PIN, i);
  //   Serial.print(F("Angle : "));
  //   Serial.println(i);
  //   delay(100UL);
  // }
  delay(1000UL);
}
