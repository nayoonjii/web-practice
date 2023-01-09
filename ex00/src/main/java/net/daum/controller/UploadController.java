package net.daum.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {//동기식 또는 비동기식 방법으로 c:\\upload 폴더에 첨부한 파일을 업로드

	@GetMapping("uploadForm")//get 으로 접근하는 url매핑주소 처리.uploadForm 매핑주소 등록
	public void uploadForm() {
		//리턴타입이 없는 보이드 형이면 매핑주소가 뷰페이지 파일명이 된다.
		//뷰페이지 경로(뷰 리졸브 경로는 /WEB-INF/views/uploadForm.jsp)
		
	}//uploadForm()
	
	@PostMapping("/uploadFormAction") //post로 접근하는 매핑주소 처리.uploadFormAction 매핑주소 등록
	public void uploadFormAction(MultipartFile[] uploadFile) {
		/* 스프링에서 MultipartFile 타입을 제공해서 업로드 되는 파일을 쉽게 처리. 다중업로드 파일은 배열로 받는다.
		 * <input type="file" name="uploadFile" />네임 피라미터 이름과 MultipartFile[] uploadFile
		 * 에서 전달인자 즉 매개변수명 (사용자 정의명 : 임의로 지정가능함)을 같게 해줘야 첨부한 파일을 제대로 가져옴
		 */
		String uploadFolder = "C:\\upload";//이진파일 업로드 서버 경로
		
		for(MultipartFile multipartFile : uploadFile) {
			System.out.println("------------------------>");
			System.out.println("UpLoad File Name : "+ multipartFile.getOriginalFilename());
			//원래 업로드 원본 파일명을 구해서 출력
			System.out.println("UpLoad File Size : "+ multipartFile.getSize());
			//업로드되는 파일크기
			
			File saveFile = new File(uploadFolder,multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);//c:\\upload 폴더에 실제 첨부된 파일이 저장됨.
			}catch(Exception e) {e.printStackTrace();}
		}//향상된 확장for
	}//uploadFormAction()
	
	//비동기식 이진파일 업로드 폼
	@GetMapping("/uploadAjax")
	public ModelAndView uploadAjax() {
		ModelAndView um = new ModelAndView();
		um.setViewName("userAjax");//뷰페이지 경로가 /WEB-INF/views/userAjax.jsp
		return um;
	}//uploadAjax()
	
	//비동기식 아작스 첨부파일 업로드 
	@PostMapping("uploadAjaxOK")
	public ModelAndView uploadAjaxOK(MultipartFile[] uploadFile) {
		System.out.println("upload ajax post......");
		String uploadFolder="C:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			System.out.println("-------------------->");
			System.out.println("Upload File Name : "+multipartFile.getOriginalFilename());
			System.out.println("Upload File Size : "+multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();//첨부한 원본파일명을 구함
			File saveFile =new File(uploadFolder,uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);//upload폴더에 실제 첨부파일 업로드				
			}catch(Exception e) {e.printStackTrace();}
		}//향상된 확장for
		return null;
	}//uploadAjaxOK()
	
}








