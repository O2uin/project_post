const uint8_t VR_PIN{A0};
void setup() {
  // put your setup code here, to run once:
  //BT->9600UL
  //시리얼 통신 ㄱ
  Serial.begin(115200UL); //unsigned long ->유선 115200
  pinMode(A0, INPUT); //생략 가능
}

void loop() {
  // put your main code here, to run repeatedly:
  //0~1023
  int vr_value = analogRead(A0);
  Serial.print("VR VALUE : ");
  Serial.println(vr_value);
  delay(50UL);
}
