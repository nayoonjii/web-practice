<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료실 수정</title>
<link rel="stylesheet" type="text/css" href="/resources/css/bbs.css" />
<script src="/resources/js/jquery.js"></script>
<script src="/resources/js/bbs.js"></script>
</head>
<body>
	<div id="bsW_wrap">
		<h2 class="bsW_title">자료실 수정</h2>
		<form method="post" action="bbs_edit_ok"
			onsubmit="return write_check();" enctype="multipart/form-data">
			<%-- 파일을 첨부해서 서버로 업로드 하는 자료실 기능을 만들때 유의 사항)
       1. method=post만 가능하다.get은 안된다. 폼태그 내에서 method속성을 생략하면 기본값이get이다.
       	그러므로 생략하면 안된다.
       	
       2.폼태그내에 enctype="multipart/form-data"속성을 꼭 지정해야 한다. 파일첨부해서
  		서버로 전송되는 첨부된 파일을 바이너리모드(binary mode)즉 이진파일이라 부른다.
  		그렇지 않은 일반게시판에서 서버로 전송되는 데이터를 ascii mode(아스키 모드) 파일이라 한다.
   --%>

			<%-- 수정할 히든 기준 번호값을 전달 --%>
			<input type="hidden" name="bbs_no" value="${b.bbs_no }" />

			<%--페이징(쪽나누기)책갈피 기능 --%>
			<input type="hidden" name="page" value="${page }" />


			<table id="bsW_t">
				<tr>
					<th>글쓴이</th>
					<td><input name="bbs_name" id="bbs_name" size="14"
						value="${b.bbs_name }" /></td>
				</tr>
				<tr>
					<th>글제목</th>
					<td><input name="bbs_title" id="bbs_title" size="33"
						value="${b.bbs_title }" /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="bbs_pwd" id="bbs_pwd"
						size="14" /></td>
				</tr>
				<tr>
					<th>글내용</th>
					<td><textarea name="bbs_cont" id="bbs_cont" rows="8" cols="34">${b.bbs_cont}</textarea></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td><input type="file" name="bbs_file" /><br />${b.bbs_file}</td>
				</tr>
			</table>
			<div id="bsW_menu">
				<input type="submit" value="수정" /> <input type="reset" value="취소"
					onclick="$('#bbs_name').focus();"> <input type="button"
					value="목록" onclick="location='bbs_list?page=${page}';">
				<%-- bbs_list?page=쪽번호가 get방식 즉 쿼리 스트링 방식으로 전달된다. 주소창에 값이 노출된다.
   		page피라미터 이름에 쪽번호가 담겨져서 전달된다. 이것은 페이징에서 내가 본 페이지 번호로 바로 이동하기
   		위한 책갈피 기능이다. --%>
			</div>
		</form>
	</div>
</body>
</html>