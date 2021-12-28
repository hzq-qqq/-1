package ex

import (
	"fmt"
	"testing"
	"time"
)

func TestTryLock(t *testing.T) {
	ex := &ExLock{}
	go func() {
		if ex.TryLock() {
			fmt.Println("go 尝试加锁成功")
			fmt.Println("go 访问共享资源")
			time.Sleep(time.Second * 2)
			ex.Unlock()
		} else {
			fmt.Println("go 尝试加锁失败")
		}
	}()
	if ex.TryLock() {
		fmt.Println("main 尝试加锁成功")
		fmt.Println("main 访问共享资源")
		time.Sleep(time.Second * 2)
		ex.Unlock()
	} else {
		fmt.Println("main 尝试加锁失败")
	}

}
