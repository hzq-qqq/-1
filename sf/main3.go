package main

import "fmt"

type Node struct {
	str   string //字符串
	times int    //出现次数
}
type TopKStr struct {
	nodeMap      map[string]*Node //次频表
	indexNodeMap map[string]int   // 堆位置表
	heap         []*Node          //top堆
	len          int              //k
}

func NewTopKStr(len int) *TopKStr {
	return &TopKStr{
		nodeMap:      make(map[string]*Node),
		indexNodeMap: make(map[string]int),
		heap:         make([]*Node, 0),
		len:          len,
	}
}

//添加新元素
func (t *TopKStr) Add(str string) {
	var newNode *Node
	if t.nodeMap[str] == nil { //当前不存在该字符串
		newNode = &Node{
			str:   str,
			times: 1,
		}
		t.nodeMap[str] = newNode
		t.indexNodeMap[str] = -1 //表示当前该字符串不在堆中
	} else {
		newNode = t.nodeMap[str]
		newNode.times++ //该字符串已经出现过，访问次数加1
	}
	if t.indexNodeMap[str] == -1 { //当前字符串没有在堆中
		if t.len == len(t.heap) {
			if newNode.times > t.heap[0].times { //当前字符串出现的次数大于堆中字符串最小出现次数
				t.indexNodeMap[t.heap[0].str] = -1
				t.heap[0] = newNode
				t.indexNodeMap[newNode.str] = 0
				t.heapfy(0, t.len) //重新向下调整
			}
		} else {
			t.indexNodeMap[newNode.str] = len(t.heap)
			t.heap = append(t.heap, newNode)
			t.heapInsert(len(t.heap) - 1)
		}
	} else { //当前字符串已经在堆中,向下调整,构建小小根堆
		t.heapfy(t.indexNodeMap[str], t.len)
	}
}

func (t *TopKStr) heapInsert(idx int) {
	par := (idx - 1) / 2
	for {
		if par >= 0 && t.heap[idx].times < t.heap[par].times {
			t.indexNodeMap[t.heap[idx].str] = par
			t.indexNodeMap[t.heap[par].str] = idx
			t.swap(idx, par)
			idx = par
			par = (idx - 1) / 2
		} else {
			break
		}
	}
}

func (t *TopKStr) heapfy(tar int, heapSize int) {
	left := tar*2 + 1
	for {
		if left < heapSize && t.heap[tar].times > t.heap[left].times {
			t.swapIndex(tar, left)
			t.swap(tar, left)
			tar = left
			left = left*2 + 1
		} else if left+1 < heapSize && t.heap[tar].times > t.heap[left+1].times {
			t.swapIndex(tar, left+1)
			t.swap(tar, left+1)
			tar = left + 1
			left = left*2 + 1
		} else {
			break
		}
	}
}

func (t *TopKStr) swapIndex(l int, r int) {
	t.indexNodeMap[t.heap[l].str] = r
	t.indexNodeMap[t.heap[r].str] = l
}

func (t *TopKStr) swap(l int, r int) {
	tp := t.heap[l]
	t.heap[l] = t.heap[r]
	t.heap[r] = tp
}

func (t *TopKStr) printTopK() {
	fmt.Println("topK: ")
	for i := 0; i < len(t.heap); i++ {
		fmt.Printf("Top %v: %v \n", len(t.heap)-i, t.heap[i])
	}
	fmt.Println("=======================")
}

func main() {
	tpk := NewTopKStr(3)
	tpk.Add("aaa")
	tpk.Add("bbb")
	tpk.Add("ccc")
	tpk.printTopK()

	tpk.Add("aaa")
	tpk.printTopK()

	tpk.Add("ddd")
	tpk.printTopK()

	tpk.Add("ddd")
	tpk.printTopK()

}
