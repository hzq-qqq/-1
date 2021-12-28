package goid

import (
	"runtime"
	"strconv"
	"strings"
)

//拿到channel的id
func Get() (int64, error) {
	bt := make([]byte, 1024)
	runtime.Stack(bt, false)
	str := strings.Fields(string(bt))
	id, err := strconv.Atoi(str[1])
	if err != nil {
		return -1, err
	}
	return int64(id), nil
}
