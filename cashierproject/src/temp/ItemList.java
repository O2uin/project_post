package temp;

import java.time.LocalDateTime;

public class ItemList {
	//상품명, 가격, 19여부, 유통기한
	private String name;
	private int price;
	private boolean teen;//true면 청불
	private LocalDateTime expiredate;
	private int count;//재고
	//우유,두부,사과,라먄 1d | 과자,빵,계란 1h |  술,담배 12h | 얼음 null
	private boolean expir;//유통기한 지났는지 여부 true일때 지남

	public ItemList(String name, int price, boolean teen, LocalDateTime expiredate,int count) {
		this.name=name;
		this.price=price;
		this.teen=teen;
		this.expiredate=expiredate;
		this.count=count;
	}
	
	/*public ItemList(String name, int price, boolean teen, int count) {//이 생성자 없어도 되겠다.......
		this.name=name;
		this.price=price;
		this.teen=teen;
		this.expiredate=null;
		this.count=count;
	}*/
	
	public void setItemList(String name, int price, boolean teen, LocalDateTime expiredate, int count) {
		this.name=name;
		this.price=price;
		this.teen=teen;
		this.expiredate=expiredate;
		this.count=count;
		this.expir=false;
	}
	
	public void setCount(int i, boolean sales) {//수량, 환불/판매 여부
		if(sales) {//판매
			count-=i;
		}else {
			count+=i;
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public boolean isTeen() {
		return teen;
	}

	public LocalDateTime getExpiredate() {
		return expiredate;
	}

	public boolean isExpir() {
		return expir;
	}

	public void setExpir(boolean expir) {
		this.expir = expir;
	}

}
