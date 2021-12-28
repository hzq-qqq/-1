package fir

import (
	"fmt"
	"testing"
	"time"
)

//测试可重入锁
func TestMyRecursiveLock(t *testing.T) {
	lock := &Recursive{}

	lock.Lock()
	fmt.Printf("第一次加锁，锁重入值为: %v \n", lock.recursive)
	lock.Lock()
	fmt.Printf("第二次加锁，锁重入值为: %v \n", lock.recursive)
	go func() {
		lock.Lock()
		fmt.Println("go 获取锁到锁")
		lock.UnLock()
		fmt.Println("go 释放锁")
	}()
	time.Sleep(time.Second * 3)
	lock.UnLock()
	lock.UnLock()
	fmt.Println("锁已经释放完毕")
	time.Sleep(time.Second * 3)
}
