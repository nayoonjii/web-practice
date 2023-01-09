<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 MVC게시판 수정 폼</title>
<script  src="../resources/js/jquery.js"></script> <!-- jQuery 라이브러리 경로 -->
<script  src="../resources/js/board.js"></script> <!-- jQuery&javascript 유효성검증 파일경로 -->
</head>
<body>
<form method="post" action="board_edit_ok?bno=${b.bno}&page=${page}" onsubmit="return check();">
<%-- 액션 속성값 ?bno=번호&page=쪽번호 2개의 피라미터 값이 get으로 전달됨.수정할 글쓴이 글제목 글내용은 post로 전달된다. --%>
  <table border="1">
    <tr>
      <th colspan="2">스프링 MVC게시판 수정</th>
    </tr>
    <tr>
      <th>글쓴이</th>
      <td><input name="writer" id="writer" size="14" value="${b.writer}"/></td>
      	  <!-- type속성을 생략하면 기본값이 text이다. -->
    </tr>
    <tr>
      <th>글제목</th>
      <td><input name="title" id="title" size="36" value="${b.title}"/></td>
    </tr>
    <tr>
      <th>글내용</th>
      <td><textarea name="content" id="content" rows="10" cols="38">${b.content}</textarea></td>
      <%--여러줄을 입력하는textarea에서는 value속성이 없다. --%>
    </tr>
    <tr>
      <th colspan="2">
      <input type="submit" value="글수정"/>
      <input type="reset" value="취소" onclick="$('#writer').focus();" />
      <input type="button" value="목록" onclick="location=
      		'/board/board_list?page=${page}';"/>
      </th>
    </tr>
  </table>
</form>
</body>
</html>