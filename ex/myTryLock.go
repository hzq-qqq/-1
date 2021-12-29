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

//获取等待锁的goroutine数量
func (e *ExLock) GetWaiterNums() int {
	status := atomic.LoadInt32((*int32)(unsafe.Pointer(&e.Mutex)))
	v := status >> mutexWaiterShift
	return int(v)
}

// 锁是否被持有
func (m *ExLock) IsLocked() bool {
	state := atomic.LoadInt32((*int32)(unsafe.Pointer(&m.Mutex)))
	return state&mutexLocked == mutexLocked
}

// 是否有等待者被唤醒
func (m *ExLock) IsWoken() bool {
	state := atomic.LoadInt32((*int32)(unsafe.Pointer(&m.Mutex)))
	return state&mutexWoken == mutexWoken
}

// 锁是否处于饥饿状态
func (m *ExLock) IsStarving() bool {
	state := atomic.LoadInt32((*int32)(unsafe.Pointer(&m.Mutex)))
	return state&mutexStarving == mutexStarving
}
