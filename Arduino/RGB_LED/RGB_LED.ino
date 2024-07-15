const uint8_t VR_PIN{A0};
const uint8_t RED{5U};
const uint8_t GREEN{6U};
const uint8_t BLUE{7U};

void setup() {
  // put your setup code here, to run once:
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);

  Serial.begin(115200UL);
}

void RGB_color(int rv, int gv, int bv)
{
  analogWrite(RED, rv);
  analogWrite(GREEN, gv);
  analogWrite(BLUE, bv);
}

void loop() {
  // put your main code here, to run repeatedly:
  int vr_value=analogRead(VR_PIN);
  int mapped_value=map(vr_value, 0,1023,0,255);//어 이러면 RGB 값 다 같은거 아닌가..
  RGB_color(mapped_value,55,55);

}
