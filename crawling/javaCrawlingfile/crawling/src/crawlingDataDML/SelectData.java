package crawlingDataDML;

import java.sql.SQLException;
import java.util.Scanner;

public class SelectData extends DBConnection{

	public SelectData() {
		// TODO Auto-generated constructor stub
		while(true) {
			System.out.println("++-------알라딘 베스트셀러 검색-------++");
			System.out.println(" +1. 제목 검색\t\t\t+");//select * from bestseller where 책이름="받은 값"
			System.out.println(" +2. 저자 검색\t\t\t+");//select * from bestseller where 저자="받은 값"
			System.out.println(" +3. 출판사 검색\t\t\t+");//select * from bestseller where 출판사="받은값"
			System.out.println(" +4. 특정 순위 검색\t\t\t+");//select * from bestseller where book_id = "받은 값"
			System.out.println(" +5. 최대 가격대 검색\t\t+"); // select * from bestseller where 가격<="받은값"
			System.out.println(" +6. 베스트셀러 100위 보기\t\t+");//select * from bestseller
			System.out.println(" +0. 시스템 종료\t\t\t+");
			System.out.println("++------------------------------++");
			System.out.print("입력: ");
			Scanner scan=new Scanner(System.in);
			String input=scan.next();
			
			switch(input) {
			case "1":
				selectName();
				break;
			case "2":
				selectWriter();
				break;
			case "3":
				selectPublisher();
				break;
			case "4":
				selectRank();
				break;
			case "5":
				//selectPrice();
				System.out.println("준비중입니다.");
				break;
			case "6":
				selectAll();
				break;
			case "0":
				System.out.print("검색 시스템을 종료합니다.");
				System.exit(0);
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
		
		
	}
	
	String a = "순위";
	String b = "제목";
	String c = "저자";
	String d = "출판사";    // 출력문에 컬럼명을 표시하기 위한 변수들.
	String e = "가격";
	
	boolean check=false;
	
	public void selectName() {
		DBstart();
		System.out.print("제목 입력 : ");
		Scanner scan=new Scanner(System.in);
		String input=scan.next()+"%";
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where 책이름 like ?";
			statement = conn.prepareStatement(query);
			statement.setString(1,input);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
			}
			if(!check) {
				System.out.println("\t========================");
				System.out.println("\t   !검색된 자료가 없습니다!");
				System.out.println("\t========================");
			}
			check=false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
		
	}
	public void selectWriter() {
		DBstart();
		System.out.print("저자 입력 : ");
		Scanner scan=new Scanner(System.in);
		String input=scan.next()+"%";
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where 저자 like ?";
			statement = conn.prepareStatement(query);
			statement.setString(1,input);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
			}
			if(!check) {
				System.out.println("\t========================");
				System.out.println("\t   !검색된 자료가 없습니다!");
				System.out.println("\t========================");
			}
			check=false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
		
	}
	public void selectPublisher() {
		DBstart();
		System.out.print("출판사 입력 : ");
		Scanner scan=new Scanner(System.in);
		String input="%"+scan.next()+"%";
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where 출판사 like ?";
			statement = conn.prepareStatement(query);
			statement.setString(1,input);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
			}
			if(!check) {
				System.out.println("\t========================");
				System.out.println("\t   !검색된 자료가 없습니다!");
				System.out.println("\t========================");
			}
			check=false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
	}
	
	public void selectPrice() {//디비에 가격을 int로 안받아서 못한다 이거
		DBstart();
		System.out.print("최대가격 입력 : ");
		Scanner scan=new Scanner(System.in);
		int input=scan.nextInt();
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where 가격<="+input;
			statement = conn.prepareStatement(query);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
			}
			if(!check) {
				System.out.println("\t========================");
				System.out.println("\t   !검색된 자료가 없습니다!");
				System.out.println("\t========================");
			}
			check=false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
		
	}
	public void selectRank() {
		DBstart();
		System.out.print("순위 입력 : ");
		Scanner scan=new Scanner(System.in);
		int input=scan.nextInt();
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where book_id="+input;
			statement = conn.prepareStatement(query);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
			}
			if(!check) {
				System.out.println("\t========================");
				System.out.println("\t   !검색된 자료가 없습니다!");
				System.out.println("\t========================");
			}
			check=false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
		
	}
	
	public void selectAll() {
		DBstart();
		
		try {
			query = "SELECT book_id, 책이름, 저자, 출판사, 가격 FROM bestseller where book_id <101";
			statement = conn.prepareStatement(query);
			
			select = statement.executeQuery();
			printTitle();
			while(select.next()) {
				int bookid=select.getInt(1);
				String book_name = select.getString(2);
				String writer=select.getString(3);
				String publisher=select.getString(4);
				String price = select.getString(5);
				
				
				printInfo(bookid, book_name, writer, publisher, price);
				check=false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			DBstop();
		}
		
	}
	
	public void printTitle() {
		System.out.println("+-------------------------------------------------------------------------------+");
		System.out.printf("|%s\t| %s\t\t\t| %s\t\t| %s\t\t| %s\t\t|\n",a,b,c,d,e);
		System.out.println("+===============================================================================+");
	}
	
	public void printInfo(int bookid, String book_name, String writer, String publisher, String price) {
		
		System.out.printf("|%d위\t| %s | %s | %s | %s원 |",bookid,book_name,writer,publisher,price);
		System.out.println();
		System.out.println("+-------------------------------------------------------------------------------+");
		check=true;
	}

}