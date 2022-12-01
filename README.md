---
# community-server
커뮤니티 서비스의 벡엔드 서버

---
# 목적
- 대용량 트레픽을 고려한 어플리케이션
- 객체지향적으로 접근하여 유지보수할때 클린 코드구현을 목적
- 단위테스트를 통한 검증가능한 코드
- 참고 : https://lolchess.gg/board/tft/listhttps://lol.inven.co.kr/

---
# 기획
- 커뮤니티 사이트를 구현함으로써 자유롭게 소통하는 및 정보 공유 사이트를 목표로 구현
- ****https://ovenapp.io/view/Pv1HR7ajNN47W6qWgKHjIro334XPQvBj/****

---
# 프로그램 주요 기능
- 회원가입
  - 회원 탈퇴, 아이디 및 닉네임 중복체크, 비밀번호 암호화
- 로그인, 로그아웃 기능
- 카테고리 관리
  - 카테고리 추가, 삭제
- 게시글 관리
  - 게시글 & 파일 추가, 삭제, 수정, 조회
  - 유저 정보, 게시글 제목, 게시글 내용 등
- 공지글(관리자만) 추가 기능
- 게시글 검색 기능
  - 작성 유정 아이디, 게시글 제목, 게시글 내용 등을 통해 검색
- 댓글 기능

---
# 사용기술
- JAVA8, Spring Boot, MyBatis, MySQL, Redis

---
# ERD(Entity Relationship Diagram)
<details><summary>ERD</summary>
<img width="704" alt="ERD" src="https://user-images.githubusercontent.com/75170367/204240631-3f477b7a-0c12-4caa-b515-3c4f66bfb3a7.png">
</details>

---
# 시퀀스
<details><summary>Sequence Diagram</summary>

## 회원가입
![회원가입](https://user-images.githubusercontent.com/75170367/204241015-42fee152-2938-4e7c-8014-8f7664cd955a.jpg)

## 로그인
![로그인](https://user-images.githubusercontent.com/75170367/204241296-ab0a5f18-053f-41ca-aebe-4011c6830d72.jpg)

## 로그아웃
![로그아웃](https://user-images.githubusercontent.com/75170367/204241327-56cb2cb7-547a-4ef7-a56c-5f9c09c066aa.jpg)

## 회원탈퇴
![회원탈퇴](https://user-images.githubusercontent.com/75170367/204241275-5d721efc-4e07-4ce9-b212-133ce3234f9e.jpg)

## 카테고리 추가
## 카테고리 삭제
## 게시글 추가
## 게시글 삭제
## 게시글 수정
## 게시글 조회
## 공지 게시글 추가
## 게시글 검색
## 댓글 기능
</details>
