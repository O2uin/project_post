package temp;
import java.time.*;
import java.util.*;

public class MainMenu extends TimeCheck{
//1.재고 2.현재 잔고 3.매출액 4.유통기한 5.업무시작
	private int menu;
	private boolean main=true;
	
	public MainMenu(){
		
	}
	
	public MainMenu(DataBase db) {
		while(main) {
			printMenu_main(db);
		}
		SubMenu sub=new SubMenu(db);
	}
	
	public void printMenu_main(DataBase db) {
		System.out.println("┌--------------------TopMenu--------------------┐");
		System.out.println("|1)재고 확인 |2)현재 잔고 |3)매출 |4)유통기한 확인 |5)업무\t|");
		System.out.println("└-----------------------------------------------┘");
		System.out.print("입력: ");
		Scanner scan=new Scanner(System.in);
		menu=scan.nextInt();
		
		switch(menu) {
		case 1:
			menu_itemCheck(db);
			break;
		case 2:
			menu_chashCheck(db);
			break;
		case 3:
			menu_salesCheck(db);
			break;
		case 4:
			menu_expirationCheck(db);
			break;
		case 5:
			main=false;
			break;
		default:
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	//재고 확인
	public void menu_itemCheck(DataBase db) {
		//재고는 *로 표기
		//재고=items[i].count
		System.out.println("===========재고 현황===========");
		System.out.println("|  제품명\t|\t수량");
		System.out.println("|-------|------------------");
		for(int i=0;i<db.items.length;i++) {//items 개수만큼 반복
			System.out.printf("|  %s\t| ", db.items[i].getName());
			if(db.items[i].getCount()>0) {//재고가 0개가 아닌 것만 출력
				for(int a=0;a <db.items[i].getCount();a++) {
					System.out.print("* ");
					}
				System.out.printf("(%d개)\r\n",db.items[i].getCount());
			}else {
				System.out.println("!재고 없음!");
			}
		}
		System.out.println("=============================");
		
	}
	
	//매출
	public void menu_chashCheck(DataBase db) {
		System.out.print("현재 잔고: ");
		System.out.println(db.getCash()+"원");
	}
	
	//현재잔고
	public void menu_salesCheck(DataBase db) {
		System.out.print("현재 매출: ");
		System.out.println(db.getSalesCash()+"원");
	}
	
	//유통기한
	public void menu_expirationCheck(DataBase db) {
		System.out.println("=============유통기한 확인=============");
		System.out.println("|  제품명\t|\t유통기한");
		System.out.println("|-------|--------------------------");
		for(int i=0; i<db.items.length ;i++) {
			LocalDateTime t=db.items[i].getExpiredate();
			if(t!=null) {
				System.out.printf("| %s\t| ",db.items[i].getName());
				
					if (db.items[i].getCount()!=0) {//재고 존재 여부
						int number = (int)db.timeCompute(t, super.readTime());
					if(number>0) {
						System.out.printf("%s\t(유통기한 만료)\r\n",super.expireFormat(t));
					}else {
						int a=Math.abs(number);//분으로 계산중
						int day=a/(60*24);
						int hour=a/60;
						int min=a%60;
						System.out.printf("%s\t(%d일%d시간%d분)\r\n",super.expireFormat(t),day, hour-day*24, min);
					}
				}else {
					System.out.println("(재고없음)");
				}
			}
		}
		System.out.println("===================================");
	}
	
}
