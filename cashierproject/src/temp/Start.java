package temp;
import java.util.*;

public class Start extends TimeCheck{//상속받아서 시간 출력하기

	private String nameTrue="hello";
	private String passTrue="1234";
	private String name, pass;
	
	private boolean nameStack=false;
	private boolean passStack=false;
	private boolean login=true;
	
	public Start(DataBase db) {
		//생성자		
		while(login) {
			logIn();
		}
		welcome(db);
	}
	
	public void logIn() {
		Scanner scan=new Scanner(System.in);
		System.out.println("--------로그인--------");
		System.out.print("사용자 이름: ");
		name=scan.next();
		System.out.print("암호: ");
		pass=scan.next();
		
		if(nameTrue.equals(name)) {
			nameStack=true;
			
		}else {
			System.out.println("이름/비밀번호를 확인해주세요.");
			name=null;
			pass=null;
			return;
			
		}
		
		if(passTrue.equals(pass)) {
			passStack=true;
			
		}else {
			System.out.println("이름/비밀번호를 확인해주세요.");
			name=null;
			pass=null;
			return;
		}
		
		if(nameStack==passStack==true) {
			login=false;
		}		
	}
	
	
	public void welcome(DataBase db) {
		TimeCheck time=new TimeCheck();
		System.out.println("--------------------");
		System.out.printf(" | %s님 반갑습니다. |",name);
		time.nowTime();//로그인 시간 출력
		db.timeSetLogin(super.readTime());//db에 로그인시간 저장
		
	}
}
