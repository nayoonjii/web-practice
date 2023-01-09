 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
        &nbsp;&nbsp;&nbsp; <strong>[댓글 개수 : ${b.replycnt} 개]</strong>
      </c:if></a></td>
      <th>${b.writer}</th>
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
  
  
  <tr>
    <th colspan="5">
      <c:if test="${page <= 1}">
        [이전]&nbsp;
      </c:if>
      <c:if test="${page > 1}">
        <a href="/boardEx_list?page=${page-1}">[이전]</a>&nbsp;
      </c:if>
      
      
      <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
        <c:if test="${a == page }">
          <${a}>
        </c:if>
        <c:if test="${a !=page }">
          <a href="/boardEx_list?page=${a}">[${a}]</a>&nbsp;
        </c:if>
      </c:forEach>
      
      <c:if test="${page >= maxpage }">
        다음
      </c:if>
      <c:if test="${page<maxpage}">
        <a href="/boardEx_list?page=${page+1}">[다음]</a>
      </c:if>
    </th>
  </tr>
</table>
</body>
</html>
