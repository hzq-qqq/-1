//基于Mutex 实现tryLock功能
package ex

import (
	"sync"
	"sync/atomic"
	"unsafe"
)

const (
	mutexLocked      = 1 << iota //是否加锁
	mutexWoken                   //是否处于唤醒状态
	mutexStarving                //是否处于饥饿状态
	mutexWaiterShift = iota      //锁等待的goroutine数量
)

type ExLock struct {
	sync.Mutex
}

//尝试加锁
func (e *ExLock) TryLock() bool {
	if atomic.CompareAndSwapInt32((*int32)(unsafe.Pointer(&e.Mutex)), 0, mutexLocked) {
		return true
	}
	old := atomic.LoadInt32((*int32)(unsafe.Pointer(&e.Mutex)))
	if old&(mutexLocked|mutexWoken|mutexStarving|mutexWaiterShift) != 0 {
		return false
	}
	newSta := old | mutexLocked
	return atomic.CompareAndSwapInt32((*int32)(unsafe.Pointer(&e.Mutex)), old, newSta) //尝试在竞争下加锁（因为当先锁处为加锁状态）
}
