/**
 * board.js
 */
 
 function check(){
    $writer=$.trim($('#writer').val());//trim()함수는 양쪽공백을 제거
    if($writer.length==0){//length속성은 문자열 길이를 반환한다.첫문자는 1부터 시작
      alter('글쓴이를 입력하세요!');
      $('#writer').val('');//글쓴이 입력박스 초기화
      $('#writer').focus();//글쓴이 입력박스로 포커스 이동
      return false;
    }
    if($.trim($('#title').val()) == '' ){
      window.alert('글제목을 입력하세요!');
      $('#title').val('').focus();
      return false;
    }   
    if($.trim($('#content').val()).length == 0){
      alert('글내용을 입력하세요!');
      $('#content').val('').focus();
      return false;
    }
 }//check()