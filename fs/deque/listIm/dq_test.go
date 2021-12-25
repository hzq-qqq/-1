package listIm

import "testing"

func TestDeque(t *testing.T) {
	dq := NewDeque()
	dq.AddLast(NewNode(1))
	dq.AddLast(NewNode(2))
	dq.AddLast(NewNode(3))
	dq.AddLast(NewNode(4))
	dq.AddLast(NewNode(5))
	dq.ForRange()

	//弹出首部元素
	dq.PollFirst()
	dq.ForRange()

	//弹出尾部元素
	dq.PollLast()
	dq.ForRange()

}
