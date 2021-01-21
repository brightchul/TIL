package main

import (
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
)

func main() {
	values := url.Values{
		"query": {"hello world"},
	}

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
