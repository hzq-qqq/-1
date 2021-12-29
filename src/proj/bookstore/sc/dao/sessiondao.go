package dao

import (
	"log"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
)

//添加session
func AddSession(sess *model.Session) error {
	sql := "insert into sessions values(?,?,?)"
	stmt, _ := utils.Db.Prepare(sql)
	_, err := stmt.Exec(sess.SessionId, sess.UserName, sess.UserId)
	if err != nil {
		log.Fatal("exe insert sessions failed err = ", err)
		return err
	}
	return nil
}

//根据sessionId删除session
func DeleteSession(sessionId string) error {
	sql := "delete from sessions where session_id = ?"
	stmt, _ := utils.Db.Prepare(sql)
	_, err := stmt.Exec(sessionId)
	if err != nil {
		log.Fatal("exe delete sessions failed err = ", err)
		return err
	}
	return nil
}

//根据sessionId得到session
func GetSessionById(sessionId string) (*model.Session, error) {
	sql := "select session_id,username,user_id from sessions where session_id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("select session failed err = ", err)
		return nil, err
	}
	row := stmt.QueryRow(sessionId)
	sess := &model.Session{}
	row.Scan(&sess.SessionId, &sess.UserName, &sess.UserId)
	return sess, nil
}
