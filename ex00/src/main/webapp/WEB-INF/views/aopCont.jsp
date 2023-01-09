<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1">
        <tr>
            <td>번호: ${cont.bno}</td> <td>조회수: ${cont.bhit}</td> <td>작성일: ${cont.bdate}</td>
        </tr>
        <tr>
            <td colspan="2">제목: ${cont.btitle}</td> <td>작성자 : ${cont.bname}</td>
        </tr>
        <tr>
            <td colspan="3">내용: ${cont.bcont}</td>
        </tr>
        <tr></tr>
    </table>
</body>
</html>