const uint8_t CDS_PIN {A0};
const uint8_t PIN{7};

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  pinMode(CDS_PIN, INPUT);
  pinMode(PIN, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  int light_value=analogRead(CDS_PIN);
  int mapped=map(light_value,0,1023,0,255);

  Serial.print(F("LIGHT VALUE :"));
  Serial.println(mapped);

  delay(500UL);

  if(mapped>20){
    //off
    digitalWrite(PIN, LOW);
  }else{
    digitalWrite(PIN, HIGH);
  }
}
