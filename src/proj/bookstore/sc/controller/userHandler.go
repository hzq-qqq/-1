package controller

import (
	"net/http"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
	"text/template"
)

func Login(resp http.ResponseWriter, req *http.Request) {
	if flag, _ := dao.IsLogin(req); flag {
		GetPageBooksByPrice(resp, req)
		return
	}
	username := req.PostFormValue("username")
	password := req.PostFormValue("password")
	u1, _ := dao.CheckUserNameAndPassword(username, password)
	if u1.Id > 0 {
		uuid := utils.CreateUUID()
		sess := &model.Session{
			SessionId: uuid,
			UserName:  u1.Username,
			UserId:    u1.Id,
		}
		dao.AddSession(sess)
		cook := http.Cookie{
			Name:     "user",
			Value:    uuid,
			HttpOnly: true,
		}
		http.SetCookie(resp, &cook) //返回cookie给客户端
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/user/login_success.html"))
		t.Execute(resp, u1)
	} else {
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/user/login.html"))
		t.Execute(resp, "用户名或密码不正确！")
	}
}

func Regist(resp http.ResponseWriter, req *http.Request) {
	username := req.PostFormValue("username")
	password := req.PostFormValue("password")
	email := req.PostFormValue("email")
	u1, _ := dao.CheckUserNameAndPassword(username, password)
	if u1.Id > 0 {
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/user/regist.html"))
		t.Execute(resp, "用户名已经存在！")
	} else {
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/user/regist_success.html"))
		err := dao.SaveUser(username, password, email)
		if err != nil {
			panic(err)
		}
		t.Execute(resp, nil)
	}
}

func CheckUserName(resp http.ResponseWriter, req *http.Request) {
	username := req.PostFormValue("username")
	u1, _ := dao.CheckUserName(username)
	if u1.Id > 0 {
		resp.Write([]byte("用户名已存在！"))
	} else {
		resp.Write([]byte("<font style='color:green'>用户可用！</font>"))
	}
}

//去注销
func Logout(resp http.ResponseWriter, req *http.Request) {
	cookie, _ := req.Cookie("user")
	if cookie != nil {
		cookieValue := cookie.Value
		dao.DeleteSession(cookieValue)
		cookie.MaxAge = -1
		http.SetCookie(resp, cookie) //将修改后cookie发送给浏览器
	}
	GetPageBooksByPrice(resp, req)
}
