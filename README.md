**나만의 일정 관리 앱 서버 만들기**
1. Use Case Diagram

   ![ScheduleManagementSystem drawio (1)](https://github.com/ggumi030/ScheduleManagementApplicationServer/assets/130031828/dbd11f75-c118-40b2-9b87-ee755b39aea5)


   <br/>

3. API 명세

    https://documenter.getpostman.com/view/34881408/2sA3JRaetn

  <br/>

3. ERD
   
![todoApp drawio (1)](https://github.com/ggumi030/ScheduleManagementApplicationServer/assets/130031828/64ceb312-8ddd-4134-8586-9c27b24a7836)


<br/>
<br/>

## 기능
### 기능 1 : 회원가입
### 기능 2 : 로그인
### 기능 3 : 일정 작성
### 기능 4 : 선택한 일정 조회
### 기능 5 : 일정 목록 조회
### 기능 6 : 선택한 일정 수정
### 기능 7 : 선택한 일정 삭제
### 기능 8 : 선택한 일정의 댓글 등록
### 기능 9 : 등록한 댓글 수정
### 기능 10 : 등록한 댓글 삭제

<br/>
<br/>

## 조건
### 기능 1 : 회원가입
- `username`은  `최소 4자 이상, 200자 이하이며 이메일 형식으로 구성되어야 한다.
- `password`는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.


<br/>

### 기능 2 : 로그인
- 회원가입 된 유저만 로그인이 가능하게 한다.
- Access Token 유효기간이 지난 후 Refresh Token 갱신하지 않으면 접근 불가
- Refresh Token 만료가 되면 인증 실패 처리 및 재로그인 유도
- 스프링 시큐리티 사용하지 않고 구현

<br/>

### 기능 3 : 일정 작성
### 기능 6 : 선택한 일정 수정
### 기능 7 : 선택한 일정 삭제
### 기능 8 : 선택한 일정의 댓글 등록
### 기능 9 : 등록한 댓글 수정
### 기능 10 : 등록한 댓글 삭제
- 로그인을 하지 않으면 기능을 사용할 수 없다.
- 유효한 토큰인 경우에만 일정과 댓글을 작성할 수 있다.
- 일정을 생성한 사용자와 동일한 `username`이면 수정할 수 있다.
- 댓글을 작성한 사용자와 동일한 `username`이면 수정할 수 있다.

<br/>

### 기능 4 : 선택한 일정 조회
### 기능 5 : 일정 목록 조회
 - 모든 사용자가 조회 할 수 있다.

<br/>
<br/>

## 예외처리
- 선택한 일정 정보가 이미 삭제되어 조회할 수 없는 경우
- 삭제하려는 일정 정보가 이미 삭제 상태인 경우
- 선택한 일정의 ID를 입력 받지 않은 경우
- 댓글 내용이 비어 있는 경우
- 일정이 DB에 저장되지 않은 경우
- 선택한 일정이나 댓글의 ID를 입력 받지 않은 경우
- 일정이나 댓글이 DB에 저장되지 않은 경우
- 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우
- 선택한 일정이나 댓글의 ID를 입력받지 않은 경우
- 일정이나 댓글이 DB에 저장되지 않은 경우
- 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우
- 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때
    - 에러 메세지 : 토큰이 유효하지 않습니다.
- 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닐 때
    - 에러 메세지 : 작성자만 삭제/수정할 수 있습니다.
- DB에 이미 존재하는 `username`으로 회원가입을 요청할 때
    - 에러 메세지 : 중복된 `username` 입니다.
- 로그인 시, 전달된 `username`과 `password` 중 맞지 않는 정보가 있을 때
    - 에러 메시지 : 회원을 찾을 수 없습니다.


