//基于链表实现双端队列
package listIm

import "fmt"

type deque struct {
	head *node
	tail *node
	len  int
}

type node struct {
	val  interface{}
	pre  *node
	next *node
}

func NewNode(val interface{}) *node {
	return &node{
		val: val,
	}
}

func NewDeque() *deque {
	dq := &deque{
		head: &node{},
		tail: &node{},
	}
	dq.head.next = dq.tail
	dq.tail.pre = dq.head
	return dq
}

//首部添加node
func (dq *deque) AddFirst(tp *node) {
	next := dq.head.next
	dq.head.next = tp
	tp.pre = dq.head

	tp.next = next
	next.pre = tp
	dq.len++
}

//在尾部插入元素
func (dq *deque) AddLast(tp *node) {
	pre := dq.tail.pre
	tp.next = dq.tail
	dq.tail.pre = tp

	tp.pre = pre
	pre.next = tp

	dq.len++
}

//弹出头部节点
func (dq *deque) PollFirst() *node {
	if dq.len < 1 {
		return nil
	}
	res := dq.head.next
	dq.head.next = nil
	dq.head = res
	return res
}

//弹出尾部节点
func (dq *deque) PollLast() *node {
	if dq.len < 1 {
		return nil
	}
	res := dq.tail.pre
	dq.tail = dq.tail.pre
	dq.tail.next = nil
	return res
}

func (dq *deque) ForRange() {
	sentinel := dq.head
	for sentinel.next != dq.tail {
		fmt.Println(sentinel.next.val)
		sentinel = sentinel.next
	}
	fmt.Println("======================")
}
