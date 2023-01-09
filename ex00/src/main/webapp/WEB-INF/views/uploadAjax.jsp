<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비동기식 아작스 이진파일 업로드 폼</title>
<script src="./resources/js/jquery.js"></script>
<script>
  /*type속성생략해도 기본값으로 자바스크립트로 인식한다.*/
  $(document).ready(function(){
	 $('#uploadBtn').on('click',function(){
		 
		 var fromData = new FormData();
		 //아작스를 이용한 첨부파일 처리는 FormData를 이용한다.
		 
		 var inputFile = $("input[name='uploadFile']");//jQuery 아이디 선택자가 아닌 피라미터 이름으로
		 //접근해서 file객체를 구함
		 var files = inputFile[0].files;//첫번째 파일 객체에서 첨부한 파일을 배열로 구한다.
		 
		 //첨부파일을 formData객체에 추가
		 for(var i=0;i<files.length;i++){
			 formData.append("uploadFile",files[i]);
		 }
		 $.ajax({
			 url:'/uploadAjaxOK',//서버 매핑 주소
			 processData : false,//processData 데이터를 컨텐트 타입에 맞게 변환
			 contentType:false,//요청 컨텐트 타입
			 data:formData,//아작스를 통해서 폼데이터 자체를 전송
			 type:'POST',//보내는 방식
			 success : function(data){//받아오는 것이 성공시 호출되는 콜백 함수
				 
			 }
		 });//jQuery 비동기식 아작스
	 }) ;
  });
</script>
</head>
<body>
  <h1>Upload with Ajax</h1>
  <input type="file" name="uploadFile" multiple />
  <hr/>
  <button id="uploadBtn" type="button" >Upload</button>
  
</body>
</html>






