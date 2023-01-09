<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 MVC게시판 입력 폼</title>
<script  src="../resources/js/jquery.js"></script> <!-- jQuery 라이브러리 경로 -->
<script  src="../resources/js/board.js"></script> <!-- jQuery&javascript 유효성검증 파일경로 -->
</head>
<body>
<form method="post" onsubmit="return check();">
  <%--action속성을 지정하지 않으면 이전 매핑주소가 액션 매핑주소가 된다. 같은 매핑주소는 method방식인 get or post로 구분한다. --%>
  <table border="1">
    <tr>
      <th colspan="2">스프링 MVC게시판 입력</th>
    </tr>
    <tr>
      <th>글쓴이</th>
      <td><input name="writer" id="writer" size="14"/></td>
      	  <!-- type속성을 생략하면 기본값이 text이다. -->
    </tr>
    <tr>
      <th>글제목</th>
      <td><input name="title" id="title" size="36"/></td>
    </tr>
    <tr>
      <th>글내용</th>
      <td><textarea name="content" id="content" rows="10" cols="38"></textarea></td>
    </tr>
    <tr>
      <th colspan="2">
      <input type="submit" value="글입력"/>
      <input type="reset" value="취소" onclick="$('#writer').focus();" />
      <input type="button" value="목록" onclick="location=
      		'/board/board_list?page=${page}';"/>
      </th>
    </tr>
  </table>
</form>
</body>
</html>