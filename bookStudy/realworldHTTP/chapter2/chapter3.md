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



