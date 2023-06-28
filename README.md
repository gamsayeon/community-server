# community-server
inven 과 같은 커뮤니티 서비스의 벡엔드 서버
- 참고 : https://lolchess.gg/board/tft/list

---
# 목적
- 대용량 트래픽을 고려한 어플리케이션 (초당 1000 tps 이상의 API 사용)
  [성능테스트_계획서.pdf](성능테스트_계획서.pdf)
- 객체지향적으로 접근하여 유지보수할때 클린 코드구현을 통해 가독성을 높이는 목적
- 단위테스트를 통한 검증가능한 코드

---
# 사용기술
- JAVA8, Spring Boot, MyBatis, MySQL, Redis

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
- 게시글 랭킹 기능


---
# ERD(Entity Relationship Diagram)
![image](https://github.com/j-lab-edu/community-server/assets/75170367/6ee3e4bb-1447-49c6-aab6-46bfb5a05005)

---
# 시퀀스
<details><summary>Sequence Diagram</summary>

## 게시글 검색
![image](https://user-images.githubusercontent.com/75170367/230763012-1c418a01-5c34-49ac-bb45-16a3572c29c7.png)

## 회원가입
![image](https://user-images.githubusercontent.com/75170367/230762974-bee558dc-8645-4367-bb65-a0294e6ba28e.png)

## 로그인
![image](https://user-images.githubusercontent.com/75170367/230763001-81b7c84e-2886-46c5-92e4-c9a331f12eb4.png)

## 로그아웃
![image](https://user-images.githubusercontent.com/75170367/230762994-dfad4780-3e74-445f-9463-4efcecd178e4.png)

## 회원탈퇴
![image](https://user-images.githubusercontent.com/75170367/230762986-e86c0626-40b0-4fed-865e-0e4d5ed3695f.png)

<!-- ## 카테고리 추가
## 카테고리 삭제
## 게시글 추가
## 게시글 삭제
## 게시글 수정
## 게시글 조회
## 공지 게시글 추가
## 댓글 기능 -->
</details>
