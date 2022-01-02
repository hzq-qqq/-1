package main

import (
	"fmt"
	"sync"
	"time"
)

//有一道经典的使用 Channel 进行任务编排的题，你可以尝试做一下：
//有四个 goroutine，编号为 1、2、3、4。每秒钟会有一个 goroutine
//打印出它自己的编号，要求你编写一个程序，让输出的编号总是按照
//1、2、3、4、1、2、3、4、……的顺序打印出来。

var (
	c1 = make(chan int, 1)
	c2 = make(chan int, 1)
	c3 = make(chan int, 1)
	c4 = make(chan int, 1)
)

func main() {
	var wg sync.WaitGroup
	wg.Add(4)
	c4 <- 1
	go func() { //1
		for {
			time.Sleep(time.Second)
			<-c4
			fmt.Print(1, " ")
			c1 <- 1
		}
		wg.Done()
	}()
	go func() { //2
		for {
			time.Sleep(time.Second)
			<-c1
			fmt.Print(2, " ")
			c2 <- 2
		}
		wg.Done()
	}()
	go func() { //3
		for {
			time.Sleep(time.Second)
			<-c2
			fmt.Print(3, " ")
			c3 <- 3
		}
		wg.Done()
	}()
	go func() { //4
		for {
			time.Sleep(time.Second)
			<-c3
			fmt.Print(4, " ")
			c4 <- 4
		}
		wg.Done()
	}()
	wg.Wait()
}
