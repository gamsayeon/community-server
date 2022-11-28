# community-server
커뮤니티 서비스의 백엔드 서버

# 목적
- 대용량 트레픽을 고려한 어플리케이션
- 객체지향적으로 접근하여 유지보수할때 클린 코드구현을 목적
- 단위테스트를 통한 검증가능한 코드
- 참고 : https://lolchess.gg/board/tft/listhttps://lol.inven.co.kr/

# 기획
- 커뮤니티 사이트를 구현함으로써 자유롭게 소통하는 및 정보 공유 사이트를 목표로 구현
- ****https://ovenapp.io/view/Pv1HR7ajNN47W6qWgKHjIro334XPQvBj/****

# 프로그램 주요 기능
- 회원가입(아이디 및 닉네임 중복체크, 비밀번호 암호화 등 회원 관리 기능)
- 로그인, 로그아웃 기능
- 회원 탈퇴 기능
- 카테고리 관리(추가, 삭제)
- 게시글 관리(게시글 추가, 삭제, 수정, 조회 & 파일 추가)
- 공지글(관리자만) 추가 기능
- 게시글 검색 기능 (아이디, 단어를 이용한 검색)
- 댓글 기능

# ERD(Entity Relationship Diagram)
![image](https://user-images.githubusercontent.com/75170367/204200258-d8dee1a5-0c2b-4ee4-96cd-bdc784794efd.png)

# 시퀀스
## 회원가입
![회원가입](https://user-images.githubusercontent.com/75170367/204234559-e1d44c2c-9d02-404e-9665-d846d8726264.jpg)

## 로그인
![로그인](https://user-images.githubusercontent.com/75170367/204235305-936f5b49-21fe-4ac3-b9f0-575930854315.jpg)

## 로그아웃
![로그아웃](https://user-images.githubusercontent.com/75170367/204237601-95382866-4126-40f8-97d9-2a7b39d1893d.jpg)

## 회원 탈퇴
![회원탈퇴](https://user-images.githubusercontent.com/75170367/204236233-d5d5ca4c-f51f-4ab1-b0d9-adc093171999.jpg)

## 카테고리 추가
## 카테고리 삭제
## 게시글 추가
## 게시글 삭제
## 게시글 수정
## 게시글 조회
## 공지 게시글 추가
## 게시글 검색
## 댓글 기능
