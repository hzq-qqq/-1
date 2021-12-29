package main

import "sync"

var lock sync.Mutex

func main() {

	lock.Lock()
	lock.Unlock()
}
