<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/admin_header.jsp" />
<%-- 상단 공동페이지 외부 포함 파일 불러오기 --%>

<%--관리자 자료실 본문중에서 내용보기 --%>
<div id="aMain_cont">
<div id="abbsCont_wrap">
  <h2 class="abbsCont_title">관리자 자료실 내용</h2>
  <table id="abbsCont_t">
   <tr>
    <th>제목</th> <td>${b.bbs_title}</td>
   </tr>
   <tr>
    <th>내용</th> <td>${bbs_cont}</td>
   </tr>
   <tr>
    <th>조회수</th> <td>${b.bbs_hit}</td>
   </tr>
   <c:if test="${!empty b.bbs_file}"> <%--첨부파일이 있는 경우만 --%>
    <tr>
     <th>첨부파일명</th> <td>${b.bbs_file}</td>
    </tr>
   </c:if>   
  </table>
  <div id="abbsCont_menu">
   <input type="button" value="목록"
   onclick="location='admin_bbs_list?page=${page}';" />
  </div>
 </div>

</div>

<%--관리자 하단 공통 페이지 외부 포함파일 불러오기 include 액션 태그 --%>
<jsp:include page="../include/admin_footer.jsp" />
