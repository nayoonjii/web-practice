<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링mvc게시판 내용보기&비동기식 아작스</title>
<%--jQuery 라이브러리 읽어옴 --%>
  <script src="../resources/js/jquery.js"></script>
<style type="text/css">
 /* type 속성은 생략해도  웹브라우저가 기본 값을 css로 인식한다*/
 /*댓글 수정 화면*/
  #modDiv{
    width:300px; height:100px; background-color:gray;
    position:absolute; /*절대위치*/
    top:50%; left:50%;
    margin-top:-50px; margin-left:-150px;
    padding:10px;
    z-index:1000; /*position 속성이 absolute or fixed 인 곳에서 사용한다. 이 속성은 요소가 겹쳐지는 순서를 제어할수있다.
    				물론, 큰숫자일수록 앞에 나온다.*/ 
  }
</style>
</head>
<body>
<table border="1">
  <tr>
    <th colspan="2">스프링 mvc게시판 내용</th>
  </tr>
  <tr>
    <th>제목</th> <td>${b.title}</td>
  </tr>
  <tr>
    <th>내용</th> <td>${cont}</td>
  </tr>
  <tr>
    <th>조회수</th> <td>${b.viewcnt}</td>
  </tr>
  <tr>
    <th colspan="2">
      <input type="button" value="수정" onclick="location=
    	  '/board/board_edit?bno=${b.bno}&page=${page}';"/>
      <input type="button" value="삭제" onclick="location=
    	  '/board/board_del?bno=${b.bno}&page=${page}';"/>
      <input type="button" value="목록" onclick="location=
    	  '/board/board_list?page=${page}';"/>
    </th>
  </tr>
</table>

<br/>
<hr/>
<br/>

 <%--댓글 수정화면 --%>
  <div id="modDiv" style="display:none;">
   <%-- css display:none;에 의해서 댓글 수정화면이 안보이게 함 --%>
    
    <div class="modal-title"></div>  <%--댓글번호 --%>
    <div>
      <textarea rows="3" cols="30" id="replytext"></textarea>
    </div>
    <div>
      <button type="button" id="replyModBtn">댓글 수정</button>
      <button type="button" id="replyDelBtn">댓글 삭제</button>
      <button type="button" id="closeBtn" onclick="modDivClose();">닫기</button>
    </div>
  </div>
  
  <h2>비동기식 아작스 연습(REST api)</h2>
  
  <div>
  <div>
  	댓글 작성자:<input name="replyer" id="newReplyWriter"/> <%--type속성을생략하면 기본값이 text이다. --%>
  </div>
  <br/>
  <div>
  	댓글 내용:<textarea rows="5" cols="30" name="replytext" id="newReplyText"></textarea>
  </div>
  <br/>
    <button id="replyAddBtn">댓글 등록</button>
  </div>
  
  <br/>
  <hr/>
  [댓글개수: <span style="color:red; font-weight:bolder; font-size:26px; border:5px dotted orange;
  border-radius:15px; padding:3px; box-shadow:3px 3px 3px gray;">
  ${b.replycnt}</span> 개]
  <br/>
  
  <%--댓글 목록 --%>
  <ul id="replies"></ul><%--비동기식으로 가져온 댓글목록 --%>
  
  
  <script>
    $bno=${b.bno};//게시판 번호
    
    getAllList();//댓글 목록함수를 호출
    
    //댓글 목록
    function getAllList(){
    	$.getJSON("/replies/all/"+$bno, function(data){//jQuery 비동기식 $.getJSON()함수는 get방식으로 접근하는 
    		//json데이터를 처리, 비동기식으로 가져온 것이 성공시 data 매개변수에 가져온 값을 저장한다.
    		$str="";
    	
    		$(data).each(function(){//each()함수에의해서 li태그 단위로 댓글 개수만큼반복
    			$str += "<li data-rno='"+this.rno+"' class='replyLi'>"
    			+this.rno+" : <span class='com' style='color:blue;font-weight:bold;'>"
    			+this.replytext+"</span>"+"<button>댓글수정</button></li><br/>"    		
    		});
    		$('#replies').html($str);//html()함수로 문자와 태그를 함께변경 적용
    	});
    }//getAllList()
    
    //댓글 추가
    $('#replyAddBtn').on('click',function(){
    	$replyer=$('#newReplyWriter').val();//댓글 작성자
    	$replytext=$('#newReplyText').val();//댓글내용
    	
    	$.ajax({
    		type:'post',//method 방식
    		url:'/replies/addreply',//URL매핑주소 경로
    		headers:{
    			"Content-Type":"application/json",
    			"X-HTTP-Method-Override":"POST"
    		},
    		dataType:'text',
    		data:JSON.stringify({
    			bno:$bno,//게시판 번호값
    			replyer:$replyer,//댓글 작성자
    			replytext:$replytext//댓글 내용
    		}),
    		success:function(data){//비동기식으로 가져오는 것이 성공시 호출되는 콜백 함수, 가져온 문자는 data매개변수에 저장
    			if(data == 'SUCCESS'){
    				alert("댓글이 등록되었습니다!");
    				getAllList();//댓글 목록 함수를 호출
    			}
    		}
    	});//jQuery 비동기식 아작스 함수
    });
    
    //댓글 수정 화면
    $('#replies').on('click',".replyLi button",function(){
    	var reply=$(this).parent();//this는 버튼,button의 부모요소인 li태그를 가리킴
    	$rno = reply.attr("data-rno");//댓글번호
    	$replytext = reply.children(".com").text();//댓글내용
    	
    	$('.modal-title').html($rno);//댓글번호가 표시
    	$('#replytext').val($replytext);//댓글 내용이 표시
    	$('#modDiv').show("slow");//display:none; css에 의해서 숨겨진 댓글 수정화면을 표시하게 한다.
    });
        
    //댓글 수정화면 닫기
    function modDivClose(){
    	$('#modDiv').hide('slow');
    }
    
    //댓글 수정 완료
    $('#replyModBtn').on('click',function(){
    	$rno=$('.modal-title').html();//댓글 번호
    	$replytext=$('#replytext').val();//수정할 댓글 내용
    	
    	$.ajax({
    		type:'put',
    		url:'/replies/'+$rno,
    		headers:{
    			"Content-Type":"application/json",
    			"X-HTTP-Method-Override":"PUT"
    		},
    		data:JSON.stringify({
    			replytext:$replytext //수정할내용
    			
    		}),
    		dataType:'text',
    		success:function(result){
    			if(result == 'SUCCESS'){
    				alert('댓글이 수정되었습니다!');
    				$('#modDiv').hide('slow');
    				getAllList();//댓글 목록 함수 호출
    				
    			}
    		}
    	});
    });
    
    
    //댓글 삭제
    $('#replyDelBtn').on('click',function(){
    	var rno=$('.modal-title').html();//댓글 번호
    	
    	$.ajax({
    		type:'delete',//ReplyController.java에서 지정한 삭제 메서드 방식
    		url:'/replies/'+rno,//삭제url매핑 주소
    		headers:{
    			"Content-Type":"application/json",
    			"X-HTTP-Method-Override":"DELETE"
    		},
    		dataType:'text',
    		success:function(data){
    			if(data =="SUCCESS"){
    				alert("댓글이 삭제되었습니다!");
    				$('#modDiv').hide('slow');//댓글 수정화면을 닫는다.
    				getAllList();//댓글 목록함수 호출
    			}
    		}
    		
    	});
    });
  </script>
</body>
</html>