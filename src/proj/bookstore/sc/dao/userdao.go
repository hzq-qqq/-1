package dao

import (
	"log"
	"net/http"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
)

//验证用户名和密码
func CheckUserNameAndPassword(username string, password string) (*model.User, error) {
	sql := "select id,username,password,email from users where username = ? and password = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("stam failed err = ", err)
		return nil, err
	}
	row := stmt.QueryRow(username, password)
	user := &model.User{}
	row.Scan(&user.Id, &user.Username, &user.Password, &user.Email)
	return user, nil
}

func CheckUserName(username string) (*model.User, error) {
	sql := "select id,username,password,email from users where username = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("stam failed err = ", err)
		return nil, err
	}
	row := stmt.QueryRow(username)
	user := &model.User{}
	row.Scan(&user.Id, &user.Username, &user.Password, &user.Email)
	return user, nil
}

//添加用户
func SaveUser(username string, password string, email string) error {
	sql := "insert into users(username,password,email) values(?,?,?)"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("exe failed err = ", err)
		return err
	}
	_, err = stmt.Exec(username, password, email)
	if err != nil {
		log.Fatal("exe failed err = ", err)
		return err
	}
	return nil
}

// 添加图书
func AddBook(b *model.Book) error {
	sql := "insert into books(title,author,price,sales,stock,img_path) values(?,?,?,?,?,?)"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		panic(err)
	}
	_, err = stmt.Exec(b.Title, b.Author, b.Price, b.Sales, b.Stock, b.ImgPath)
	if err != nil {
		panic(err)
	}
	return nil
}

//检测是否登陆
func IsLogin(r *http.Request) (bool, *model.Session) {
	cookie, _ := r.Cookie("user")
	if cookie != nil {
		cookieValue := cookie.Value
		sess, _ := GetSessionById(cookieValue)
		return sess.UserId > 0, sess
	}
	return false, nil
}
