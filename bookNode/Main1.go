package main

import "fmt"

func main() {
	var myStack minStack
	myStack.push(3)
	myStack.push(4)
	myStack.push(5)
	myStack.push(1)
	myStack.push(2)
	myStack.push(2)
	n := len(myStack.data)
	for i := 0; i < n; i++ {
		fmt.Println(myStack.getmi(), " : ", myStack.pop())
	}
}

//设计一个具有getmi()功能的栈
//实现思路（1）： 使用两个栈，data，mi，
//         data作为普通的栈存储元素，mi 用栈的方式保存当前栈中最小的数据
//        pop: 直接弹出两个栈的元素就可以了
//        push： 向将数据直接存储到data栈中，再存入mi，存入 newVal和mi栈顶的最小值到栈顶中
//        getmi（） 直接从mi 中拿去数据就可以了
//type mi struct {
//	data []int
//	mi  []int
//}
//
//func (m *mi) pop() int {
//	n := len(m.data) - 1
//	if n == -1 {
//		return -1
//	}
//	res := m.data[n]
//	m.data = m.data[:n]
//	m.mi = m.mi[:n]
//	return res
//}
//func (m *mi) push(val int) {
//	n := len(m.data)
//	m.data = append(m.data, val)
//	if n > 0 && val > m.mi[n-1] {
//		m.mi = append(m.mi, m.mi[n-1])
//	} else {
//		m.mi = append(m.mi, val)
//	}
//}
//func (m *mi) getmi() int {
//	n := len(m.data)
//	if n > 0 {
//		return m.mi[n-1]
//	}
//	return -1 // 栈当前为空
//}

//实现思路（2） -
type minStack struct {
	data []int
	mi   []int
}

func (m *minStack) pop() int {
	n := len(m.data) - 1
	ml := len(m.mi) - 1
	if n == -1 {
		return -1
	}
	res := m.data[n]
	m.data = m.data[:n]
	if res == m.mi[ml] { //待弹出元素的值和当前min栈顶值相等时才弹出
		m.mi = m.mi[:ml]
	}
	return res
}
func (m *minStack) push(val int) {
	n := len(m.mi)
	m.data = append(m.data, val)
	if n == 0 || val < m.mi[n-1] { //只有newVal比当前min栈顶的值小才覆盖存入
		m.mi = append(m.mi, val)
	}
}
func (m *minStack) getmi() int {
	n := len(m.mi)
	if n > 0 {
		return m.mi[n-1]
	}
	return -1 // 栈当前为空
}

//点评：
//方案一和方案二其实都是用 stackMin 栈保存着 stackData 每一步的最小值。共同点是所有
//操作的时间复杂度都为 O(1)、空间复杂度都为 O(n)。区别是：方案一中 stackMin 压入时稍省空
//间，但是弹出操作稍费时间；方案二中 stackMin 压入时稍费空间，但是弹出操作稍省时间。
