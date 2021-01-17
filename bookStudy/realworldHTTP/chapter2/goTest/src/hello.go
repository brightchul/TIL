package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"

	"github.com/k0kubun/pp"
)

// PP라이브러리는 서드파티 라이브러리로 go get 명령어로 가져올수 있다.

func main() {
	var httpServer http.Server
	http.HandleFunc("/", handler)
	http.HandleFunc("/digest", handlerDigest)
	log.Println("start http listening: 18888")
	httpServer.Addr = ":18888"
	log.Println(httpServer.ListenAndServe())
}

func handler(w http.ResponseWriter, r *http.Request) {

	w.Header().Add("Set-Cookie", "VISIT=TRUE")

	if _, ok := r.Header["Cookie"]; ok {
		// 쿠키가 있다는 것은 한 번 다녀간 적이 있는 사람
		fmt.Fprintf(w, "<html><body>두 번째 이후</body></html>\n")
	} else {
		fmt.Fprintf(w, "<html><body>첫방문</body></html>\n")
	}
}

func handlerDigest(w http.ResponseWriter, r *http.Request) {

	pp.Printf("URL: %s\n", r.URL.String())
	pp.Printf("Query: %v\n", r.URL.Query())
	pp.Printf("Proto: %s\n", r.Proto)
	pp.Printf("Metho: %s\n", r.Method)
	pp.Printf("Header: %v\n", r.Header)

	defer r.Body.Close()
	body, _ := ioutil.ReadAll(r.Body)
	fmt.Printf("--body--\n%s\n", string(body))

	if _, ok := r.Header["Authorization"]; !ok {
		w.Header().Add("WWW-Authenticate", `Digest realm="Secret Zone", 
		nonce="TgLc25U2BQA=f510a2780463e18e6587be702c2e67fe2b04afd",
		algorithms=MD5, qop="auth"`)
		w.WriteHeader(http.StatusUnauthorized)
	} else {
		fmt.Fprintf(w, "<html><body>secret page</body></html>\n")
	}

}
