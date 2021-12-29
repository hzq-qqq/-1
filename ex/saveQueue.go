//基于Mutex实现一个安全的队列
package ex

import "sync"

type SaveQueue struct {
	value []interface{}
	sy    sync.Mutex
}

//添加值
func (s *SaveQueue) Add(val interface{}) {
	s.sy.Lock()
	defer s.sy.Unlock()
	s.value = append(s.value, val)
}

//拿到队列首部值
func (s *SaveQueue) Poll() interface{} {
	s.sy.Lock()
	defer s.sy.Unlock()
	if len(s.value) == 0 {
		return nil
	}
	val := s.value[0]
	s.value = s.value[1:]
	return val
}
