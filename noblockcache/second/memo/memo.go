package memo

type Func func(key string) (interface{}, error)

type result struct {
	ans interface{}
	err error
}

type request struct {
	key      string
	response chan result
}

type memo struct {
	req chan request
}

type entry struct {
	res    result
	isAble chan struct{}
}

//单协程操作map 保证安全性
func (m *memo) doServer(f Func) {
	mp := make(map[string]*entry)
	for req := range m.req {
		e := mp[req.key]
		if e == nil {
			e = &entry{isAble: make(chan struct{})}
			mp[req.key] = e
			e.res.ans, e.res.err = f(req.key)
			close(e.isAble)
		} else {
			<-e.isAble
			req.response <- e.res
		}
	}
}

//获取缓存结果
func (m *memo) Get(key string) (interface{}, error) {
	response := make(chan result)
	m.req <- request{
		key:      key,
		response: response,
	}
	ans := <-response
	return ans.ans, ans.err
}

func New(f Func) *memo {
	mo := &memo{
		req: make(chan request),
	}
	go mo.doServer(f)
	return mo
}

func (m *memo) Close() {
	close(m.req)
}
