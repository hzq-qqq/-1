package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"newPro/noblockcache/first/memo"
	"strconv"
	"time"
)

func main() {
	memo := memo.New(getRequestBody)
	for _, url := range incomingURLs() {
		go process(memo, url)
	}

}

func process(memo *memo.Memo, url string) {
	str := time.Now()
	_, err := memo.Get(url)
	if err != nil {
		log.Fatal("get failed err = ", err)
		return
	}
	fmt.Println("执行的时间为", time.Since(str))
}

func incomingURLs() []string {
	res := make([]string, 10)
	for i := 0; i < 10; i++ {
		res = append(res, "https://"+strconv.Itoa(i))
	}
	return res
}

func getRequestBody(url string) (interface{}, error) {
	resp, err := http.Get(url)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	return ioutil.ReadAll(resp.Body)
}
