<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록 날짜</th>
        </tr>
        <c:forEach items="${list}" var="board">
            <tr onclick="location.href='/aop_cont?bno=${board.bno}'">
                <td>${board.bno}</td> <td>${board.btitle}</td> <td>${board.bname}</td>
                <td>${board.bhit}</td> <td>${board.bdate}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>