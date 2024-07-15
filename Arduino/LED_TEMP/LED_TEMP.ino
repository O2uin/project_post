//13이랑 7로
const uint8_t LED_PIN = 13U;
const uint8_t LED_PIN_B = 7U;

void setup() {//4개씩 깜박거리는 코드
  // put your setup code here, to run once:
  pinMode(LED_PIN, OUTPUT);
  pinMode(LED_PIN_B, OUTPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(LED_PIN, HIGH);
  digitalWrite(LED_PIN_B, LOW);
  delay(1000);
  digitalWrite(LED_PIN, LOW);
  digitalWrite(LED_PIN_B, HIGH);
  delay(1000);
}
