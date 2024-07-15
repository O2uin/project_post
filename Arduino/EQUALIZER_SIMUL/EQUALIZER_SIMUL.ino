const uint8_t VR_PIN{A0};
const uint8_t LEDS[] {21U, 22U, 23U, 24U, 25U, 26U, 27U, 28U};

void setup() {
  // put your setup code here, to run once:
  pinMode(VR_PIN, INPUT);
  for(auto PIN:LEDS){
    pinMode(PIN, OUTPUT);
  }
  Serial.begin(115200UL);

}

void loop() {
  // put your main code here, to run repeatedly:
  // 초기화
  for(auto PIN:LEDS){
    digitalWrite(PIN, LOW);
  }
  int vr_value = analogRead(VR_PIN);
  int mapped_value=map(vr_value, 0, 1023, 0,8);
  Serial.print("MAP VALUE : ");
  Serial.println(mapped_value);
  for(int i=0;i<mapped_value;i++){
    digitalWrite(LEDS[i], HIGH);
  }
  
  delay(500UL);
}
