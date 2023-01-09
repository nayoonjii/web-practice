-- tbl_member테이블 생성
create table tbl_member(
  userid varchar2(100) primary key --회원아이디
  ,userpw varchar2(100) not null --비번
  ,username varchar2(50) not null --회원이름
  ,email varchar2(100) --전자우편
  ,regdate date --등록날짜
  ,updatedate date --수정날짜
);

select * from tbl_member;

--tbl_board 연습용스프링 MVC게시판 테이블 생성
create table tbl_board(
  bno number(38) primary key --게시판번호
  ,writer varchar2(50) not null --글쓴이
  ,title varchar2(200) not null --글제목
  ,content varchar2(4000) --글내용
  ,viewcnt number(38) default 0 --조회수.default 0 제약조건이 주어지면 굳이 레코드를 저장하지 않아도
  --기본값 0이 저장됨
  ,regdate date --등록날짜
);

--댓글 수를 카운터해서 저장하는 replycnt 컬럼추가
alter table tbl_board
add(replycnt number(38) default 0);
--댓글이 추가되면 +1, 댓글이 삭제되면 -1

--각 게시판 번호의 댓글 수를 카운터해서 replycnt 컬럼 레코드를 수정
update tbl_board set replycnt=(select count(rno) from tbl_reply where bno=tbl_board.bno)
where bno>0;
commit;

select * from tbl_board order by bno desc;

--bno_Seq라는 시퀀스를 삭제
drop sequence bno_seq;

--bno_seq 시퀀스 생성
create sequence bno_seq
start with 1 --1부터 시작
increment by 1 --1씩 증가
nocache; --임시 메모리를 사용안함


--bno_seq시퀀스 다음 번호값을 확인
select bno_seq.nextval from dual;

--댓글 테이블 생성
create table tbl_reply(
  rno number(38) primary key --댓글 번호
  ,bno number(38) --tbl_board테이블의 게시판 번호값만 저장되게 주종관계설정. 추가로 외래키 설정할 것임
  ,replyer varchar2(100) not null --댓글 작성자
  ,replytext varchar2(4000) not null --댓글 내용
  ,regdate date --등록 날짜
  ,updatedate date --수정날짜
);

--bno컬럼 외래키(foreign key) 설정
alter table tbl_reply add constraint tbl_reply_bno_fk
foreign key(bno) references tbl_board(bno);

--rno_seq 시퀀스(번호 발생기) 생성
create SEQUENCE rno_seq
start with 1 --1부터 시작옵션
increment by 1 --1씩증가옵션
nocache;

--rno_seq시퀀스 다음 번호값 확인
select rno_seq.nextval from dual;

select * from tbl_reply order by rno desc;

insert into tbl_reply(rno,bno,replyer,replytext,regdate) 
    values(rno_seq.nextval,23,2222,23424,sysdate);
commit;


--Spring AOP실습을 위한 tbl_user테이블을 생성
create table tbl_user(
  uid2 varchar2(50) primary key --회원아이디
  ,upw varchar2(50) not null --회원 비번
  ,uname varchar2(50) not null --회원이름
  ,upoint number(38) default 0 --메시지가 보내지면 포인트 점수 10점 업데이트
);

insert into tbl_user (uid2,upw,uname)values('user00','user00','홍길동');
insert into tbl_user (uid2,upw,uname)values('user01','user01','이순신');

select * from tbl_user order by uid2 asc;

--tbl_message테이블 생성
create table tbl_message(
  mid number(38) primary key
  ,targetid varchar2(50) not null --외래키 추가 설정,tbl_user테이블의 uid2컬럼 회원아이디값만 저장된다.
  ,sender varchar2(50) not  null --메시지를 보낸 사람
  ,message varchar2(2000) not null --보낸 메시지
  ,senddate date --메시지를 보낸 날짜
);

--테이블 수정문으로 외래키 추가설정
alter table tbl_message add constraint tbl_message_targetid_fk
foreign key(targetid) references tbl_user(uid2);

--mid_no_seq 시퀀스 생성
create sequence mid_no_seq
start with 1
increment by 1
nocache;

--시퀀스 다음 번호값 확인
select mid_no_seq.nextval from dual;

select * from tbl_message order by mid asc;

delete tbl_message where mid=3;

commit;











