# Chapter2 HTTP/1.0의 시맨틱스

## 2.1 단순한 폼 전송 (x-www-form-urlencoded)

폼을 사용한 POST 전송에는 몇가지 방식이 있는데 가장 단순한 전송방식은 다음과 같다.

```html
<form method="POST">
    <input name="title">
    <input name="author">
    <input type="submit">
</form>
```

다음과 같은 curl 커맨드로 폼과 같은 형식으로 전송이 가능하다.

```shell
$ curl --http1.0 -d title="The Art of Community" -d author="Jono Bacon" http://localhost:18888
```

curl 커맨드의 -d 옵션을 사용해 폼으로 전송할 데이터를 설정할 수 있다. -d옵션을 지정하면 브라우저와 똑같이 헤더로 `Content-Type:application/x-www-form-urlencoded` 를 설정한다. 

브라우저는 **RFC 1866**에 책정한 ㅍ변환 포멧에 따라 변환을 실시한다. 이 포맷에서는 알파벳, 수치, 별표, 하이픈, 마침표, 언더스코어의 여섯 종류 문자 외에는 변환이 필요하다. 공백은 +로 바뀐다.

curl에는 `--data-urlencode` 가 있는데 이것은 -d 대신에 사용해서 변환할 수 있다. 이 때 **RFC3986**에서 정의된 방법으로 변환되며 공백이 %20이 된다.

```sh
$ curl --http1.0 -d title="The Art of Community" --data-urlencode author="Jono Bacon" http://localhost:18888
```

어떤 변환 방법을 써도 URL 인코딩으로 부르며 하나로 취급하는 경우가 대부분이다. 웹 폽의 GET의 경우 바디가 아니라 쿼리로서 URL에 부여된다고 **RFC1866**에 정의되어 있다. 형식은 앞장 참조

​                     

## 2.2 폼을 이용한 파일 전송

HTML의 폼에서는 멀티파트 폼 형식이라느 ㄴ인코딩 타입으로 선택할 수 있다. **RFC1867**에 정의되어 있다.

```html
<form action="POST" enctype="multipart/form-data">
</form>
```

보통 HTTP 응답은 한 번에 한 파일씩 반환하므로, 빈 줄을 찾아 그곳부터 Content-Length로 지정된 바이트 수만큼 읽기만하면 데이터를 통째로 가져올수 있다. 하지만 멀티 파트를 이용하는 경우 한 번의 요청으로 복수의 파일을 전송할 수 있으므로 받는 쪽에서 파일을 나눠야 한다. 

다음은 구글 크롬 브라우저의 멀티 파트 폼 형태로 출력했을 때의 헤더이다. Content-Type 말고도 또 하나의 속성이 부여되어 있다. 이것은 경계 문자열이다. 경계문자열은 각 브라우저가 독자적인 포맷으로 랜덤하게 만든다.

```
Content-Type: multipart/form-data; boundary=---WebKitFormBoundaryyOYfbccgoID172j7
```

바디는 다음과 같이 되어 있다. 경계 문자열로 두 개의 블록으로 나뉘어 있다. 또한 맨 끝에는 경계문자열 +-- 라고 되어 있는 줄이 있다.  각각의 블록 내부도 헤더 + 빈 줄 + 콘텐츠로 되어 있다.

헤더에는 Content-Disposition이라는 항목이 있으며 대체로 Content-Type과 같은 것이다.

```
------WebKitFormBoundaryyOYfbccgoID172j7
Content-Dispoistion: form-data; name="title"

The Art of Community
------WebKitFormBoundaryyOYfbccgoID172j7
Content-Disposition: form-data; name="author"

Jono Bacon
------WebKitFormBoundaryyOYfbccgoID172j7--
```

파일 선택 입력을 추가해보자.

```html
<input name="attachment-file" type="file">
```

x-www-form-urlencoded는 이름에 대해서 그 콘텐츠라는 1:1 정보밖에 가질 수 없지만 multipart/form-data는 항목마다 추가 메타정보를 태그로 가질 수 있다. 표시된 결과를 보면 파일을 전송할 때 이름, 파일명(test.txt), 파일 종류(text/plain), 그리고 파일 내용이라는 세 가지 정보가 전송되는 것을 알 수 있다.

파일 전송시에 multipart/form-data를 지정하지 않아 실패했던 이유는 x-www-form-urlencoded에서는 파일 전송에 필요한 정보를 모두 보낼 수가 없어, 파일 이름만 전송해버리기 때문이다.

```
------WebKitFormBoundaryyOYfbccgoID172j7
Content-Disposition: form-data; name="attachment-file"; filename="test.txt"
Content-Type: text/plain

hello world

------WebKitFormBoundaryyOYfbccgoID172j7--
```

-d 대신 -F를 사용하는 것만으로 curl 커맨드는 enctype="multipart/form-data"가 설정된 폼과 같은 형식으로 송신한다. -d와 -F를 섞어 쓸 수는 없다. 파일 전송은 @를 붙여 파일 이름을 지정하면, 그 내용을 읽어와서 첨부한다.

```shell
# 파일 내용을 test.txt에서 취득, 파일명은 로컬 파일명과 같다. 형식도 자동 설정
$ curl --http1.0 -F attachment-file@test.txt http://localhost:18888

# 파일 내용을 test.txt에서 취득. 형식은 수동으로 지정
$ curl --http1.0 -F "attachment-file@test.txt;type=text/html" http://localhost:18888

# 파일 내용을 test.txt에서 취득. 파일명은 지정한 파일명을 이용.
$ curl --http1.0 -F "attachment-file@test.txt;filename=sample.txt" http://localhost:18888
```

`@파일명` 형식은 -d의 x-www-form-urlencoded에서도 사용할수 있다. 이 경우 파일명은 전송되지 않은 채 파일 내용이 전개되어 전송된다. -F일 때 파일 첨부가 아니라 내용만 보내고 싶을 때는 -F "attachment-file=< 파일명" 을 이용한다.

​                     

## 2.3 폼을 이용한 리다이렉트

1장 1.6절에서의 300번대 스테이터스 코드를 이용한 리다이렉트는 몇가지 제한이 있다.

- URL에는 2천자 이내라는 기준이 있어 GET의 쿼리를 보낼 수 있는 데이터양에 한계가 있다.
- 데이터가 URL에 포함되므로 전송하는 내용이 액세스 로그 등에 남을 우려가 있다.

이런 문제를 피하고자 종종 이용되는 방법이 HTML의 폼을 이용한 리다이렉트이다. 서버로부터 리다이렉트할 곳으로 보내고 싶은 데이터가 `<input type="hidden">` 태그로 기술된 HTML이 되돌아온다. 폼에서 보내는 곳이 리다이렉트할 곳이다. 브라우저가 이 HTML을 열면, 로드 직후 발생하는 이벤트로 폼을 전송하므로 즉시 리다이렉트해 이동하게 된다.(??)

이 방법의 장점은 IT에서도 데이터양에 제한이 없다는 점이고, 단점은 순간적으로 빈 페이지가 표시된다는 것과 JS 비활성화일 때에는 자동 전환이 되지 않는다.

```html
HTTP/1.1 200 OK
Date: 21 Jan 2004 08:00:49 GMT
Content-Type: text/html; charset=iso-8859-1

<!DOCTYPE html>
<html>
    <body onload="document.forms[0].submit()">
        <form action="리다이렉트할 주소" method="post">
            <input type="hidden" name="data" value="보낼 메세지"/>
            <input type="submit" value="Continue"/>
        </form>
    </body>
</html>
```



​                   

## 2.4 콘텐트 니고시에이션

통신 방법을 최적화하고자 하나의 요청 안에서 서버와 클라이언트가 서로 최고의 설정을 공유하는 시스템이 콘텐트 니고시에이션이다. 콘텐트 니고시에이션에는 헤더를 이용한다. 니고시에이션할 대상과 니고시에이션에 사용하는 헤더는 아래 4가지이다.

| 요청 헤더       | 응답                              | 니고시에이션 대상 |
| --------------- | --------------------------------- | ----------------- |
| Accept          | Content-Type 헤더                 | MIME 타입         |
| Accept-Language | Content-Language 헤더 / html 태그 | 표시 언어         |
| Accept-Charset  | Content-Type 헤더                 | 문자의 문자셋     |
| Accept-Encoding | Content-Encoding 헤더             | 바디 압축         |

​                        

### 2.4.1 파일 종류 결정

```
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
```

콤마로 항목을 나눈다

- image/webp
- \*/\*;q=0.8

q는 품질 계수라는 것으로 0~1까지의 수치를 설정한다. 기본은 1.0이고 이때는 q가 생략된다. 이 수치는 우선 순위를 나타낸다. 즉 웹 서버가 WebP를 지원하는 WebP를 , 그렇지 않으면 PNG등 다른 포맷(우선순위 : 0.8)을 서버에 보낼 것을 요구하고 있다.

서버는 요청에서 요구한 형식 중에서 파일을 반환한다. 우선순위를 해석해 위에서부터 차례로 지원하는 포맷을 찾고, 일치하면 그 포맷으로 반환한다. 만약 서로 일치하는 형식이 없으면 서버가 406 Not Acceptable 오류를 반환한다.

​             

### 2.4.2 표시 언어 결정

```
Accept-Language: en-US,en;q=0.8,ko;q=0.6
```

en-US, en, ko 라는 무선 순위로 요청을 보낸다. 언어 정보를 담는 상자로서 Colntent-Language헤더가 있지만, 대부분 이 헤더는 사용하지 않는 것 같다. 다음과 같이 HTML 태그 안에서 반환하는 페이지를 많이 볼 수 있다.

```html
<html lang="ko">
```

​                 

### 2.4.3 문자셋 결정

```
Accept-Charset: windows-949,utf-8;q=0.7,*;q=0.3
```

현재 모던 브라우저들은 Accept-Charset를 송신하지 않는다. 아마도 브라우저가 모든 문자셋 인코더를 내장하고 있어, 미리 니고시에이션할 필요가 없어졌기 때문일거라 여겨진다. 문자셋은 MIME 타입과 세트로 Content-Type 헤더에 실려 통지된다.

```
Content-Type: text/html; charset=UTF-8
```

HTML의 경우 문서 안에 쓸 수도 있다. HTML을 로컬에 저장했다가 다시 표시하는 경우도 많으므로, 이 방식도 함께 많이 사용한다.

```html
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
```

HTML의 `<meta http-equiv>` 태그는 hTTP 헤더와 똑같은 지시를 문서 내부에 삽입해서 반환하는 상자이다. HTML5에서는 `<meta charset="UTF-8">` 로 표기할수도 있다.

사용할수 있는 문자셋은 IANA에서 관리된다. [[링크]](https://www.iana.org/assignments/character-sets/character-sets.xhtml)

​                     

### 2.4.4 압축을 이용한 통신 속도 향상

콘텐츠 내용에 따라 다르지만, 혀냊 일반적으로 사용되는 압축 알고리즘을 적용하면 텍스트 파일은 1/10 크기로 압축된다. JSON의 경우 1/20으로 압축할 수 있다.

콘텐츠 압축은 전송 속도 향상 향상 뿐만 아니라 이용 요금에도 영향을 미친다. 또한 모바일 단말은 전파 송수신에 전력을 많이 소비하므로, 전력 소비가 줄어드는 효과도 있다.

콘텐츠 압축 니고시에이션은 모두 HTTP의 헤더 안에서 완료한다. 우선 클라이언트가 수용가능한 압축 방식을 헤더에서 지정한다. 

```
Accept-Encoding: deflate, gzip
```

curl 커맨드에서 --compressed 옵션을 지정하면, -H 옵션으로 위 헤더를 기술한 것과 같다.

```shell
$ curl --http1.0 --compressed http://localhost:18888
```

서버는 전송받은 목록 중 지원하는 방식이 있으면, 응답할 때 그 방식으로 압축하거나 미리 압축된 콘텐츠를 반환한다. 서버가 gzip을 지원하면, 조금 전에 받은 요청에 대한 응답으로 다음과 같은 ㅔ헏가 부여된다. 컨텐츠 데이터양을 나타내는 Content-Length 헤더는 압축된 파일 크기이다.

클라이언트에서 서버로 업로드할 때 압축을 이용하는 방법이 논의되고 있다. 현재 제한된 방식은 ㅎ나 번의 통신으로 완경되진 않지만 기본은 같다. 서버에서 클라이언트로 첫번째 웹페이지를 반환할 때 Accept-Encoding 헤더를 부여하고 그런 다음 클라이언트에서 무언가 업로드할 때 Content-Encoding을 부여한다. 지금의 고속화 방식과는 대조적으로 헤더가 이용된다. 요청, 응답 양쪽에서 똑같이 헤더 구조가 이용되므로 이처럼 간단하게 구현할 수 있다.

무압축을 뜻하는 identity도 사용할 수 있다.

| 이름         | 별명       | 알고리즘                                                     | IANA 등록 |
| ------------ | ---------- | ------------------------------------------------------------ | --------- |
| br           |            | 브로틀리                                                     | ㅇ        |
| compress     | x-compress | 유닉스에 탑재된 compress 커맨드                              | ㅇ        |
| deflate      |            | zlib 라이브러리에서 제공되는<br />압축 알고리즘 (RFC 1951)   | ㅇ        |
| exi          |            | W3C Efficient XML Interchange                                | ㅇ        |
| gzip         | x-gzip     | GZIP 커맨드와 같은 압축 알고리즘<br />(RFC 1952)             | ㅇ        |
| identity     |            | 무압축을 선언하는 예약어                                     | ㅇ        |
| pack200-gzip |            | 자바용 네트워크 전송 알고리즘                                | ㅇ        |
| sdch         |            | Shared Dicttionary Compressing for HTTP<br />미리 교환한 사전을 이용하는 방법 |           |

압축을 이용한 통신량 절감에는 Content-Encoding으로 콘텐츠 크리를 줄이는 방법 말고도, Transfer-Encoding 헤더로 통신 경로를 압축하는 방법도 규격에 있지만, 그다지 사용되지 않는다.

 

​                            

## 2.5 쿠키

쿠키란 웹사이트의 정보를 브라우저 쪽에 저장하는 작은 파일이다. 일반적으로 데이터베이스는 클라이언트가 데이터베이스 관리 시스템에 SQL을 발행해서 데이터를 저장하지만, 쿠키의 경우 거꾸로 서버가 클라이언트(브라우저)에 이파일을 보관해달라고 쿠키 저장을 지시한다.

쿠키도 HTTP 헤더를 기반으로 구현되었다. 예를 들어 이 서버는 최종 액세스 날짜와 시간을 클라이언트에 저장하려고 한다.

```
Set-Cookie: LAST_ACCESS_DATE=Jul/31/2016
Set-Coodie: LAST_ACCESS_TIME=12:04
```

HTTP는 stateless(언제 누가 요청해도 요청이 같으면 결과가 같음)를 기본으로 개발되었지만, 쿠키를 이용하면 서버가 상태를 유지하는 statefull처럼 보이게 서비스를 제공할 수 있다.

브라우저에서도 쿠키를 읽거나 서버에 보낼 때 쿠키를 설정할 수 있다.

CURL에서는 `-c / --cookie-jar ` 옵션으로 지정한 파일에 수신한 쿠키를 저장하고 `-b/--cookie` 옵션으로 지정한 파일에서 쿠키를 읽어와 전송한다. 브라우저처럼 동시에 송수신하려면 둘 다 지정한다. `-b/--cookie` 옵션은 파일에서 읽기만 하는 게 아니라 개별 항목을 추가할 때도 사용할 수 있다.

```shell
$ curl --http1.0 -c cookie.txt -b cookie.txt -b "name=value" http://example.com/helloworld
```

​                     

### 2.5.1 쿠키의 잘못된 사용법

#### 영속성 문제

쿠키는 어떤 상황에서도 확실하게 저장되는 것은 아니다. 브라우저 설정, 방문기록 삭제, 개발자 도구등으로 따라 세션이 끝나면 초기화되거나 쿠키를 보관하라는 서버의 지시를 못하기도 한다. 즉 쿠키의 데이터가 사라질수 있다는 것이다. 그러므로 사라지더라도 문제가 없는 정보나 서버 정보로 복원할 수 있는 자료를 저장하는 용도에 적합하다. 

#### 용량 문제

쿠키의 최대 크기는 4kb 사양으로 정해져 있다. 쿠키는 헤더로서 항상 통신에 부가되므로 통신량이 늘어나는데, 통신량 증가는 요청과 응답 속도 모두에 영향을 미친다. 

#### 보안 문제

secure 속성을 부여하면 HTTPS 프로토콜로 암호화된 통신에서만 쿠키가 전송되지만, HTTP 통신에서는 쿠키가 평문으로 전송된다. 따라서 노출될 위험성이 있으며 암호화가 될지라도 사용자가 자유롭게 접근할 수 있으며 사용자가 쿠키를 수정할 수도 있다. 정보를 넣을 때는 서명, 암호화 처리가 필요하다.

​                           

### 2.5.2 쿠키에 제약을 주다

HTTP 클라이언트는 쿠키를 제한하는 속성을 해석해 쿠키 전송을 제어할 책임이 있다. 속성은 새미콜론으로 구분해서 나열할 수 있으며 대소문자를 구별하지 않는다.

- Expires, Max-Age 속성 : 쿠키의 수명을 설정한다. Max-Age는 초단위로 지정. 현재 시각에 지정된 초수를 더한 시간에서 무효가 된다. Expires는 Wed, 09 Jun 2021 10:18:14 GMT 같은 형식의 문자열을 해석한다.
- Domain 속성 : 클라이언트에서 쿠키를 전송할 대상 서버. 샹략하면 쿠키를 발행한 서버가 된다.
- Path 속성 : 클라이언트에서 쿠키를 전송할 대상 서버의 경로. 생략하면 쿠키를 발행한 서버의 경로이다.
- Secure 속성 : https로 프로토콜을 사용한 보안 접속일 때만 클라이언트에서 서버로 쿠키를 전송한다. 쿠키는 URL을 키로 전송을 결정하므로, DNS 해킹으로 URL을 사칭하면 의도치 않은 서버에 쿠키를 전송할 위험이 있다. DNS해킹은 기기를 조작하지 않고도 무료 와이파이 서비스 등으로 속여 간단히 할 수 있다. Secure 속성을 붙이면 http 접속일 때는 브라우저가 경고를 하고 접속하지 않아 정보 유출을 막게 된다.
- HttpOnly 속성 : 이 속성을 붙이면 js 엔진으로부터 쿠키를 감출 수 있다. 크로스 사이트 스크립팅등 악의적인 js가 실행되는 보안 위험에 대한 방어가 된다.
- SameSite 속성 : 이 속성은 크롬 브라우저 버전 51에서 도입한 속성으로, 같은 오리진(출처)의 도메인에 전송하게 된다.

​                   

## 2.6 인증과 세션

인증에는 몇가지 방식이 있다. 유저명과 패스워드를 매번 클라이언트에서 보내는 방식 2가지를 먼저 보자.

​                          

### 2.6.1 BASIC 인증과 Digest 인증

#### BASIC 인증

가장 간단한 것이  BASIC 인증이다. BASIC 인증은 유저명과 패스워드를 BASE64로 인코딩한 것이다. BASE64 인코딩은 가역변환이므로 서버로부터 복원해 유저명과 패스워드를 추출할 수 있다. 단 SSL/TLS 통신을 사용하지 않은 상태에서 통신이 감청되면 손쉽게 로그인 정보가 유출된다.

```
base64(유저명 + ":" + 패스워드)
```

```shell
# --basic은 생략 가능
# BASIC 인증시 -u/--user 옵션으로 유저명과 패스워드 전송
$ curl --http1.0 --basic -u user:pass http://localhost:18888
```

```
// 다음과 같은 헤더가 부여된다.
Authorization: "Basic dXNlcjpwYXNz"
```

​                             

#### Digest 인증

이보다 더 강력한 방식이 Digest 인증이다. Digest 인증은 해시함수를 이용한다. 아래와 같은 헤더가 부여된다.

```
WWW-Authenticate: Digest realm="영역명", nonce="1234567890", algorithm=MD5, qop="auth"
```

- realm : 보호되는 영역의 이름으로 인증창에 표시된다.
- nonce: 서버가 매번 생성하는 랜덤한 데이터이다.
- qop : 보호 수준을 나타낸다.

클라이언트는 이곳에서 주어진 값과 무작위로 생성한 cnounce를 바탕으로 다음처럼 계산해서 response를 구한다.

```
A1 = 유저명 ":" real ":" 패스워드
A2 = HTTP 메서드 ":" 콘텐츠 URI
response = MD5( MD5(A1) ":" nonce ":" nc ":" cnonce ":" qop ":" MD5(A2) )
```

- nc : 특정 nonce값을 사용해 전송한 횟수이다. qop가 없을 때는 생략한다. 8자리 16진수로 표현한다. 같은 nc값이 다시 사용된 것을 알 수 있으므로, 서버가 리플레이 공격을 탐지할 수 있다. 

클라이언트에서는 생성한 cnonce와 계산으로 구한 response를 부여해 한데 모으고, 다음과 같은 헤더를 덧붙여 재요청을 보낸다.

```
Authorization: Digest username="유저명", realm="영역명", nonce="1234567890",                      url="/secret.html", algorithm=MD5, qop=auth, nc=00000001,
               cnonce="0987654321", response="9d47a3f8b2d5c"
```

서버에서도 이 헤더에 있는 정보와 서버에 저장된 유저명, 패스워드로 같은 계산을 실시한다. 재발송된 요청과 동일한 response가 계산되면 사용자가 정확하게 유저명과 패스워드를 입력했음을 보증할 수 있다. 

```shell
# --digest와 -u/--user 옵션으로 Digest 인증 사용가능
# 401 반환이 안되면 접속이 그대로 종료
$ curl --http1.0 --digest -u user:pass http://localhost:18888/digest

# -v를 붙여보면 한번 401로 거부된 후에 다시 보낸다는 것을 알수 있다.
$ curl --http1.0 --digest -u user:pass http://localhost:18888/digest -v
```

​                           

### 2.6.2 쿠키를 사용한 세션 관리

BASIC 인증과 Digest 인증 모두 많이 사용되지 않는다.

- 특정 폴더 아래를 보여주지 않는 방식으로만 사용할 수 있어서, 톱페이지에 사용자 고유 정보를 제공할 수 없다. 톱페이지에 사용자 고유 정보를 제공하려면 톱페이지도 보호할 필요가 있어, 톱페이지 접속과 동시에 로그인 창을 포시해야 한다. 처음 방문하는 사용자에게 친절한 톱페이지는 아니다.
- 요청할 때마다 유저명과 페스워드를 보내고 계산해서 인증할 필요가 있다. 특히 Digest 인증방식은 계산량도 많다.
- 로그인 화면을 사용자화할 수 없다. 
- 명시적인 로그오프를 할 수 없다.
- 로그인한 단말을 식별할 수 없다. 

최근 많이 사용되는 방식은 폼을 이용한 로그인과 쿠키를 이용한 세션 관리 조합이다. 이방식으로는 폼과 쿠키를 이용하는 단순한 구조가 된다.

클라이언트는 폼으로 ID와 비밀번호를 전송한다. Digest 인증과 달리 유저 아이디와 패스워드를 직접 송신하므로 SSL/TLS이 필수이다. 서버 측에서는 유저 ID와 패스워드를 인증하고 문제가 없으면 세션 토큰을 발행한다. 발행한 토큰은 DB에 저장한다음 토큰은 쿠키로 클라이언트에 되돌아간다. 두 번째 이후 접속에서는 쿠키를 재전송하고 로그인된 클라이언트임을 서버가 알수 있다.

웹 서비스에 따라서는 사이트간 요청 위조 CSRF 대책으로 랜덤 키를 보내는 경우도 있으므로 랜덤 키도 전송한다.

​                          

### 2.6.3 서명된 쿠키를 이용한 세션 데이터 저장

쿠키는 스토리지로서 사용할 수 있다.

루비온 레일즈의 기본 세션 스토리지는 쿠키를 이용해 데이터를 저장한다. 장고도 지원한다. 이 시스템에서는 변조되지 않도록 클라이언트에 전자 서명된 데이터를 보낸다. 클라이언트가 서버로 쿠키를 재전송하면 서버는 서명을 확인한다. 서명하는 것도, 서명을 확인하는 것도 서버에서 하므로 클라이언트는 열쇠를 갖지 않는다. 공개 키와 비밀 키 모두 서버에 있다.

이 시스템의 장점은 서버 측에서 데이터 저장 시스템을 준비할 필요가 없다는 점이다. 서버를 상세하게 기능 단위로 나누는 마이크로 서비스라도 세션 스토리지 암호화 방식을 공통화해두면 따로 데이터 스토어를 세우지 않고 세션 데이터를 읽고 쓸 수 있게 된다.

클라이언트 입장에서 보면 서버에 액세스해서 조작한 결과가 쿠키로 저장된다. 크키를 갖고 있는 한 임시 데이터가 유지된다. 다만 전통적인 Memcached, 관계형 DB를 이용하면 세션 스토리와 달리 같은 사용자라도 스마트폰과 컴퓨터로 각각 접속한 경우 데이터가 공유되지 않는다.

​                          

## 2.7 프록시

프록시는 HTTP 등의 통신을 중계한다. 중계 뿐만 아니라 각종 부가 기능을 구현한 경우도 있다. 프록시에 캐시 기능을 붙여놓으면 웹서버의 부담은 줄이고 각 사용자가 페이지를 빠르게 열람할 수 있게 하는 효과가 있다. 또한 네트워크 방화벽 역할도 한다. 저속 통신 회선용 데이터를 압축해 속도를 높이는 필터, 콘텐츠 필터링 등에도 이용된다.

프록시 구조는 단순해서 GET 등의 메서드 다음에 오는 경로명 형식만 바뀐다. 메서드 뒤의 경로명은 보통 `/helloworld` 처럼 슬래시로 시작되는 유닉스 형식의 경로명이 되지만, 프록시를 설정하면 스키마도 추가되어 `http://` , `https://` 로 시작되는 URL 형식이 된다. HTTP/1.1 부터 등장한 Host 헤더도 최종적으로 요청을 받는 서버명 그대로이다. 실제로 요청을 보내는 곳은 프록시 서버가 된다.

이 테스트 서버는 프록시용 통신도 그 자리에서 응답하지만, 원래는 중계할 곳으로 요청을 리다이렉트하고 결과를 클라이언트에 반환한다.

```
GET /helloworld
Host: localhost:18888
GET http://example.com/helloworld
Host: example.com
```

프록시 서버가 악용되지 않도록 인증을 이용해 보호하는 경우가 있다. 이런 경우는 Proxy-Authenticate 헤더에 인증 방식에 맞는 값이 들어간다. 중계되는 프록시는 중간의 호스트 IP 주소를 특정 헤더에 기록해 간다. 예전부터 사용한 것은 X-Forwarded-For 헤더이다. 그 이후 2014년에 RFC7239로 표준화 되면서 Forwarded 헤더가 도입되었다.

```
X-Forwarded-For: client, proxy1, proxy2
```

프록시를 설정하려면 `-x/--proxy` 옵션을 사용한다. 프록시 인증용 유저명과 패스워드는 `-U/--proxy-user` 옵션을 이용한다.

```shell
$ curl --http1.0 -x http://locahost:18888 -U user:pass http://example.com/helloworld
```

`--proxy-basic, --proxy-digest` 등의 옵션으로 프록시 인증 방식을 변경할 수도 있다. 

프록시와 비슷한 것으로는 게이트웨이가 있다. HTTP/1.0  에서는 다음과 같이 정의되어 있다.

- 프록시 : 통신 내용을 이해한다. 필요에 따라서 콘텐츠를 수정하거나 서버 대신 응답한다.
- 게이트웨이 : 통신 내용을 그대로 전송한다. 내용의 수정도 불허한다. 클라이언트에서는 중간에 존재하는 것을 알아채서는 안된다.

HTTPS 통신의 프록시 지원은 HTTP/1.1에서 추가된 CONNECT 메서드를 이용한다. 

​                          

## 2.8 캐시

콘텐츠가 변경되지 않았을 때 로컬에 저장된 파일을 재사용해서 다운로드 횟수를 줄이고 성능을 높이는 캐시 메커니즘이 등장했다. GET, HEAD 메서드 이외는 기본적으로 캐시되지 않는다.

​                          

### 2.8.1 갱신 일자에 따른 캐시

HTTP/1.0에서의 캐시를 설명한다. 당시는 정적 콘텐츠 위주라서 콘텐츠가 갱신되었다. 웹서버는 대개 다음과 같은 헤더를 응답에 포함한다. 날짜는 RFC 1123 형식으로 기술되고 타임 존에는 GMT를 설정한다.

```
Last-Modified: Wed, 08 Jun 2016 15:23:45 GMT
```

웹 브라우저가 캐시된 URL을 다시 읽을 때는 서버에서 반환된 일시를 그대로 If-Modified-Since 헤더에 넣어 요청한다.

```
If-Modified-Since: Wed, 08 Jun 2016 15:23:45 GMT
```

웹 서버는 요청에 포함된 If-Modified-Since의 일시와 서버의 콘텐츠의 일시를 비교한다. 변경되었으면 200 OK를 반환하고 콘텐츠를 응답 바디에 실어서 보낸다. 변경되지 않았으면, 304 Not Modified를 반환하고 바디를 응답에 포함하지 않는다.

​                          

### 2.8.2 Expires

Expires 헤더에는 날짜와 시간이 들어간다. 클라이언트는 지정한 기한 이내라면 강제로 캐시를 이용한다. 즉 요청을 아예 하지 않는다는 것이다.

```
Expires: Fri, 05 Jun 2016 00:23:45 GMT
```

참고로 '3초 후 콘텐츠 유효 기간이 끝난다'라고 설정했어도 3초 후에 마음대로 리로드 하지 않는다. 여기에 설정된 날짜와 시간은 어디까지나 접속을 할지 말지 판단할 때만 사용한다. 또한 뒤로가기 버튼 등으로 방문 이력을 조작하는 경우는 기한이 지난 오래된 콘텐츠가 그대로 이용될 수 있다.

Expires를 사용하면 서버에 변경 사항이 있는지 묻지 않게 되므로 SNS 톱페이지 등에 사용할 때는 주의해야 한다. 지정한 기간 이내의 변경 사항은 모두 무시되어 새로운 콘텐츠를 전혀 볼 수 없기 때문이다. 스타일시트 등 좀처럼 갱신되지 않는 정적 콘텐츠에 사용하는 것이 바람직하다. 

HTTP/1.1인 RFC2068에서는 변경할 일이 없는 콘텐츠라도 최대 1년의 캐시 수명을 설정하자는 가이드라인이 추가되었다.

​                          

### 2.8.3 Pragma:no-cache

클라이언트가 프록시 서버에 지시할수 있다. 지시를 포함한 요청 헤더가 들어갈 곳으로서 HTTP/1.0부터 Pragma 헤더가 정의되어 있다.  Pragma 헤더에 포함할 수 있는 페이로드로 유일하게 HTTP 사양으로 정의된 것이 no-cache이다.

no-cache는 '요청한 콘텐츠가 이미 저장되어 ㅣㅇㅆ어도, 원래 서버에서 가져오라' 고 프록시 서버에 지시하는 것이다. no-cache는 hTTP/1.1에서 Cache-Control에 통합되었지만 하위 호환성을 위해 남아 있다. 클라이언트가 정보의 수명과 품질을 일일이 관리하는 상태는 부자연 스럽다.

게다가 프록시가 기대한 대로 동작할지 보증할수가 없다. 

HTTP/2가 등장한 이후로는 보안 접속 비율이 증가 했다. 보안 통신에서는 프록시가 통신 내용을 감시할 수 없고 중계만 할수 있다. 프록시의 캐시를 외부에서 적극적으로 관리하는 의미가 이제 없다고 할수 있다.

​                          

### 2.8.4 ETag 추가

날짜와 시간을 이용한 캐시 비교만으로 해결할 수 없을 때도 있다. 그럴 때 사용할수 있는 것이 RFC 2068의 HTTp/1.1에서 추가된 ETag (entity tag)이다. ETag는 순차적인 갱신 일시가 아니라 파일의 해시 값으로 비교한다. 일시를 이용해 확인할 때처럼 서버는 응답에 ETag 헤더를 부여한다. 두번째 이후 다운로드 시 클라이언트는 If-None-Match 헤더에 다운로드된 캐시에 들어있던 ETag 값을 추가해 요청한다. 서버는 보내려는 파일의 ETag와 비교해서 같으면 304 Not Modified로 응답한다. 여기까지는 HTTP/1.0에도 있었던 캐시 제어 구조이다.

ETag는 서버가 자유롭게 결정해서 반환할 수 있다. 일시 외의 갱신 정보를 고려한 해시 값을 서버가 생성할 수 있다. ETag는 갱신 일시와 선택적으로 사용할 수 있다. 

​                          

### 2.8.5 Cache-Control (1)

서버는 Cache-Control 헤더로 더 유연한 캐시 제어를 지시할 수 있다. Expires보다 우선해서 처리된다. 먼저 서버가 응답으로 보내는 헤더를 소개한다.

- public : 같은 컴퓨터를 사용하는 복수의 사용자간 캐시 재사용을 허가한다.
- private : 같은 컴퓨터를 사용하는 다른 사용자간 캐시를 재사용하지 않는다. 같은 URL에서 사용자마다 다른 콘텐츠가 돌아오는 경우에 이용한다.
- max-age=n : 캐시의 신선도를 초단위로 설정. 그 이후는 서버에 문의한뒤 304 Not Modified가 반환되었을 때만 캐시를 이용한다.
- s-maxage=n : max-age와 같으니 공유 캐시에 대한 설정값이다.
- no-cache : 캐시가 유효한지 매번 문의한다. max-age=0과 거의 같다.
- no-store : 캐시하지 않는다.

no-cache는 Pragma: no-cache와 똑같이 캐시하지 않는 것은 아니고, 시간을 보고 서버에 접속하지 않은 채 콘텐츠를 재이용하는 것을 그만둘 뿐이다. 갱신 일자와 ETag를 사용하며, 서버가 304를 반환했을 때 이용하는 캐시는 유효하다. 캐시하지 않는 것은 no-store이다.

캐시와 개인 정보 보호 관계도 주의해야 한다. Cache-Control은 리로드를 억제하는 시스템이고, 개인 정보 보호 목적으로 사용할 수 없다. private는 같은 URL이 유저마다 다른 결과를 줄 경우에 이상한 결과가 되지 않도록 지시하는 것이다. 보안 접속이 아니면 통신 경오에는 내용이 보인다.  no-store도 마찬가지이다. 

콤마로 구분해 복수 지정이 가능하다.

- private, public 중하나, 혹은 설정하지 않는다. (기본은 private)
- max-age, s-maxage, no-cache, no-store 중 하나

> 캐시에 관한 포괄적인 설명
>
> https://web.dev/http-cache/

​                          

### 2.8.6 Cache-Control (2)

Cache-Control 헤더를 요청 헤더에 포함해서 프록시에 지시할 수 있다. 

클라이언트 측에서 요청 헤더에서 사용할 수 있는 설정값

- no-cache : `Pragma: no-cache` 와 같다.
- no-store : 응답의 no-store와 같고 프록시 서버에 캐시를 삭제하도록 요청한다.
- max-age : 프록시에서 저장되 냌시가 최초로 저장되고 나서 지정 시간 이상 캐시는 사용하지 않도록 프록시에 요청한다.
- max-stale : 지정한 시간만큼 유지 기간이 지났어도 클라이언트는 지정한 시간 동안은 저장된 캐시를 재사용하라고 프록시에 요청한다. 연장 시간은 생략할 수 있고, 그런 경우 영원히 유요하다는 의미가 된다.
- min-fresh : 캐시의 수명이 지정된 시간 이상 남아 있을 때, 캐시를 보내도 좋다고 프록시에 요청한다. 즉 적어도 지정된 시간만큼은 신선해야 한다. 
- no-transform : 프록시가 콘텐츠를 변형하지 않도록 프록시에 요청한다.
- only-if-cached : 캐시된 경우에만 응답을 반환하고, 캐시된 콘텐츠가 없을 땐 504 Gateway Timeout 오류 메세지를 반환하도록 프록시에 요청한다. 이 헤더가 설정되면 처음을 제외하고 오리진 서버에 전혀 액세스하지 않는다.



응답 헤더에서 서버가 프록시에 보내는 캐시 컨트롤 지시

- no-transform : 프록시가 콘텐츠를 변경하는 것을 제어한다.
- must-revalidate : no-cache와 비슷하지만 프록시 서버에 보내는 지시가 된다. 프록시 서버가 서버에 문의했을 때 서버의 응답이 없으면, 프록시 서버가 클라이언트에 504 Gateway Timeout이 반환되기를 기대한다.
- proxy-revalidate : must-revalidate와 같지만, 공유 캐시에만 요청한다.

​                          

### 2.8.7 Vary

같은 URL이라도 클라이언트에 따라 반환 결과가 다름을 나타내는 헤더가 Vary이다.

예를 들어, 사용자의 브라우저가 스마트폰용 브라우저일 때는 모바일용 페이지가 표시되고, 사용하는 언어에 따라 내용이 바뀌는 경우를 들 수 있다. 이처럼 표시가 바뀌는 이유에 해당하는 헤더명을 Vary에 나열해서 잘못된 콘텐츠의 캐시로 사용되지 않게 한다. 로그인이 필요하다면 쿠키도 지시할 것이다.

```
Vary: User-Agent, Accept-Language
```

Vary 헤더는 검색 엔진용 힌트로도 사용된다. 브라우저별, 언어별로 바르게 인덱스를 만드는 힌트도 된다. 

모바일 브라우저인지 판정하는 방법으로는 User-Agent가 있는데 유저 에이전트 이름은 관례적인 것이지 정규화된 정보가 아니다. 틀릴수도 있다. 

​                          

## 2.9 리퍼러

리퍼러(Referer)는 사용자가 어느 경로로 웹사이트에 도달했는지 서버가 파악할 수 있도록 클라이언트가 서버에 보내는 헤더이다. RFC1945에 오자로 등록되었다고 한다.

클라이언트가 `http://www.example.com/link.html` 을 클릭해서 다른 사이트로 이동할 때, 링크가 있는 페이지의 URL을 목적지 사이트의 서버에 아래와 같은 형식으로 전송한다. 웹페이지가 이미지나 스크립트를 가져올 경우는 리소스를 요청할 때 리소스를 이용하는 html 파일의 URL이 리퍼러로서 전송된다.

```
Referer: http://www.example.com/link.html
```

만약 북마크에서 선택하거나 주소창에서 키보드로 직접 입력했을 때에는 Referer 태그를 전송하지 않거나 `Referer:about:blank ` 를 전송한다. 웹 서비스는 리퍼러 정보를 수집해서 어떤 페이지가 자신의 서비스에 링크를 걸었는지도 알 수 있다.

리퍼러를 보내지 않도록 웹 브라우저를 설정할수도 있다.

방어적인 용도로 이미지 다운로드시 리퍼러를 설정되지 않은 요청은 거절하는 경우도 있는데, 위와 같이 설정되어 있다면 정상적으로 동작하지 않게 되니 이런식으로 보안대책을 세우지는 않을 것이다.

클라이언트가 리퍼러 전송을 제한하는 규약이 RFC 2616으로 제정되었다. 액세스 출발지 및 액세스 목적지의 스키마 조합과 리퍼러 전송 유무 관계는 다음 표와 같다.

| 액세스 출발지 | 액세스 목적지 | 전송하는가?  |
| ------------- | ------------- | ------------ |
| HTTPS         | HTTPS         | 한다.        |
| HTTPS         | HTTP          | 하지 않는다. |
| HTTP          | HTTPS         | 한다.        |
| HTTP          | HTTP          | 한다.        |

하지만 이 규칙을 엄격히 적용하면 서비스 간 연계에 차질이 생길수 있어 2017년에는 대기 상태였다. 리퍼러 정책은 다음 중 한 가지 방법으로 설정할 수 있다. (철자 오류가 수정된 점에 주의)

- Referrer-Policy 헤더
- `<meta name="referrer" content="설정값">`
- `<a>` 태그 등 몇 가지 요소의 referrerpolicy 속성 및 `rel="noreferrer"` 속성

리퍼러 정책으로서 설정할 수 있는 값에는 다음과 같은 것이 있다.

- no-referrer : 전혀 보내지 않는다.
- no-referrer-when-downgrade : 현재 기본 동작과 마찬가지로 HTTPS->HTTP일 때는 전송하지 않는다.
- same-origin : 동일 도메인 내의 링크에 대해서만 리퍼러를 전송한다.
- origin : 상제 페이지가 아니라 톱페이지에서 링크된 것으로 해 도메인 이름만 전송한다.
- strict-origin : origin과 같지만 HTTPS->HTTP일 때는 전송하지 않는다.
- origin-when-crossorigin : 같은 도메인 내에서는 완전 리퍼러를, 다른 도메인에는 도메인 이름만 전송한다.

- strict-origin-when-crossorigin : origin-when-crossorigin과 같지만 HTTPS->HTTP일 때는 송신하지 않는다.
- unsafe-url : 항상 전송한다.

이 밖에도 아래와 같이 Content-Security-Policy 헤더로 지정할 수 있다.

```
 Content-Security-Policy : referrer origin
```

​                          

## 2.10 검색 엔진용 콘텐츠 접근 제어

검색엔진에서 정보를 수집하는 크롤러의 접근을 제어하는 방법에는 robots.txt와 사이트맵이 있다.

​                     

### 2.10.1 robots.txt

robots.txt는 서버 콘텐츠 제공자가 크롤러에 접근 허가 여부를 전하기 위한 프로토콜이다. 사양 이름으로는 'Robots Exclusion Standard'이다. robots.txt는 일종의 신사협정이라 할수 있으며 검색 엔진의 크롤러가 이 텍스트를 해석할 수 있다.

```
User-agent: *
Disallow: /cgi-bin/
Disallow: /tmp/
```

모든 크롤러에 대해 cgi-bin 폴더와 tmp 폴더 접근을 금지 했다. User-agent에 구글 봇처럼 개별적으로 지정할 수도 있다.

robots.txt와 비슷한 내용을 html 메타태그로도 기술할 수 있다.

```
<meta name="robots" content="noindex" />
```

대표적인 디렉티브로는 아래가 있다. [[더 자세한 사항 참고]](https://developers.google.com/search/reference/robots_meta_tag)

| 디렉티브  | 의미                                                |
| --------- | --------------------------------------------------- |
| noindex   | 검색 엔진이 인덱스하는 것을 거부한다.               |
| nofollow  | 크롤러가 페이지 내의 링크를 따라가는 것을 거부한다. |
| noarchive | 페이지 내 콘텐츠를 캐시하는 것을 거부한다.          |

```
// X-Robots-Tag 헤더에도 사용 가능하다.
X-Robots-Tag: noindex, nofollow
```



### 2.10.2 robots.txt와 재판결과

웹 서비스 제공자와 크롤러 제작자 사이에 robots.txt를 설치하면 웹 서비스 제공자가 의사표현을 한것이므로 이를 지켜야 한다. 



### 2.10.3 사이트맵

사이트맵은 웹사이트에 포함된 페이지 목록과 메타데이터를 제공하는 XML 파일이다. robots.txt가 블랙리스트라면, 사이트맵은 화이트리스트처럼 사용된다. 웹사이트가 동적 페이지라 크롤러가 페이지를 찾을수 없는 경우라도 사이트맵으로 보완할 수 있다. [[사이트맵 사양]](https://www.sitemaps.org/ko/protocol.html)

url태그를 등록하고 싶은 페이지 수만큼 작성한다. loc는 절대 URL이다. 

```xml
// 예시 XML 형식으로 기술한다.

<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
   <url>
      <loc>http://www.example.com/</loc>
      <lastmod>2005-01-01</lastmod>
      <changefreq>monthly</changefreq>
      <priority>0.8</priority>
   </url>
   <url>
      <loc>http://www.example.com/catalog?item=12&amp;desc=vacation_hawaii</loc>
      <changefreq>weekly</changefreq>
   </url>
   <url>
      <loc>http://www.example.com/catalog?item=73&amp;desc=vacation_new_zealand</loc>
      <lastmod>2004-12-23</lastmod>
      <changefreq>weekly</changefreq>
   </url>
   <url>
      <loc>http://www.example.com/catalog?item=74&amp;desc=vacation_newfoundland</loc>
      <lastmod>2004-12-23T18:00:15+00:00</lastmod>
      <priority>0.3</priority>
   </url>
   <url>
      <loc>http://www.example.com/catalog?item=83&amp;desc=vacation_usa</loc>
      <lastmod>2004-11-23</lastmod>
   </url>
</urlset>

```

사이트맵은 robots.txt에 사용할 수 있다. 또한 각 검색 엔진에 XML 파일을 업로드하는 방법이 있다. 구글의 경우 [[서치 콘솔 사이트맵 툴]](https://search.google.com/search-console/not-verified?original_url=/search-console/sitemaps&original_resource_id)을 사용한다.

```
//robots.txt
Sitemap: http://www.example.org/sitemap.xml
```

구글의 경우 사이트맵을 사용해 웹사이트의 메타데이터를 검색 엔진에 전달할 수 있다.

- 웹사이트에 포함되는 이미지의 경로, 설명, 라이선스, 물리적인 위치
- 웹사이트에 포함되는 비디오 섬네일, 타이틀, 재생 시간, 연령 적합성 등급이나 재생 수
- 웹사이트에 포함되는 뉴스의 타이틀, 공개일, 카테고리, 뉴스에서 다루는 기업의 증권 코드

그외 자세한 내용은 [[서치 콘솔 도움말 참조]](https://support.google.com/webmasters#topic=9128571)



