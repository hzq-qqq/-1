package arrIm

import "testing"

func TestName(t *testing.T) {
	dq := NewDeque(10)
	dq.AddFirst(1)
	dq.AddFirst(2)
	dq.AddFirst(3)
	dq.AddLast(2)
	dq.AddLast(3)
	dq.AddLast(4)
	dq.ForRange()
	//弹出首部元素
	dq.PollFirst()
	dq.ForRange()
	//弹出尾部元素
	dq.PollLast()
	dq.ForRange()
}
