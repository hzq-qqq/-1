package main

import (
	"bytes"
	"fmt"
	"math"
	"math/rand"
	"net"
	"strconv"
	"sync"
	"time"
)

var (
	wg sync.WaitGroup
)

func main() {
	wg.Add(2)
	go serverGo()
	time.Sleep(time.Millisecond * 500)
	go clientGo(1)
	wg.Wait()
}

const (
	ServerNet     = "tcp"            //网络协议
	ServerAddress = "127.0.0.1:8888" //socket
	Delimiter     = '\t'             //消息边界
)

// 服务端
func serverGo() {
	listener, err := net.Listen(ServerNet, ServerAddress)
	if err != nil {
		panic(err)
	}
	defer listener.Close()
	defer wg.Done()
	for true {
		conn, err := listener.Accept()
		if err != nil {
			panic(err)
		}
		go handConn(conn)
	}
}

func handConn(conn net.Conn) {
	for true {
		er := conn.SetReadDeadline(time.Now().Add(time.Second * 10)) // 10秒内没有收到消息，服务端就关闭该连接
		str, err := read(conn)
		if err != nil || er != nil {
			break
		}
		fmt.Println("server received: ", str)
		for true {
			intReq, err := strToInt32(str)
			if err != nil {
				n, _ := Write(conn, err.Error())
				fmt.Println("Sent error message bytes: ", n)
				continue
			}
			floatResp := math.Cbrt(intReq)
			respMsg := fmt.Sprintf("The cube root of %d is %f.", intReq, floatResp)
			_, err = Write(conn, respMsg)
			if err != nil {
				fmt.Println("resp failed err = ", err)
			}
		}
	}
}

func strToInt32(str string) (float64, error) {
	return strconv.ParseFloat(str, 64)
}

func Write(conn net.Conn, msg string) (int, error) {
	var buffer bytes.Buffer
	buffer.WriteString(msg)
	buffer.WriteByte(Delimiter)
	return conn.Write(buffer.Bytes())
}

func read(conn net.Conn) (string, interface{}) {
	readByte := make([]byte, 1)
	var buffer bytes.Buffer //处理部分读和部分写的问题
	for true {
		_, err := conn.Read(readByte)
		if err != nil {
			return "", err
		}
		rdbt := readByte[0]
		if rdbt == Delimiter {
			break
		}
		buffer.WriteByte(rdbt)
	}
	return buffer.String(), nil
}

//客户端
func clientGo(id int) {
	conn, err := net.DialTimeout(ServerNet, ServerAddress, 2*time.Second)
	if err != nil {
		panic(err)
	}
	defer conn.Close()
	defer wg.Done()
	fmt.Println("Client address id is ", id)
	requestNum := 5
	conn.SetDeadline(time.Now().Add(time.Millisecond * 5))
	for i := 0; i < requestNum; i++ {
		req := rand.Int31()
		_, err := Write(conn, fmt.Sprintf("%d", req))
		if err != nil {
			fmt.Println("write req failed err = ", err)
			continue
		}
	}
}
