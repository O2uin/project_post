//PB7 ->13번 :LED
//PE5 ->3번 :BUTTONSW
#include <avr/io.h>
#include <util/delay.h>

#define F_CPU 16000000UL; //수정자 클럭 수 16MHz

int main()//Entry point 진입점
{
  DDRB = 0x80; //=> 0b1000_0000; => pinMode(13, OUTPUT);
  DDRE = ~0b00100000; //
  for(;;)//==loop()
  {
    bool button_state=PINE & 0x20;//0b00100000 //PINE-Egroup
    _delay_ms(20UL);//0.002초

    if(button_state==true){
      PORTB=0x80; //digitalwrite(13, HIGH);
    }else{
      PORTB=~0b10000000;//digitalwrite(13, LOW);
    }
  }
  return 0;
}