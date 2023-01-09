create table users(
  username varchar2(50) primary key --회원아이디
  ,password varchar2(50) not null --비번
  ,enabled char(1) default '1'
);

select * from users;

--회원아이디에 권한부여 테이블
create table authorities(
  username varchar2(50) not null --아이디
  ,authority varchar2(50) not null --아이디에 준 권한
  ,constraint authorities_username_fk foreign key(username)
  references users(username) --외래키 (주종관계에서 종속테이블)
);

--인덱스 설정
create unique index ix_auth_username on authorities (username,authority);

--user테이블에 샘플 레코드 3개 insert
insert into users(username,password) values('user00','pw00');
insert into users(username,password) values('member00','pw00');
insert into users(username,password) values('admin00','pw00');

commit;
select* from users order by username asc;

--권한 부여 레코드 4개저장
insert into authorities (username,authority) values('user00','ROLE_USER');
insert into authorities (username,authority) values('member00','ROLE_MANAGER');
insert into authorities (username,authority) values('admin00','ROLE_MANAGER');
insert into authorities (username,authority) values('admin00','ROLE_ADMIN');

commit;
select * from authorities order by username;

drop table tbl_member;

create table tbl_member(
  userid varchar2(50) primary key --회원아이디
  , userpw varchar2(50) not null --비밀 번호
  , username varchar2(50) not null --회원이름
  , regdate date default sysdate --가입날짜
  , updatedate date default sysdate --수정날짜
  , enabled char(1) default'1'
);

alter table tbl_member modify(userpw varchar2(200));
select * from tbl_member order by userid asc;

create table tbl_member_auth (--권한 부여 테이블
  userid varchar2(50) not null --아이디
  , auth varchar2(50) not null --권한부여
  ,constraint tbl_member_auth_userid_fk foreign key(userid) REFERENCES tbl_member(userid)
  --외래키로 설정되어서tbl_member userid 컬럼 레코드 아이디값만 저장됨
);

select * from tbl_member_auth order by userid; --asc문은 오름차순 정렬 .생략가능

--스프링 시큐리티 자동 로그인 정보를 유지하는 테이블

create table persistent_logins(
username varchar2(64) not null --회원아이디
,series varchar2(64) primary key --비번
,token varchar2(64) not null --토큰 정보
,last_used timestamp not null --로그인 한 날짜 시간
);

select * from persistent_logins;











