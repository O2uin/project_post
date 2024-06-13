package temp;

import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

public class DataBase extends TimeCheck{
	
	private int cash=390500;//잔액
	private int salesCash=0;//매출액
	
	private LocalDateTime login;
	//private LocalDateTime start;
	private LocalDateTime end;
	//기본 물품 술/담배 필수
	//주스 술 우유 두부 라면 빵 사과 달걀 담배 과자
	ItemList[] items=new ItemList[10];
	
	private int[] save=new int[10];
	
	public DataBase() {
		//기본 물품 생성
		//이름 가격 유통기한 재고
		items[0]=new ItemList("빵", 700, false, super.timeSet(2024, 6, 5, 12, 0),5);
		items[1]=new ItemList("사과", 2000, false, super.timeSet(2024, 6, 6, 12, 0),4);
		items[2]=new ItemList("달걀", 8000, false, super.timeSet(2024, 6, 7, 12, 0),2);
		items[3]=new ItemList("과자", 1200, false, super.timeSet(2024, 6, 8, 12, 0),3);
		items[4]=new ItemList("라면", 1500, false, super.timeSet(2024, 6, 9, 12, 0),7);
		items[5]=new ItemList("두부", 1700, false, super.timeSet(2024, 6, 10, 12, 0),3);
		items[6]=new ItemList("우유", 3400, false, super.timeSet(2024, 6, 11, 12, 0),1);
		items[7]=new ItemList("얼음컵", 1300, false,null,5);
		items[8]=new ItemList("술", 2200, true, super.timeSet(2024, 6, 13, 12, 0),2);
		items[9]=new ItemList("담배", 4000, true, super.timeSet(2024, 6, 14, 12, 0),6);
	}
	
	public void saveSales(int i, int count) {
		//판매 개수저장
		save[i]+=count;
	}
	public void saveSalesMinus(int i, int count) {
		save[i]-=count;
	}
	
	public int getSaveSales(int i){
		return save[i];
	}

	
	/*public String getTime() {//시간 포맷
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy.MM.dd. a HH:MM");
		String tm=start.format(dtf);
		return tm;
	}*/
	
	public LocalDateTime getEnd() {//종료시간
		return end;
	}

	public LocalDateTime getLogin() {//로그인 시간 출력
		return login;
	}

	public void timeSetLogin(LocalDateTime time) {//로그인 시간 가져오기
		this.login=time;
		
	}
	/*public void timeSetStart(LocalDateTime time) {
		this.start=time;
		
	}*/
	public void timeSetEnd(LocalDateTime time) {//종료시간
		this.end=time;
	}
	
	public int getCash() {//잔고
		return cash;
	}

	public int getSalesCash() {//매출
		return salesCash;
	}
	
	public void setPlusCash(int cash) {//판매시 불러오기
		this.cash += cash;//잔고
		this.salesCash += cash;//매출
	}	

	public void setMinusCash(int cash) {//환불시 불러오기
		this.cash-=cash;
		this.salesCash-=cash;
	}
	
	
}
