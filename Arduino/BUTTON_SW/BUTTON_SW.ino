const uint8_t LED_PIN {13U};//c++초기화용 uniform initialization
const uint8_t BUTTON_SW1 {6U}; //input용
const uint8_t BUTTON_SW2 {7U}; 

void setup() {
  // put your setup code here, to run once:
  pinMode(LED_PIN, OUTPUT);//LED 반드시 output => 안하면 불 옅다
  pinMode(BUTTON_SW1, INPUT); //안해도 기본은 INPUT
}

void loop() {
  // put your main code here, to run repeatedly:
  boolean button1 = digitalRead(BUTTON_SW1);
  if(button1==HIGH){
    digitalWrite(LED_PIN, HIGH);
  }else{
    digitalWrite(LED_PIN, LOW);
  }
}
