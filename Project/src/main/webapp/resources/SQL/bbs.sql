--bbs자료실 테이블 생성
create table bbs(
  bbs_no number(38) primary key --자료실번호
  ,bbs_name varchar2(50) not null --글쓴이
  ,bbs_title varchar2(200) not null --글제목
  ,bbs_pwd varchar2(20) not null --비번
  ,bbs_cont varchar2(4000) not null --글내용
  ,bbs_file varchar2(300) --이진파일명
  ,bbs_hit number(38) default 0 --조회수
  ,bbs_ref number(38)--글 그룹 번호 역할=>원본글과 답변글을 서로 묶어주는 기능
  ,bbs_step number(38) --첫번째 답변글이면 1, 두번째 답변글이면 2, 원본글이면 0,즉 원본
  --글과 답변글을 구분하는 기준이면서 몇번째 답변글인가를 알려준다.
  ,bbs_level number(38) --답변글 정렬순서, 답변글과 연관있는 컬럼명은 bbs_ref,bbs_step,
  --bbs_level 이 된다.
  ,bbs_date date --글등록 날짜
);

select * from bbs order by bbs_no desc;

--bbs_no_seq라는 시퀀스를 생성
create SEQUENCE bbs_no_seq
start with 1 --1부터 시작
INCREMENT by 1 --1씩증가
nocache; --임시메모리를 사용하지 않음

--bbs_no_seq 시퀀스 다음 번호값 확인
select bbs_no_seq.nextval from dual;








