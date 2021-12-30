package main

import (
	"fmt"
	"sort"
)

func main() {
	matrix := make([][]int, 5)
	matrix[0] = []int{0, 1, 1, 1, 1}
	matrix[1] = []int{0, 1, 0, 0, 1}
	matrix[2] = []int{0, 1, 0, 0, 1}
	matrix[3] = []int{0, 1, 1, 1, 1}
	matrix[4] = []int{0, 1, 0, 1, 1}
	fmt.Println(getMaxMatrix(matrix))
}

//最大正方形边长（题目4）
func getMaxMatrix(matrix [][]int) int {
	m := len(matrix)
	n := len(matrix[0])
	rigt := getRi(matrix)
	dow := getDow(matrix)
	var ans int
	for i := 0; i < n; i++ {
		for j := 0; j < m; j++ {
			bj := min(i, j, n, m)
			for k := 0; k < bj; k++ {
				if matrix[i][j+k] == 0 { // 上边界出现一个0，不能构成正方形
					break
				}
				if rigt[i][j] >= k+1 && dow[i][j] >= k+1 && dow[i][j+k] >= k+1 && rigt[i+k][j] >= k+1 && k+1 > ans {
					ans = k + 1
				}
			}
		}
	}
	return ans
}

func min(i int, j int, n int, m int) int {
	if n-i < m-j {
		return n - i
	}
	return m - j
}

func getRi(matrix [][]int) [][]int {
	n := len(matrix)
	m := len(matrix[0])
	ri := make([][]int, n)
	for i := 0; i < n; i++ {
		ri[i] = make([]int, m)
		for j := m - 1; j >= 0; j-- {
			if matrix[i][j] == 1 {
				if j == m-1 {
					ri[i][j] = 1
				} else {
					ri[i][j] = ri[i][j+1] + 1
				}
			} else {
				if j == m-1 {
					ri[i][j] = 0
				} else {
					ri[i][j] = ri[i][j+1]
				}
			}
		}
	}
	return ri
}

func getDow(matrix [][]int) [][]int {
	n := len(matrix)
	m := len(matrix[0])
	ri := make([][]int, n)
	for i := 0; i < n; i++ {
		ri[i] = make([]int, m)
	}
	for j := 0; j < m; j++ {
		for i := n - 1; i >= 0; i-- {
			if matrix[i][j] == 1 {
				if i == n-1 {
					ri[i][j] = 1
				} else {
					ri[i][j] = ri[i+1][j] + 1
				}
			} else {
				if i == n-1 {
					ri[i][j] = 0
				} else {
					ri[i][j] = ri[i+1][j]
				}
			}
		}
	}
	return ri
}

//括号匹配问题(第二天 2 )
func kh(val string) int {
	var count int
	var ans int
	for i := 0; i < len(val); i++ {
		if val[i] == '(' {
			count++
		} else {
			count--
			if count < 0 {
				count = 0
				ans++
			}
		}
	}
	ans += count
	return ans
}

//A,B集合转移次数问题 —— 贪心
func AB(a []int, b []int) int {
	var aSum int
	var bSum int
	for i := 0; i < len(a); i++ {
		aSum += a[i]
	}
	for i := 0; i < len(b); i++ {
		bSum += b[i]
	}
	lessMp := make(map[int]bool)
	var maxSum int
	var minSum int
	var max []int
	var min []int
	var ops int
	if avg(aSum, len(a)) > avg(bSum, len(b)) {
		maxSum = avg(aSum, len(a))
		minSum = avg(bSum, len(b))
		max = a
		min = b
	} else {
		maxSum = avg(bSum, len(b))
		minSum = avg(aSum, len(a))
		max = b
		min = a
	}
	for i := 0; i < len(min); i++ {
		lessMp[min[i]] = true
	}
	sort.Ints(max)
	moreSize := len(max)
	lessSize := len(min)
	for i := 0; i < len(max); i++ {
		if max[i] < avg(maxSum, moreSize) && max[i] > avg(minSum, lessSize) && !lessMp[max[i]] {
			lessMp[max[i]] = true
			moreSize--
			maxSum -= max[i]
			lessSize--
			minSum += max[i]
			ops++
		}
	}
	return ops
}

func avg(sum int, l int) int {
	return sum / l
}
