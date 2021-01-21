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

	resp, err := http.Post("http://localhost:18888", writer.FormDataContentType(), &buffer)

	if err != nil {
		panic(err)
	}

	log.Println("Status:", resp.Status)
}
