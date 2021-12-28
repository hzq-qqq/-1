//基于用户提供的token实现加锁释放锁  —— 这种做法更好。
package sec

import (
	"fmt"
	"sync"
	"sync/atomic"
)

type RecursiveLock struct {
	sy        sync.Mutex
	token     int64
	recursive int64
}

//基于token标识做加锁
func (r *RecursiveLock) Lock(token int64) {
	if atomic.LoadInt64(&r.token) == token {
		r.recursive++ //锁重入了，重入值+1，后直接返回
		return
	}
	r.sy.Lock() //第一次加锁或者锁没重入
	atomic.StoreInt64(&r.token, token)
	r.recursive = 1
}

func (r *RecursiveLock) UnLock(token int64) {
	if atomic.LoadInt64(&r.token) != token {
		panic(fmt.Sprintf("No the owner of lock Unlock"))
	}
	r.recursive--
	if r.recursive > 0 { //锁依然重入
		return
	}
	atomic.StoreInt64(&r.token, 0)
	r.sy.Unlock()
}
