#include <Keypad.h>
const String PASSWORD = "1234";
String input_password="";
const uint8_t ROWS=4U;
const uint8_t COLS=4U;
const char hexaKeys[ROWS][COLS]={
  {'0','1','2','A'},
  {'3','4','5','B'},
  {'6','7','8','C'},
  {'*','9','#','D'}
};

uint8_t rowPins[ROWS]={2U,3U,4U,5U};//input
uint8_t colPins[COLS]={9U,8U,7U,6U};//output
Keypad keypad((char*)hexaKeys, rowPins, colPins, ROWS, COLS);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200UL);
  keypad.addEventListener(keyEvent);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(keypad.getKeys()){//버튼 눌리면 true
    keypad.getKey();//자동으로 keyevent호출
  }
}

