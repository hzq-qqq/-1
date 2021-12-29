package daotest

import (
	"fmt"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"testing"
)

func TestAddSession(t *testing.T) {
	sess := &model.Session{
		SessionId: "13072349194",
		UserName:  "张三",
		UserId:    3,
	}
	dao.AddSession(sess)
}

func TestDeleteSession(t *testing.T) {
	dao.DeleteSession("13072349194")
}

func TestGetSession(t *testing.T) {
	sess, _ := dao.GetSessionById("8322d9e1-03c7-4f3b-46b6-efbb33b1029c")
	fmt.Println(sess)
}
