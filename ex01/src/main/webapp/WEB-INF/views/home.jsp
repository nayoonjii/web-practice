<%@ page contentType="text/html; charset=UTF-8" %>
<%-- page 디렉티브(지시자) 지정. contentType="text/html;charset=UTF-8" 속성은 서블릿의
response.setContentType("text/html;charset=UTF-8")과 기능이 같다. 즉 브라우저(사용자)에 
출력되는 문자, 태그, 언어 코딩 타입을 설정 --%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello 시큐리티!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
