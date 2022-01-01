package main

import (
	"context"
	"fmt"
	"time"
)

//验证父ctx 关闭时，子ctx也会立即关闭
func main() {
	par, cancle := context.WithCancel(context.Background())

	cur := context.WithValue(par, "a", "abc")
	go func() {
		for {
			select {
			case <-cur.Done(): //当子ctx 被关闭时，会关闭这里的channel，返回一个零值，使用该值来做验证
				fmt.Println("zi ctx closed")
				return
			default:
				fmt.Println("zi ctx running")
			}
		}
	}()
	time.Sleep(time.Millisecond)
	fmt.Println("par ctx closed ")
	cancle() //关闭par 的ctx
	time.Sleep(time.Millisecond)
}
