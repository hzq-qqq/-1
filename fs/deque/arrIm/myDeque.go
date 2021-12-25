//基于数组实现双端队列  —— 适用于数组长度固定的情况
package arrIm

import "fmt"

type deque struct {
	head  int
	tail  int
	value []interface{}
	len   int
}

func NewDeque(len int) *deque {
	if len <= 0 {
		return nil
	}
	return &deque{
		head:  len/2 - 1,
		tail:  len / 2,
		value: make([]interface{}, len+2),
		len:   0,
	}
}

//实现中使用了两个空位置来表示head，tail下一个需要存储的位置
func (dq *deque) AddFirst(val interface{}) {
	if dq.len < cap(dq.value)-2 {
		dq.value[dq.head] = val
		dq.head = (dq.head - 1) % cap(dq.value)
	}
}

func (dq *deque) AddLast(val interface{}) {
	if dq.len < cap(dq.value)-2 {
		dq.value[dq.tail] = val
		dq.tail = (dq.tail + 1) % cap(dq.value)
	}
}

func (dq *deque) PollFirst() interface{} {
	res := dq.value[(dq.head+1)%cap(dq.value)]
	dq.head = (dq.head + 1) % cap(dq.value)
	return res
}

func (dq *deque) PollLast() interface{} {
	res := dq.value[(dq.tail-1)%cap(dq.value)]
	dq.tail = (dq.tail - 1) % cap(dq.value)
	return res
}

func (dq *deque) ForRange() {
	bg := (dq.head + 1) % cap(dq.value)
	for bg != (dq.tail)%cap(dq.value) {
		fmt.Print(dq.value[bg], " ")
		bg = (bg + 1) % cap(dq.value)
	}
	fmt.Println("==================")
}
