package memo

import "sync"

//方法类型
type Func func(key string) (interface{}, error)

//缓存结果
type Result struct {
	res interface{}
	err error
}

type entry struct {
	res    Result
	isAble chan struct{}
}

//缓存
type Memo struct {
	f     Func
	cache map[string]*entry
	mu    sync.Mutex
}

//返回f函数的缓存a
func New(f Func) *Memo {
	return &Memo{
		f:     f,
		cache: make(map[string]*entry),
	}
}

//从缓存中获取结果(仅对并发不安全的操作设置同步)
func (m *Memo) Get(key string) (interface{}, error) {
	m.mu.Lock()
	e := m.cache[key]
	if e == nil {
		e := &entry{
			isAble: make(chan struct{}),
		}
		m.cache[key] = e
		m.mu.Unlock()
		e.res.res, e.res.err = m.f(key) // f函数的执行和请求的加锁，解锁，等待结果并行执行，性能更高
		close(e.isAble)
	} else {
		m.mu.Unlock()
		<-e.isAble
	}
	return e.res.res, e.res.err
}
