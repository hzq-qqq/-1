package controller

import (
	"log"
	"net/http"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"strconv"
	"text/template"
)

//删除图书
func DeleteBook(resp http.ResponseWriter, req *http.Request) {
	bkId := req.FormValue("bookId")
	dao.DeleteBookById(bkId)
	GetPageBooks(resp, req)
}

//跳转到图书修改页面
func ToUpdateBookPage(resp http.ResponseWriter, req *http.Request) {
	bkId := req.FormValue("bookId")
	book := dao.GetBookById(bkId)
	if book.Id > 0 {
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/manager/book_edit.html"))
		t.Execute(resp, book)
	} else {
		t := template.Must(template.ParseFiles("src/proj/bookstore/pages/manager/book_edit.html"))
		t.Execute(resp, "")
	}
}

//跳转到图书修改页面
func UpdateOrAddBook(resp http.ResponseWriter, req *http.Request) {
	title := req.PostFormValue("title")
	author := req.PostFormValue("author")
	price := req.PostFormValue("price")
	sales := req.PostFormValue("sales")
	stock := req.PostFormValue("stock")
	bookId := req.PostFormValue("bookId")

	fprice, _ := strconv.ParseFloat(price, 64)
	fsales, _ := strconv.ParseInt(sales, 10, 0)
	fstock, _ := strconv.ParseInt(stock, 10, 0)
	fInt, _ := strconv.ParseInt(bookId, 10, 0)

	book := &model.Book{
		Id:     int(fInt),
		Title:  title,
		Author: author,
		Price:  fprice,
		Sales:  int(fsales),
		Stock:  int(fstock),
	}
	if book.Id > 0 {
		err := dao.UpdateBook(book)
		if err != nil {
			log.Fatal("controller update book failed err = ", err)
		}
	} else {
		err := dao.AddBook(book)
		if err != nil {
			log.Fatal("controller add book failed err = ", err)
		}
	}
	GetPageBooks(resp, req)
}

//获取带分页的图书
func GetPageBooks(resp http.ResponseWriter, req *http.Request) {
	pageNo := req.FormValue("pageNo")
	page, _ := dao.GetPageBooks(pageNo)
	t := template.Must(template.ParseFiles("src/proj/bookstore/pages/manager/book_manager.html"))
	t.Execute(resp, page)
}

func IndexHandler(resp http.ResponseWriter, req *http.Request) {
	pageNo := req.FormValue("pageNo")
	if pageNo == "" {
		pageNo = "1"
	}
	page, _ := dao.GetPageBooks(pageNo)
	t := template.Must(template.ParseFiles("src/proj/bookstore/index.html"))
	t.Execute(resp, page)
}

func GetPageBooksByPrice(resp http.ResponseWriter, req *http.Request) {
	pageNo := req.FormValue("pageNo")
	minPrice := req.FormValue("min")
	maxPrice := req.FormValue("max")
	if pageNo == "" {
		pageNo = "1"
	}
	var page *model.Page
	if minPrice == " " && maxPrice == " " {
		page, _ = dao.GetPageBooks(pageNo)
	} else {
		page, _ = dao.GetPageBooksByPrice(pageNo, minPrice, maxPrice)
		page.MinPrice = minPrice
		page.MaxPrice = maxPrice
	}

	if flag, sess := dao.IsLogin(req); flag {
		if sess.UserId > 0 {
			page.IsLogin = true
			page.UserName = sess.UserName
		}
	}
	t := template.Must(template.ParseFiles("src/proj/bookstore/index.html"))
	t.Execute(resp, page)
}
