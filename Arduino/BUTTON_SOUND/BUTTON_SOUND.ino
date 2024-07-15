const uint8_t BUTTON_SW = 11U;

void setup() {
  // put your setup code here, to run once:
  pinMode(BUTTON_SW, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  boolean button = digitalRead(BUTTON_SW);
  if(button==LOW){
    //눌렸을때 
    tone(BUTTON_SW, 262);
  }
}
