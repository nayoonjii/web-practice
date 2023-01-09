package net.daum.vo;

import lombok.Data;

@Data //setter(),getter()메서드를 자동제공
//@Setter //setter()메서드를 자동 제공한다.
//@Getter //getter()메서드를 자동 제공한다.
public class ProductVO {//자바 저장빈 클래스
	
	

	//getter() 메서드 정의	
	private String name;//상품명을 저장하는 변수
	private int price; //상품가격
	
	public ProductVO(String name, int price) {
		this.price = price;
		this.name = name;
	}//생성자 오버로딩

/*	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ProductVO[상품명="+name+",상품가격="+price+"]";
	}
	
*/	

}
