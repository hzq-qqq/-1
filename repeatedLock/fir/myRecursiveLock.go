//基于goroutine id 实现的可重入锁
package fir

import (
	"fmt"
	"log"
	"newPro/concurrency/repeatedLock/fir/goid"
	"sync"
	"sync/atomic"
)

type Recursive struct {
	sy        sync.Mutex
	owner     int64 //锁持有者
	recursive int64 // 锁重入次数
}

//可重入加锁
func (r *Recursive) Lock() {
	gid, err := goid.Get()
	if err != nil {
		log.Fatal("get channel id failed err = ", err)
		return
	}
	if atomic.LoadInt64(&r.owner) == gid { //发生锁重入，重入值加一后，返回
		r.recursive++
		return
	}
	r.sy.Lock()                      //没有锁重入或第一次加锁
	atomic.StoreInt64(&r.owner, gid) //得到锁，原子设置锁
	r.recursive = 1                  //第一次得到锁，设置锁重入值为1
}

//可重入加锁
func (r *Recursive) UnLock() {
	gid, err := goid.Get()
	if err != nil {
		log.Fatal("get goroutineId failed err = ", err)
		return
	}
	if atomic.LoadInt64(&r.owner) != gid {
		panic(fmt.Sprintf("wrong the owner UnLock"))
	}
	r.recursive--
	if r.recursive != 0 { // 锁重入依然大于0，直接返回
		return
	}
	atomic.StoreInt64(&r.owner, -1)
	r.sy.Unlock()
}
