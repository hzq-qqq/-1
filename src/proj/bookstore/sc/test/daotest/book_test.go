package daotest

import (
	"fmt"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"testing"
)

func TestBook(t *testing.T) {
	books, _ := dao.GetBooks()
	for _, v := range books {
		fmt.Printf("%v\n", v)
	}
}

func TestAddBook(t *testing.T) {
	bk := &model.Book{
		Id:      1,
		Title:   "三国演义",
		Author:  "吴承恩",
		Price:   100,
		Sales:   1000,
		Stock:   2000,
		ImgPath: "src/proj/bookstore/static/img/default.jpg",
	}
	dao.AddBook(bk)
}

func TestDeleteBookById(t *testing.T) {
	err := dao.DeleteBookById("33")
	if err != nil {
		return
	}
}

func TestGetBookById(t *testing.T) {
	book := dao.GetBookById("2")
	fmt.Println(book)
}

func TestUpdateBook(t *testing.T) {
	book := &model.Book{
		Id:     1,
		Title:  "杂货店",
		Author: "东野",
		Price:  100,
		Sales:  100,
		Stock:  100,
	}
	dao.UpdateBook(book)
}

func TestPageBooks(t *testing.T) {
	page, _ := dao.GetPageBooks("2")
	fmt.Println("当前页是：", page.PageNo)
	fmt.Println("总页数是：", page.TotalPageNo)
	fmt.Println("总记录数是：", page.TotalRecord)
	for _, v := range page.Books {
		fmt.Println(v)
	}
}

func TestGetPagesBooksByPrice(t *testing.T) {
	page, _ := dao.GetPageBooksByPrice("3", "10", "30")
	for _, v := range page.Books {
		fmt.Println(v)
	}
}
