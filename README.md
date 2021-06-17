# BOOKI 
독서 기록 및 채팅

글자 추출 및 번역 어플리케이션

### * 서버 repository
https://github.com/serim88/BOOKI

### 주요 기능

#### splash 화면 / 회원가입 및 로그인

![login](https://user-images.githubusercontent.com/53509789/122339345-ee3f8780-cf7b-11eb-9295-6d6f4cdc8a88.gif)

- splash 화면 1.5초 후 이동한다. (자동 로그인 되어있으면 홈화면, 되어있지 않으면 로그인 화면으로)
- 회원 가입시 아이디 중복 여부 체크 후 가입


#### 홈 

![autologin](https://user-images.githubusercontent.com/53509789/122339348-ef70b480-cf7b-11eb-8e07-e51d4326964e.gif)

1. 자동 로그인 체크
- 로그인 성공시 서버에서 jwt 토큰을 받아 헤더에 저장한다.
- 이후 로그인시 jwt 토큰 확인 후 자동 로그인한다.

![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/53509789/122341432-6b6bfc00-cf7e-11eb-8d14-46bfc3b0ddec.gif)

2. 카테고리 별 베스트셀러 (인터파크 api)
- 탭 레이아웃을 이용하여 카테고리 별로 베스트 셀러 도서를 보여준다.
3. 검색 기능 (인터파크 api)
- google stt(speach to text)를 이용하여 음성 인식 검색 구현 , 도서 검색
4. 추천 도서
해당 사용자의 읽을 책 데이터 기반으로 추천 도서를 보여준다.

#### 도서 상세보기

![detail1_gif](https://user-images.githubusercontent.com/53509789/122339583-3eb6e500-cf7c-11eb-8444-1f9fdc118846.gif)

1. 베스트셀러, 검색, 독서 피드 등 모든 도서들의 상세보기 페이지 (인터파크 api)
2. 읽을 책, 읽고 있는책, 읽은 책 
- 읽을 책, 읽고 있는책, 읽은 책을 체크 할 수 있으며, 이는 나의 서재에서 관리 할 수 있다.
- 읽을 책에는 다짐, 읽고 있는 책에는 시작일, 읽은 책에는 종료일을 추가 할 수 있다.
3. 관련도서
- 해당 도서와 같은 카테고리의 도서들을 관련 도서로 보여준다.

![detail2_gif](https://user-images.githubusercontent.com/53509789/122339612-437b9900-cf7c-11eb-92a1-db8ec3a9a9b1.gif)

4. 해당 도서 관련 독서 피드 / 채팅방
- 드로어를 통해 해당 도서 관련 독서 피드, 해당 도서 관련 채팅방 목록 확인이 가능하다. 

#### 독서 피드

![feed1__gif](https://user-images.githubusercontent.com/53509789/122339461-162eeb00-cf7c-11eb-9216-b09caaa7a112.gif)
![feed2_gif](https://user-images.githubusercontent.com/53509789/122339468-18914500-cf7c-11eb-9149-b00ecf4b2097.gif)

1. 독서 기록 
- 모든 사용자들이 작성한 독서 기록을 피드로 보여준다. (비밀글 제외)
2. google tts (text to speach)
- 피드 상세보기에서 floating button을 이용하여 해당 피드 내용을 들을 수 있다.
3. 좋아요, 댓글 기능
- 피드의 좋아요, 댓글 작성이 가능하다.
4. 다른 유저의 서재로 이동 
- 피드 목록, 피드 상세보기 페이지, 피드 댓글의 작성자 아이디를 누르면 해당 아이디의 서재로 이동한다.

#### 글자 추출 및 번역

![translate_gif](https://user-images.githubusercontent.com/53509789/122339511-247d0700-cf7c-11eb-9b2e-e66bcb396968.gif)

1. 글자 추출 (tess-two 라이브러리)
- 추출할 부분을 사진 찍어 글자 추출이 가능하다.
2. 번역 ( 파파고 api)
- 추출한 글자 혹은 입력을 통해 원하는 언어로 번역이 가능하다.

#### 채팅

![chat_gif](https://user-images.githubusercontent.com/53509789/122339524-2a72e800-cf7c-11eb-910c-7af10c39a589.gif)

1. 나의 채팅방 목록
- 내가 참여한 채팅방 목록을 보여준다.
2. 해당 도서 채팅방 목록
- 해당 도서 관련 채팅방 목록을 보여준다.

#### 나의 서재 (마이 페이지)

![mypage](https://user-images.githubusercontent.com/53509789/122339924-9a816e00-cf7c-11eb-8b0c-cceee65456d9.gif)

1. 사용자 정보
- 해당 사용자의 정보(id, 프로필 이미지, 등급)을 보여준다.
- 해당 사용자의 읽은 책 권수에 따라 등급을 정해서 보여준다. (0권-10권 silver, 10권-20권 gold, 20권-30권 vip, 30권 이상 vvip )
2. 도서 현황
- 탭 레이아웃을 통해 읽을 책, 읽고 있는책, 읽은 책을 구분하여 보여준다.
3. 독서 피드 현황
- 탭 레이아웃을 통해 나의 피드, 내가 좋아요한 피드 리스트를 구분하여 보여준다.
- 해당 사용자가 비밀글로 설정한 글은 이 곳에만 보여진다.

#### 다른 유저의 서재
1. 사용자 정보
- 해당 사용자의 정보(id, 프로필 이미지, 등급)을 보여준다.
- 해당 사용자의 읽은 책 권수에 따라 등급을 정해서 보여준다. 
2. 도서 현황
- 탭 레이아웃을 통해 읽을 책, 읽고 있는책, 읽은 책을 구분하여 보여준다.
3. 독서 피드 현황
- 해당 사용자의 작성글 리스트를 보여준다.（비밀글 제외）
