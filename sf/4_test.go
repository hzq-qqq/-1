package main

import (
	"fmt"
	"testing"
)

func TestSf(t *testing.T) {
	var tp = []int{4, 4, 2, 3, 9, 7, 5}
	fmt.Println(six1(tp))
}

//最大绝对值只差(基本实现)
func six(nums []int) int {
	if len(nums) == 0 {
		return 0
	}
	n := len(nums)
	pre := make([]int, n)
	pre[0] = nums[0]
	post := make([]int, n)
	post[n-1] = nums[n-1]
	for i := 1; i < n; i++ {
		pre[i] = mx(pre[i-1], nums[i])
	}
	for i := n - 2; i >= 0; i-- {
		post[i] = mx(post[i+1], nums[i])
	}
	ans := 0
	idx := 1
	for idx < n {
		ans = mx(ans, abs(pre[idx-1]-post[idx]))
		idx++
	}
	return ans
}

//优化空间复杂度
func six1(nums []int) int {
	if len(nums) == 0 {
		return 0
	}
	max := 0
	l := 1
	lMax := nums[0]
	r := len(nums) - 2
	rMax := nums[len(nums)-1]
	ans := 0
	for i := 0; i < len(nums); i++ {
		max = mx(max, nums[i])
	}
	for l <= r {
		ans = mx(ans, mx(max-rMax, max-lMax))
		lMax = mx(lMax, nums[l])
		rMax = mx(rMax, nums[r])
		l++
		r--
	}
	return ans
}

func abs(v int) int {
	if v > 0 {
		return v
	}
	return -v
}

func mx(l int, r int) int {
	if l > r {
		return l
	}
	return r
}
