<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료실 삭제</title>
<link rel="stylesheet" type="text/css" href="/resources/css/bbs.css" />
<script src="/resources/js/jquery.js"></script>
<script>
 function del_check(){
    if($.trim($("#del_pwd").val()) == ""){
       alert("삭제 비번을 입력하세요!");
       $("#del_pwd").val("").focus();
       return false;
    }
 }
</script>
</head>
<body>
	<div id="bsDel_wrap">
		<h2 class="bsDel_title">자료실 삭제</h2>
		<form method="post" action="bbs_del_ok?bbs_no=${b.bbs_no }"
		 onsubmit="return del_check();">
		 <!-- page 히든값과 비번은post로 방식으로 전달되고, bbs_del_ok?bbs_no=번호는 주소창에
		 노출되는 get방식으로 전달되는 것이다. -->
			<!-- <input type="hidden" name="bbs_no" value="${b.bbs_no}" /> --> <input
				type="hidden" name="page" value="${page}" />
			<table id="bsDel_t">
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="del_pwd" id="del_pwd"
						size="14" /></td>
				</tr>
			</table>
			<div id="bsDel_menu">
				<input type="submit" value="삭제" /> <input type="reset" value="취소"
					onclick="$('#del_pwd').focus();" />
			</div>
		</form>
	</div>
</body>
</html>