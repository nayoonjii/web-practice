package net.daum.vo;

import lombok.Data;

@Data //setter(),getter()메서드 자동제공
public class SampleVO {
	
	private int mno; //변수명이 json데이터의 키이름이 된다.
	private String firstName;//성
	private String lastName;//이름

}
