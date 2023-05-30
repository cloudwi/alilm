# Musinsa-Stock-Notification-Bot

## 버전 소개
무신사 재고 추적기가 버전 2로 업데이트 될 예정입니다.
- 현재는 로컬에서 프로그램을 돌려서 사용해야 합니다. (일반 사용자에게 난이도가 너무 높음)
  따라서 여러명의 유저가 함께 사용할 수 있는 웹 서비스로 탈 바꿈 할 것입니다.
- 취업을 준비한다는 핑계로 프로젝트에 잠시 관심이 없었는데 다시 찾아왔습니다.
- 준비 기간 도메인 주도 설계 시작하기 책을 봤습니다. 학습한 지식을 녹여 보겠습니다.

### 프로젝트 기간
12월 29일

### 기획의도
> 무신사에서 사고싶은 옷이 품절이라 기다려본 경험이 있습니다.
> 문의도 넣어보았지만 돌아오는 답변은 아래와 같습니다.
> 따라서 정확한 일정을 모르는 
> 1. 다른 사람이 반품 
> 2. 주문 취소
> 3. 알려지지 않은 재입고
>
> 가 되어야 구매가 가능합니다.

![스크린샷 2023-02-09 03 53 43](https://user-images.githubusercontent.com/86584887/217726462-9f6f79c0-b95f-4b7c-878f-0faebb0e5aab.png)

> 그렇다면 매번 무신사에서 내가원하는 상품 페이지에 들어가서 직접 확인해야하는 노동이 필요합니다.
> 위 일을 자동으로 컴퓨터에게 시켜서 내가 원하는 상품이 입고되었다면 알림을 받을 수 있도록 하는 서비스를 만들어 보고 싶었습니다.

### 사용방법
1. code clone
```
git clone https://github.com/cloudwi/Musinsa-Stock-Notification-Bot.git
```
2. 토큰번호, 챗 Id 환경변수로 등록 
- 블로그에서 알려주는 텔레그램 토큰과 chat_id 준비 https://kshman94.tistory.com/40

or

- cd src/main/resources/application.yml 에 telegramToken, chat_id를 본인의 정보로 수정 해야합니다..
3. Java 애플리케이션 실행
```
./gradlew build
java -jar Musinsa-Stock-Notification-Bot-0.0.1-SNAPSHOT.jar
```
4. 상품 등록 그리고 기다림 ...
<img src=img.png width="250"/>
- /add 의 id는 원하는 상품의 번호이다 예시로 아래의 사진 왼쪽 상단에 표시하고있는 7자리 번호입니다.
- 그리고 ,{원하는 사이즈} 를 입력하면 됩니다.

![스크린샷 2023-02-09 04 00 30](https://user-images.githubusercontent.com/86584887/217726498-eb24064a-73cf-4bcc-afdb-14b0b549688a.png)

```
/add 2921758,30 //상품등록 명령어
```
```
/findAll //등록 상품 전체 조회 명령어
```
```
/delete 2921758,30 //등록된 상품 
```
