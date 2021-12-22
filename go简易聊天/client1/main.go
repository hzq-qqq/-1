package main

import (
	"fmt"
	"io"
	"log"
	"net"
	"os"
)

func main() {
	conn, err := net.Dial("tcp", "localhost:8000")
	if err != nil {
		log.Fatal("connect failed err = ", err)
		return
	}
	defer conn.Close()
	go reveive(conn, os.Stdout)
	io.Copy(conn, os.Stdin)
}

func reveive(conn net.Conn, stdout *os.File) {
	for true {
		bt := make([]byte, 100)
		_, err := conn.Read(bt)
		if err != nil {
			fmt.Println("断开连接")
			break
		} else {
			fmt.Println(string(bt))
		}
	}
}
