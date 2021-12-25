package myDoubleList

import (
	"fmt"
	"testing"
)

func TestDoubleList(t *testing.T) {
	dou := NewDoubleList()
	tp1 := NewNode(1)
	dou.AddNode(tp1)

	tp2 := NewNode(2)
	dou.AddNode(tp2)

	tp3 := NewNode(3)
	dou.AddNode(tp3)

	dou.ForRange()

	fmt.Println("========================")

	dou.DeleteNode(tp1)
	dou.ForRange()

	fmt.Println("========================")
	tp2.val = 10
	dou.ForRange()

	fmt.Println("========================")
	fmt.Println(dou.FindNode(0))
}
