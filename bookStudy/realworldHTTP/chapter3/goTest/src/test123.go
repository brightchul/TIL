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
