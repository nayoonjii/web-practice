<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 동기식(화면전환이 있다.) 파일 첨부 기능</title>
</head>
<body>
  <h1>동기식 파일 첨부</h1>
  <form method="post" action="uploadFormAction" enctype="multipart/form-data">
  <%-- 파일 첨부기능을 만들 때 유의 사항
  		1. method=post방식이어야 하낟. get방식은 안된다. method속성을 생략하면 기본값이get이다. 그러므로 생략하면 안된다.
  		2. 폼 태그내에 enctype="multipart-data"를 지정해야 파일을 첨부해서 서버로 업로드 할수있다.
  		--%>
  	<input type="file" name="uploadFile" multiple />
  	<%--'multiple'속성을 지원해 줘서 하나의 input type="file"로 다중 이진 파일을 동시에 업로드가 가능하다. --%>
  	<input type="submit" value="파일 업로드" />
  </form>
</body>
</html>