# Musinsa-Stock-Notification-Bot

### 프로젝트 기간
11월 29 ~ 1월 6일 

### 기획의도
> 무신사에서 사고싶은 옷이 품절이라 기다려본 경험이 있습니다.
> 문의도 넣어보았지만 돌아오는 답변은 아래와 같습니다.
> 따라서 정확한 일정을 모르는 
> 1. 다른 사람이 반품 
> 2. 주문 취소
> 3. 알려지지 않은 재입고
>
> 가 되어야 구매가 가능합니다.

![스크린샷 2023-02-09 03.53.43.png](..%2F..%2F..%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-02-09%2003.53.43.png)

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
- cd src/main/resources/application.yml 에 ${telegramToken}, ${chat_id}를 본인이 정보로 수정 해도됩니다.
3. Java 애플리케이션 실행
```
./gradlew build
java -jar Musinsa-Stock-Notification-Bot-0.0.1-SNAPSHOT.jar
```
4. 상품 등록 그리고 기다림 ...
<img src=img.png width="250"/>
- /add 의 id는 원하는 상품의 번호이다 예시로 아래의 사진 왼쪽 상단에 표시하고있는 7자리 번호입니다.
- 그리고 ,{원하는 사이즈} 를 입력하면 됩니다.
![스크린샷 2023-02-09 04.00.30.png](..%2F..%2F..%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-02-09%2004.00.30.png)