# Chapter3 HTTP/1.0 클라이언트 구현

## 3.3 이 장에서 다룰 레시피

| 레시피                          | 메서드        | Go 언어 API              |
| ------------------------------- | ------------- | ------------------------ |
| GET을 이용한 정보 획득          | GET           | http.Get                 |
| 쿼리가 있는 정보 획득           | GET           | http.Get                 |
| HEAD를 이용한 헤더 획득         | HEAD          | http.Head                |
| x-www-form-urlencoded로 폼 전송 | POST          | http.PostForm            |
| POST로 파일 전송                | POST          | http.Post                |
| multipart/form-data로 파일 전송 | POST          | http.PostForm            |
| 쿠키 송수신                     | GET/HEAD/POST | http.Client              |
| 프록시                          | GET/HEAD/POST | http.Client              |
| 파일 시스템 접근                | GET/HEAD/POST | http.Client              |
| 자유로운 메서드 전송            | 아무거나      | http.Request/http.Client |
| 헤더 전송                       | 아무거나      | http.Request/http.Client |



## 3.4 GET 메서드 송신 + 쿼리 전송, 바디, 상태코드, 헤더 수신, 

```go
package main

import (
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
)

func main() {
    // 쿼리 문자열을 만든다
	values := url.Values{
		"query": {"hello world"},
	}

    // values.Encode()를 호출해 문자열로 만든다
    // RFC와 호환성이 높은 변환 방식으로 쿼리 문자열을 만든다
	resp, err := http.Get("http://localhost:18888" + "?" + values.Encode())

	if err != nil {
		panic(err)
	}
	defer resp.Body.Close() // 후처리 코드 일종의 final

	body, err := ioutil.ReadAll(resp.Body) // 바디의 내용을 바이트열로 읽어옴

	if err != nil {
		panic(err)
	}

	log.Println(string(body)) // 바이트열을 UTF-9 문자열로 변환, 화면 출력

	// 응답 헤더 목록을 출력한다
	log.Println("Headers:", resp.Header)

	// 문자열로 200 ok
	log.Println("Status:", resp.Status)

	// 수치로 200
	log.Println("StatusCode:", resp.StatusCode)
}

```



## 3.6 HEAD 메서드로 헤더 가져오기

```go
package main

import (
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
)

func main() {
    // HEAD는 바디를 가져올 수 없다. 길이가 0인 바이트 배열이 반환
	resp, err := http.Head("http://localhost:18888")

	if err != nil {
		panic(err)
	}
    
    // 문자열로 200 ok
	log.Println("Status:", resp.Status)
	// 응답 헤더 목록을 출력한다
	log.Println("Headers:", resp.Header)
}

```



## 3.7 x-www-form-urlencoded 형식의 POST 메서드 전송

```go
package main

import (
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
)

func main() {
    // 쿼리 문자열을 만든다
	values := url.Values{
		"test": {"value!!!"},
	}
    
    // url.Values.Encode()호출하지 않고 PostForm함수에 전달한다.
    // 이럴 경우 RFC 3986에 따라 변환한다.
	resp, err := http.PostForm("http://localhost:18888", values)

	if err != nil {
		panic(err)
	}
    
	log.Println("Status:", resp.Status)
}

```



## 3.8 POST 메서드로 임의의 바디 전송

POST 메서드를 사용하면 임의의 콘텐츠를 바디에 넣어 보낼수 있다. HTTP/1.0 브라우저로 보낼수는 없었지만, HTTP/1.1 이후에 등장한 XMLHttpRequest를 이용해서 실현할 수 있다.

```shell
// 파일에서 읽어 들인 임의의 콘텐츠를 전송
$ curl -T main.go -H "Content-Type: text/plain" http://localhost:18888
```

```go
package main

import (
	"io"
	"log"
	"net/http"
	"os"
	"strings"
)

// POSTData returns void
func POSTData(data io.Reader) {
	resp, err := http.Post("http://localhost:18888", "text/plain", data)
    
	if err != nil { // 전송 실패
		panic(err)
	}
    
	log.Println("Status:", resp.Status)
}

func main() {

	file, err := os.Open("test123.go")
	if err != nil {
		panic(err)
	}
	POSTData(file)

	reader := strings.NewReader("텍스트")
	POSTData(reader)

}

```



## 3.9 multipart/form-data 형식으로 파일 전송

```shell
$ curl -F "name=Michael Jackson" -F "thumbnail=@photo.jpg" http://localhost:18888
```

```go
package main

import (
	"bytes"
	"io"
	"log"
	"mime/multipart"
	"net/http"
	"os"
)

func main() {
	var buffer bytes.Buffer                      // 멀티파트부 조립후 바이트열 저장할 버퍼를 선언
	writer := multipart.NewWriter(&buffer)       // 멀티파트를 조립할 작성자를 생서
	writer.WriteField("name", "Michael Jackson") //파일 이외의 필드는 WriteField 메서드로 등록

	// 파일을 읽는 조작
	fileWriter, err := writer.CreateFormFile("thumbnail", "photo.jpg")

	if err != nil {
		panic(err)
	}

	readFile, err := os.Open("photo.jpg") // 파일을 연다

	if err != nil {
		panic(err)
	}

	defer readFile.Close()
	io.Copy(fileWriter, readFile) // 모든 컨텐츠를 io.Writer에 복사
	writer.Close()                // 멀티파트의 io.Writer를닫고 버퍼에 모두 출력

	resp, err := http.Post(
        "http://localhost:18888", writer.FormDataContentType(), &buffer)

	if err != nil {
		panic(err)
	}

	log.Println("Status:", resp.Status)
}

```

처음에 io.Reader인 mytes.Buffer에 멀티파트 폼이 전송할 콘텐츠를 작성하고 있다.  이 콘텐츠를 만드는 데 사용하는 것이 multipart.Writer 오브젝트이다. 이 오브젝트를 통해 폼의 항목이나 파일을 써넣으면 multipart.newWriter()의 인수로 전달한 bytes.Buffer에 기록된다. 이 bytes.Buffer는 io.Reader인 동시에 io.Writer이기도 하다.

Content-Type에는 경계 문자열을 넣어야 한다. 경계 문자열은 multipart.Writer 오브젝트가 내부에서 난수로 생성한다. Boundary() 메서드로 취득할수 있으므로 다음과 같이 써어 Content-Type을 만들어 낼 수 있다. FormDataContentType() 메서드는 이 코드의 지름길이다.

```
"multipart/form-data; boundary=" + writer.Boundary()
```



### 3.9.1 전송할 파일에 임의의 MIME 타입을 설정한다

앞의 코드는 각 파일의 Content-Type이 사실상 void 형이라 할 수 있는 application/octet-stream 형이 된다. 이 때 textproto.MIMEHeader로 임의의 MIME 타입을 설정할 수 있다. 

```go
// 전송할 파일에 임의의 MIME 타입 설정하기
import (
	"net/textproto"
)

part := make(textproto.MIMEHeader)
part.Set("Content-Type", "image/jpeg")
part.Set("Content-Dispoistion", `form-data; name="thumbnail"; filename="photo.jpg"`)
fileWriter, err := writer.CreatePart(part)

if err != nil {
    panic(err)
}

readFile, err := os.Open("photo.jpg")

if err != nil {
    panic(err)
}

io.Copy(fileWriter, readFile)

```



`multipart.Writer.WriterField(), multipart.Writer.CreateFormFile()` 메서드는 멀티파트의 구성 요소인 파트 자체를 건드리지 않고도 콘텐츠를 작성할 수 있는 API이다. 각 파트의 헤더도 자동으로 설정되었다. 위의 코드는 각 메서드 안에서 이루어지는 세부 처리를 밖으로 꺼내 임의의 Content-Type을 지정할 수 있게 했다.



## 3.10 쿠키 송수신

쿠키는 브라우저 내부에서 상태를 유지해야 한다.

```go
package main

import (
	"log"
	"net/http"
	"net/http/cookiejar"
	"net/http/httputil"
)

func main() {
	jar, err := cookiejar.New(nil) // 쿠키를 저장할 cookiejar 인스턴스를 생성
	if err != nil {
		panic(err)
	}
	client := http.Client{ // 쿠키를 저장할 수 있는 http.Client 인스턴스를 만든다
		Jar: jar,
	}
	// 쿠키는 첫번째 액세스에서 쿠키를 받고, 두번째 이후의 액세스에서 쿠키를
	// 서버에 보내는 구조이므로 두번 액세스 한다
	for i := 0; i < 2; i++ {
		// http.Get 대신 작성한 클라이언트의 Get 메서드로 액세스한다.
		resp, err := client.Get("http://localhost:18888/cookie")
		if err != nil {
			panic(err)
		}
		dump, err := httputil.DumpResponse(resp, true)
		if err != nil {
			panic(err)
		}
		log.Println(string(dump))
	}
}

```



Go 언어의 http.Client는 http.CookieJar 인터페이스를 구현하는임의의 오브젝트를 쿠키 처리의 백엔드로서 사용할 수 있다. 기본적으로 메모리에만 저장하므로 재시작시 사라진다. 파일로 저장하는 기능은 서드파티 구현, 직접 구현을 해야 한다. 

아래코드처럼 기본 클라이언트를 변경해 쿠키를 유효화할 수 있다. 물론 그 프로세스 내 net/http 패키지의 동작에 영향을 주므로, 일괄 처리로 바로 끝나는 프로그램으로 제한하는 등 영향 범위에 주의하면서 사용해야 한다.

```go
// 쿠키 송수신을 위해 기본 클라이언트를 치환한다.
http.DefaultClient = &http.Client {
    Jar: jar,
}

resp, err := http.Get("http://localhost:18888/cookie")
```



## 3.11 프록시 이용

```shell
$ curl -x http://localhost:18888 http://github.com
```

프록시도 http.Client를 이용한다. 쿠키의 경우 Jar 멤버 변수에 설정해서 유효해졌지만 이번에 사용할 것은 Transport이다. Transport는 실제 통신을 하는 백엔드이다.

```go
package main

import (
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
)

func main() {
	proxyUrl, err := url.Parse("http://localhost:18888")
	if err != nil {
		panic(err)
	}
	client := http.Client{
		Transport: &http.Transport{
			Proxy: http.ProxyURL(proxyUrl),
		},
	}

	resp, err := client.Get("http://github.com")
	if err != nil {
		panic(err)
	}

	dump, err := httputil.DumpResponse(resp, true)
	if err != nil {
		panic(err)
	}
	log.Println(string(dump))
}

```

 client.Get의 대상은 외부 사이트이다. 하지만 프록시의 방향은 로컬 테스트 서버이다. 이 코드를 실행하면 외부로 직접 요청을 날리지 않고, 로컬 서버가 일단 요청을 받는다. 로컬 서버가 직접 응답을 반환하므로 `github.com`에 대한 액세스가 일어나지 않는다. 

위 코드 속의 프록시 URL을 아래와 같이 바꾸면, BASIC 인증에서 사용자 이름과 패스워드를 지정할 수 있다.

```
http://유저명:패스워드@github.com
```

http.Client에서 사용되는 프록시 파라미터는 환경 변수에서 정보를 가져와 프록시를 설정하는 처리로 되어 있으므로, 다양한 프로그램에서 함께 사용하는 환경 변수 HTTP_PROXY, HTTPS_PROXY로 설정되어 있을 때는 그곳에 설정한 프록시에 요청을 보낸다. NO_PROXY에 설정을 무시할 호스트 이름을 적어두면, 그 호스트와는 프록시를 거치지 않고 직접 통신할 수 있다.

```go
// 쿠키와 마찬가지로 아래 코드를 통해서 http 패키지의 DefaultTransport에 설정하면, http.Get등 글로벌 함수에서도 프록시가 사용된다.
http.DefaultTransport = &http.Transport{
    Proxy: http.ProxyURL(proxyUrl),
}
```



## 3.12 파일 시스템 액세스

HTTP/1.0을 다룬 1장에서는 URL의 구조로서 스키마를 소개했다. 지금까지는 http, https 스키마에 대한 액세스를 소개했다. file 스키마는 로컬 파일에 액세스할 때 사용하는 스키마이다. curl에서는 다음 명령을 실행하면 작업 폴더 내 해당 파일 내용을 콘솔에 출력할 수 있다.

```shell
$ curl file://main.go
```

통신 백엔드 http.Transport에는 이 밖의 스키마용 트랜스포트를 추가하는 RegisterProtocol 메서드가 있다. 이 메서드에 등록할 수 있는 파일 액세스용 백엔드 http.NewFileTransport()도 있다. 이 메서드를 사용해서 로컬 파일에서 액세스할 수 있게 된다. 위 사례와는 UR 표기법이 조금 다르지만, 로컬 파일의 내용이 응답 바디에 담겨서 돌아온다.

```go
package main

import (
	"log"
	"net/http"
	"net/http/httputil"
)

func main() {
	transport := &http.Transport{}
	transport.RegisterProtocol("file", http.NewFileTransport(http.Dir(".")))
	client := http.Client{
		Transport: transport,
	}
	resp, err := client.Get("file://./test123.go")
	if err != nil {
		panic(err)
	}
	dump, err := httputil.DumpResponse(resp, true)
	if err != nil {
		panic(err)
	}
	log.Println(string(dump))
}
```

```shell
# DELETE를 curl로 전송
$ curl -X DELETE http://localhost:18888
```

DELETE 처리를 Go언어로 구현

```go
package main

import (
	"log"
	"net/http"
	"net/http/httputil"
)

func main() {
	client := &http.Client{}
    
    // http.Request 구조체를 생성
	request, err := http.NewRequest(
        "DELETE", "http://localhost:18888", nil)
	if err != nil {
		panic(err)
	}
	resp, err := client.Do(request)
	if err != nil {
		panic(err)
	}
	dump, err := httputil.DumpResponse(resp, true)
	if err != nil {
		panic(err)
	}
	log.Println(string(dump))
}

```



## 3.14 헤더 전송

curl 커맨드로 헤더를 전송할 때는 -H 옵션을 지정한다.

```shell
$ curl -H "Content-Type=image/jpeg" -d "!@image.jpeg" http://localhost:18888
```

임의의 메서드를 전송할 때 사용한 http.Request 구조체에는 Header라는 필드가 있다. 이는 http.Response 구조체의 Header 필드와 같다. GET 메서드 예제에서는 Get() 메서드로 헤더 값을 가져왔다. 헤더를 추가할 때는 Add() 메서드를 사용한다.

```
// 헤더 추가
request.Header.Add("Content-Type", "imagejpeg")
```

curl에는 특정헤더를 사용하는데 편리하도록 준비한 옵션이 있다. BASICd 인증용 `--basic -u 유저명:패스워드` 옵션이나 쿠키용 `-c 파일` 옵션 등이 그것이다. Go 언어에서도 아래와 같이 똑같은 헬퍼 메서드가 http.Request 구조체이 메서드로서 제공된다.

```go
// BASIC 인증
request.SetBasicAuth("유저명", "패스워드")
// 쿠키를 수동으로 하나 추가한다
request.AddCookie(&http.Cookie{Name: "test", Value: "value"})
```

쿠키는 헤더에 설정하지 않아도 http.Client의 Jar에 Cookie.Jar의 인스턴스를 설정해 송수신하게 된다. 수동으로 헤더에 설정하면 받지 않은 쿠키도 자유롭게 전송할 수 있다.



## 3.15 국제화 도메인

go 에서의 국제화 변환 처리는 idna.ToASCII(), idna.ToUnicode() 함수로 실행한다. 요청을 보내기전에 도메인 이름을 idna.ToASCII로 변환해서 API로 한글 도메인의 사이트 정보를 가져올 수 있게 된다.

```go
package main

import (
	"fmt"

	"golang.org/x/net/idna"
)

func main() {
	src := "악력왕"
	ascii, err := idna.ToASCII(src)
	if err != nil {
		panic(err)
	}
	fmt.Printf("%s -> %s\n", src, ascii)

	// 라이브러리 오류시
	// go get -u golang.org/x/net/idna
}
```

