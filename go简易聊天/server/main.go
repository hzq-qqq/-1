package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"sync"
	"time"
)

var (
	//消息通道
	message = make(chan string, 100)
	//管理所有的cli
	clients = make(map[chan string]bool)
	// 处理新到来的客户端，并处理添加cli 到clients 中，避免并发访问的问题
	come = make(chan chan string)
	//处理当cli离开时但协程删除clients
	leave = make(chan chan string)
	//超时断开客户端连接
	timeMap = make(map[chan string]int)
	//互斥锁，配合timeMap使用保证安全性
	connMap = make(map[chan string]net.Conn)
	lock    = sync.Mutex{}
)

func main() {
	liser, err := net.Listen("tcp", "localhost:8000")
	if err != nil {
		log.Fatal("listener failed err = ", err)
		return
	}
	go boastProcess() // 处理广播信息
	go idleProcess()
	for true {
		conn, err := liser.Accept()
		if err != nil {
			log.Fatal("accent failed err = ", err)
			return
		}
		go process(conn)
	}
}

//断开超时间没有发送消息的客户端连接
func idleProcess() {
	for true {
		time.Sleep(10 * time.Second)
		lock.Lock()
		for ch, ti := range timeMap {
			if ti <= time.Now().Second() {
				leave <- ch
			}
		}
		lock.Unlock()
	}
}

//广播信息
func boastProcess() {
	for true {
		select {
		case msg := <-message:
			{
				fmt.Println(msg)
				for cli := range clients {
					cli <- msg
				}
			}
		case msg := <-come:
			{
				clients[msg] = true // 单协程添加新的cli到clients 中，避免添加的安全问题
			}
		case msg := <-leave:
			{
				if clients[msg] {
					delete(clients, msg)
					close(msg)
					connMap[msg].Close()
					delete(connMap, msg)
				}
			}

		}
	}
}

//处理客户端连接的信息
func process(conn net.Conn) {
	who := conn.RemoteAddr().String()
	message <- who + " has arrived"
	ch := make(chan string, 2)
	come <- ch

	lock.Lock()
	timeMap[ch] = time.Now().Second() + int(time.Second.Seconds()*10)
	connMap[ch] = conn
	lock.Unlock()

	go clientWrite(conn, ch)
	go write(conn, ch, who)
}

func write(conn net.Conn, ch chan string, who string) {
	read := bufio.NewScanner(conn)
	for read.Scan() {
		timeMap[ch] = time.Now().Second() + int(time.Second.Seconds()*10) // 每次收到客户端发送过来的消息就更新操作时间
		message <- who + " say " + read.Text()
	}
	leave <- ch
}

//处理接受其他cli发送的信息
func clientWrite(conn net.Conn, ch chan string) {
	for ms := range ch {
		fmt.Fprintf(conn, ms)
	}
}
