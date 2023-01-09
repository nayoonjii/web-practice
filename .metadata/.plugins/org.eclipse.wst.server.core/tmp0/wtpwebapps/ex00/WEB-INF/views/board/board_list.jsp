<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 mvc게시판 목록</title>
</head>
<body>
<table border="1">
  <tr>
    <th colspan="5">스프링 mvc게시판 목록</th>
  </tr>
  <tr>
    <td colspan="5" align="right"><strong>게시물 수: ${totalCount}</strong>개</td>
  </tr>
  <tr>
    <th>번호</th> <th>글제목</th> <th>글쓴이</th> <th>조회수</th> <th>등록날짜</th>
  </tr>
  <c:if test="${!empty list }">
    <c:forEach var="b" items="${list}">
    <tr>
      <th>${b.bno}</th>
      <td style="padding-left:14px; font-weight:bolder;">
      <a href="/board/board_cont?bno=${b.bno}&page=${page}" >${b.title}
      <c:if test="${b.replycnt != 0 }">
        &nbsp;&nbsp;&nbsp;<%--3칸의 빈공백 --%> <strong>[댓글 개수 : ${b.replycnt} 개]</strong>
      </c:if></a></td>
      <%--?bno=번호&page=쪽번호 주소창에 노출되는 get방식으로 2개의 피라미터값이 전달됨 --%>
      <th>${b.writer}</th><%--b.getWriter()를 사용한것 과 같은 효과가난다. --%>
      <th>${b.viewcnt}</th>
      <th>${b.regdate}</th>
    </tr>
    </c:forEach>
  </c:if>
  <c:if test="${empty list}">
    <tr>
    <th colspan="5">게시물 목록이 없습니다.</th>
    </tr>
  </c:if>
  
<%--   <%--페이징 즉 쪽나누기 부분 --%> --%>
<!--   <tr> -->
<!--     <th colspan="5"> -->
<%--       <c:if test="${page <= 1}"> --%>
<!--         [이전]&nbsp; -->
<%--       </c:if> --%>
<%--       <c:if test="${page > 1}"> --%>
<%--         <a href="/board/board_list?page=${page-1}">[이전]</a>&nbsp; --%>
<%--       </c:if> --%>
      
<%--       현재 쪽번호 출력 --%>
<%--       <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1"> --%>
<%--         <c:if test="${a == page }">현재 쪽번호가 선택된경우 --%>
<!--           <${a}> -->
<%--         </c:if> --%>
<%--         <c:if test="${a !=page }">현재쪽번호가 선택 안된 경우 --%>
<%--           <a href="/board/board_list?page=${a}">[${a}]</a>&nbsp; --%>
<%--         </c:if> --%>
<%--       </c:forEach> --%>
      
<%--       <c:if test="${page >= maxpage }"> --%>
<!--         다음 -->
<%--       </c:if> --%>
<%--       <c:if test="${page<maxpage}"> --%>
<%--         <a href="/board/board_list?page=${page+1}">[다음]</a> --%>
<%--       </c:if> --%>
<!--     </th> -->
<!--   </tr> -->
  
<!--   <tr> -->
<!--     <td colspan="5" align="right" style="padding-right:14px;"> -->
<!--       <input type="button" value="글쓰기"  -->
<%--       onclick="location='/board/board_write?page=${page}';"/> --%>
<!--     </td> -->
<!--   </tr> -->
<!-- </table> -->

<!-- <script type="text/javascript"> -->
//   var msg='${msg}';/*자바스크립트에서 스프링 컨트롤러에서 저장된 키이름을 참조해서 EL or JSTL로 가져올수있다.
//   여기서는 EL즉 표현언어를 사용해서 가져왔다.*/
//   if(msg == 'SUCCESS'){
// 	  alert('처리가 완료 되었습니다!');
//   }
<!-- </script> -->
</body>
</html>