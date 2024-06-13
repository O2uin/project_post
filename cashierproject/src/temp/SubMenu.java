package temp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SubMenu extends TimeCheck{
//1.물건 판매 2. 환불 3.19금 물품 4.물품입고 5.물품 재고 및 제품 이름 검색
	private int select;
	private boolean sub=true;
	//private boolean age=false;
	
	public SubMenu(DataBase db) {//db받아오면서 생성자 하나 만들어야함
		while(sub) {
		printMenu_sub(db);
		}
		MainMenu main =new MainMenu(db);
	}
	
	public void printMenu_sub(DataBase db) {
		System.out.println("┌------------------+WorkMenu+-------------------┐");
		System.out.println("|1]판매 |2]환불 |3]입고 |4]재고/제품 검색 |5]이전 |6]종료\t|");
		System.out.println("└-----------------------------------------------┘");
		System.out.print("입력: ");
		Scanner scan=new Scanner(System.in);
		select=scan.nextInt();
		
		switch(select) {
		case 1:
			sales_menu(db);
			break;
		case 2:
			refund_menu(db);
			break;
		case 3:
			input_menu(db);
			break;
		case 4:
			find_menu(db);
			break;
		case 5:
			sub=false;
			break;
		case 6:
			endPrint(db);
			System.exit(0);
			break;
		default: System.out.println("잘못된 입력입니다.");
		}
	}
	
	//판매
	public void sales_menu(DataBase db) {
		//1.제품 검색
		//2.구입가능여부(재고가 0이 아님/유통기한이 지나지 않음)
		//3.카드판별
		//4.재고-1 매출+ 
		//--술/담배일 경우 생년월일 체크--
		expireSet(db);//유통기한 체크
		System.out.print("구매할 상품명:");
		Scanner sc=new Scanner(System.in);
		String item=sc.nextLine();
		boolean not=true;
		
		for(int i=0;i<db.items.length;i++) {
			if(db.items[i].getName().equals(item)) {//0번 if
				if(db.items[i].isExpir()) {//유통기한 지났을 경우
					System.out.println("유통기한이 만료된 상품입니다.");
					not=false;
					break;
				}
				if(db.items[i].isTeen()) {//청불일 경우
					if(!ageCheck()) {
						not=false;
						break;
					}
					System.out.println("인증되었습니다.");
					System.out.println();
					//나이가 현재 년도 빼기 뭐더라 현재-생일=19
				}
				
				
				//재고 확인
				if(db.items[i].getCount()!=0) {//1번 if
					System.out.print("카드 번호 입력(-포함 입력): ");
					String card=sc.nextLine();
					
					if(cardCheck(card)) {//2번 if
						System.out.print("구매 수량: ");
						int n=sc.nextInt();
						
						if(n<=db.items[i].getCount()) {//3번 if
							if(n!=0) {
								db.saveSales(i, n);//판매정보 저장
								db.items[i].setCount(n, true);//재고-
								db.setPlusCash(db.items[i].getPrice()*n);//매출+	
								System.out.println("구매 완료! 매출 +"+db.items[i].getPrice()*n);
							}else {
								System.out.println("입력 오류입니다.");
							}
						}else {//3번 else
							System.out.println("재고가 부족합니다.");
						}
					}else {//2번 else
						//카드 틀렸음
						System.out.println("유효하지 않은 카드입니다.");
					}
					
				}else {//1번 else
					System.out.println("재고가 없습니다.");
				}
				
				not=false;
			}
		}
		
		if(not) {//1번 비교문 안에 들어가지 못했을 경우
			System.out.println("존재하지 않는 상품입니다.");
		}
	}
	
	//환불
	public void refund_menu(DataBase db) {
		//1.이름 입력 판매이력 확인?
		//=>db에 배열 하나 파야할듯?
		//2.가격 입력? 정보를 가지고 잇는데 굳이 입력을?
		//3.카드인데 왜 거스름돈이 있냐고
		//4.매상-
		boolean not=true;
		System.out.print("환불할 제품명: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.nextLine();
		
		for(int i=0;i<db.items.length;i++) {//배열 길이만큼 반복
			if(db.items[i].getName().equals(name)) {
				if(db.getSaveSales(i)>0) {
					System.out.print("환불 수량: ");
					int a = sc.nextInt();
					
					if(db.getSaveSales(i)>=a) {
						db.items[i].setCount(a, false);//카운트 복구
						db.setMinusCash(db.items[i].getPrice()*a);//매출 복구
						db.saveSalesMinus(i, a);//구매내역 복구
						System.out.println("환불되었습니다. "+db.items[i].getPrice()*a+"원 환불");
					}else {
						System.out.println("수량을 확인해주세요.");
					}
					
				}else {
					System.out.println("구매내역이 존재하지 않습니다.");
				}
				
				not=false;
			}
		}
		
		if(not) {
			System.out.println("존재하지 않는 상품입니다.");
			not=true;
		}
	}
	
	//입고
	public void input_menu(DataBase db) {
		//상품화면 , 중복확인
		//이미 존재하는 상품, 유통기한이 지난 상품, 그냥 재고없는거
		//중복되지 않은 상품은 유통기한 지났거나 재고 없는 곳에 넣을 수 있음 없으면 빈곳 실패 띄우던가...
		//중복된 상품은 취소시키기 유통기한이 따로 있기 때문에...
		expireSet(db);//유통기한 체크
		
		System.out.print("입고상품명: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		
		String oriName;
		boolean overlap=false;//이미 존재하는 상품
		boolean noSpace=true;
		LocalDateTime date=super.readTime().plusDays(1);//오늘 +1일
		
		for(int i=0;i<db.items.length;i++) {
			int price=db.items[i].getPrice();
			boolean teen=db.items[i].isTeen();
			
			if(name.equals(db.items[i].getName())) {//이미 존재하는 상품
				overlap=true;
				if(db.items[i].getCount()>0 && db.items[i].isExpir()==false) {//재고가 1개 이상이거나 유통기한이 지남
					System.out.println("재고가 확인된 상품은 추가할 수 없습니다.");
				}else if(db.items[i].isExpir()) {
					System.out.println("유통기한이 지난 상품을 제거 후 입고합니다.");
					System.out.print("상품 개수: ");
					int count=sc.nextInt();
					db.items[i].setItemList(name, price, teen, date, count);
				}else {
					//상품을 추가 입고했습니다.
					System.out.println("상품을 추가 입고합니다.");
					System.out.print("상품 개수: ");
					int count=sc.nextInt();
					db.items[i].setItemList(name, price, teen, date, count);
				}
			}
		}
		
		//새로운 상품
		if(!overlap) {
			for(int j=0;j<db.items.length;j++) {
				if(db.items[j].isExpir() || db.items[j].getCount()==0) {//유통기한 지났거나 재고 없는 상품
					//새 상품 입고
					oriName=db.items[j].getName();
					noSpace=false;
					System.out.println("새 상품을 입고합니다.");
					System.out.print("상품 개수: ");
					int count=sc.nextInt();
					System.out.print("상품 가격: ");
					int price=sc.nextInt();
					boolean roop=true;
					boolean teen=false;
					while(roop) {
						System.out.print("청소년 구매 가능 상품 여부(가능, 불가능): ");
						String num=sc.next();
						
						switch(num) {
						case "가능":
							roop=false;
							teen=false;
							break;
						case "불가능":
							roop=false;
							teen=true;
							break;
						default: System.out.println("입력 오류");
						}
					}
					db.items[j].setItemList(name, price, teen, date, count);
					System.out.printf("%s의 위치에 입고했습니다.\r\n",oriName);
					break;//반복문 끊어주기
				}
				
			}
			
			if(noSpace) {
				//자리없다
				System.out.println("매대 자리가 부족합니다.");
			}
			
		}
		
	}
	
	//검색
	public void find_menu(DataBase db) {
		//이름 입력-> 재고확인? 상품이 n개 있습니다. 재고가 없습니다.
		expireSet(db);//유통기한 체크
		System.out.print("검색할 상품명: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		boolean notFound=true;
		String date;
		String teen;
		
		for(int i=0;i<db.items.length;i++) {
			if(db.items[i].getName().equals(name) && !db.items[i].isExpir() && db.items[i].getCount()!=0) {
				//정보 출력
				notFound=false;
				if(db.items[i].isTeen()) {
					teen="불가";
				}else {
					teen="가능";
				}
				date=super.expireFormat(db.items[i].getExpiredate());
				System.out.printf("재고: %d개\r\n", db.items[i].getCount());
				System.out.println("유통기한: "+date);
				System.out.println("가격: "+db.items[i].getPrice()+"원");
				System.out.println("청소년 구매 "+teen+" 상품입니다.");
			}
		}
		
		if(notFound) {
			System.out.println("상품을 찾을 수 없습니다.");
		}
	}
	
	//카드유효성체크
	public boolean cardCheck(String card) {
		String pattern="\\d{4}-\\d{4}-\\d{4}-\\d{4}"; //1111-1111-1111-1112
		boolean result=Pattern.matches(pattern, card);
		return result;
	}
	
	//성인인증
	public boolean ageCheck() {
		System.out.println("성인 인증이 필요한 상품입니다.");
		System.out.print("생년월일 입력: ");
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		//2001 0321 /10000나누면 2001만 남겟지?
		
		Calendar now=Calendar.getInstance();					
		int hour=now.get(Calendar.YEAR);
		
		if(hour-(n/10000)<19) {
			System.out.println("청소년 구매 불가.");
			return false;
		}
		return true;
	}
	
	//종료출력
	public void endPrint(DataBase db) {
		//총 매출, 수당, 로그인시간, 일한 시간
		System.out.print(" | 업무를 종료합니다. |");
		super.nowTime();//현재시간 출력
		db.timeSetEnd(super.readTime());//end에 값 넣기
		System.out.println("------=====결과=====------");
		System.out.println("|잔고: "+db.getCash()+"\t\t|");
		System.out.println("|총 매출: +"+db.getSalesCash()+"\t\t|");
		System.out.println("|로그인 시간: "+super.timeFormat(db.getLogin())+"\t|");//시작시간
		
		long time=db.getLogin().until(db.getEnd(), ChronoUnit.MINUTES);//몇분인지 세기
		
		System.out.println("|업무시간: "+time+"분"+"\t\t|");//끝-시작
		System.out.println("|수당: "+time*9800+"\t\t|");//업무(분)*9800
		System.out.println("-------------------------");
	}

	//유통기한
	public void expireSet(DataBase db) {//검색이나 구매같은거에서 계속 불러오면서 유통기한 체크 부울값을 바꾸는걸로
		for(int i=0; i<db.items.length ;i++) {
			LocalDateTime t=db.items[i].getExpiredate();
			if(t!=null) {
				int number = (int)db.timeCompute(t, super.readTime());
				if(number>0) {
					//System.out.printf("%s\t(유통기한 만료)\r\n",super.expireFormat(t));
					db.items[i].setExpir(true);//지났다
				}
			}
			
		}
		
	}
}
