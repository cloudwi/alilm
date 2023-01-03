# Musinsa-Stock-Notification-Bot
- 무신사에서 내가 사고싶은 옷이 품절이라 재 입고 문의를 넣어본 적이 있습니다.
  - 하지만 돌아오는 답변은 "입고 예정이 없습니다." 따라서 저는 기약없는 기다림을 해야 했습니다. 
  - 그런데 누군가 주문을 취소했다면 혹은 내가 모르는 사이에 입고가 되었다면 ?. 너무 아쉽겠죠
  - 이 서비스로 기다림을 줄여보세요

## 사용방법
- git clone https://github.com/cloudwi/Musinsa-Stock-Notification-Bot.git
- 블로그에서 안내하는 텔레그램 토큰과 chat_id 준비 https://kshman94.tistory.com/40
- cd src/main/resources/application.yml 에 telegramToken, chat_id를 본인이 정보로 수정
- Java 애플리케이션 실행
- 구매하고 싶지만 품절인 상품 품번 확인 아래이미지 url의 2747337과 같은 자리에 있습니다.
  
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/5afcf943-0e03-4e31-9e8d-f69d414fa15a/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221229%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221229T180207Z&X-Amz-Expires=86400&X-Amz-Signature=f49a619e1cc33d5b5b47fdc85ee639135bcfdad2d29142c2bbb9d7783d1b42de&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject" width="400" height="400"/>
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/83359606-b39c-44b2-a440-4a931ae7939d/KakaoTalk_Photo_2022-12-30-03-01-34.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221229%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221229T180636Z&X-Amz-Expires=86400&X-Amz-Signature=35c911cd6e6e4f35b6110fbb8c891ea2e16c6e88bbe7fca99addebfbedf0a2fc&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22KakaoTalk_Photo_2022-12-30-03-01-34.jpeg%22&x-id=GetObject" width="200" height="400"/>
363