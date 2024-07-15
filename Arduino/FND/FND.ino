//PIN  29  28 27 26 25 24 23 22
//     DP  G  F  E  D  C  B  A
//0    0   0  1  1  1  1  1  1  ->0X3F
//1    0   0  0  0  0  1  1  0  ->0X06

const uint8_t PINS[] {22,23,24,25,26,27,28,29};

const uint8_t patterns[]={
  0b00111111, 0x06, 0x5B, 0x4F,
  0x66, 0x6D, 0x7D, 0x27, 0x7F, 0x67
};
void setup() {
  // put your setup code here, to run once:
  for(auto PIN:PINS){
    pinMode(PIN, OUTPUT);
  }
}

void loop() {
  // put your main code here, to run repeatedly:
  for(int number=0;number<10;++number){
    
    for(int i=0;i<8;i++){
      if(bitRead(patterns[number], i)==HIGH){
        digitalWrite(PINS[i],HIGH);
      }else{
        digitalWrite(PINS[i],LOW);
      }
    }

    delay(1000);
    for(int i=0;i<8;i++){
      digitalWrite(PINS[i], LOW);
    }
  }

  

}
