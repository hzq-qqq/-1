package myDoubleList

import "fmt"

type doubleList struct {
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

func NewDoubleList() *doubleList {
	res := &doubleList{
		head: &node{},
		tail: &node{},
		len:  0,
	}
	res.head.next = res.tail
	res.tail.pre = res.head
	return res
}

//添加节点
func (dol *doubleList) AddNode(tp *node) {
	pre := dol.tail.pre
	pre.next = tp
	tp.pre = pre

	tp.next = dol.tail
	dol.tail.pre = tp
	dol.len++
}

//删除节点
func (dol *doubleList) DeleteNode(tp *node) {
	tp.pre.next = tp.next
	tp.next.pre = tp.pre
	dol.len--
}

//根据位置i，查询node
func (dol doubleList) FindNode(i int) *node {
	if i >= dol.len {
		return nil
	}
	sentinel := dol.head
	for i > 0 {
		sentinel = sentinel.next
		i--
	}
	return sentinel.next
}

//遍历
func (dol *doubleList) ForRange() {
	sentinel := dol.head
	for sentinel.next != nil {
		fmt.Println(sentinel.next.val)
		sentinel = sentinel.next
	}
}
