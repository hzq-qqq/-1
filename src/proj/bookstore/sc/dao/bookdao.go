package dao

import (
	"log"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
	"strconv"
)

//拿到所有的图书
func GetBooks() ([]*model.Book, error) {
	sql := "select id,title,author,price,sales,stock,img_path from books"
	rows, _ := utils.Db.Query(sql)
	var books []*model.Book
	for rows.Next() {
		book := &model.Book{}
		rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImgPath)
		books = append(books, book)
	}
	return books, nil
}

//根据图书的id删除图书
func DeleteBookById(id string) error {
	sql := "delete from books where id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare delete book failed err = ", err)
		return err
	}
	_, err = stmt.Exec(id)
	if err != nil {
		log.Fatal("exe delete book failed err = ", err)
		return err
	}
	return nil
}

//根据图书的id删除图书
func GetBookById(id string) *model.Book {
	sql := "select id,title,author,price,sales,stock,img_path from books where id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare delete book failed err = ", err)
		return nil
	}
	row := stmt.QueryRow(id)
	book := &model.Book{}
	row.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImgPath)

	return book
}

//更新图书的信息
func UpdateBook(book *model.Book) error {
	sql := "update books set title = ? ,author = ? ,price = ? ,sales = ?, stock = ? where id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("dao prepare update book failed err = ", err)
		return err
	}
	_, err = stmt.Exec(book.Title, book.Author, book.Price, book.Sales, book.Stock, book.Id)
	if err != nil {
		log.Fatal("dao exe update book failed err = ", err)
		return err
	}
	return nil
}

//带有分页的数据信息
func GetPageBooks(pageNo string) (*model.Page, error) {
	fpageNo, _ := strconv.ParseInt(pageNo, 10, 0)
	sql := "select count(*) from books"
	var totalRecord int64
	row := utils.Db.QueryRow(sql)
	row.Scan(&totalRecord)
	//设置每页只显示4条激励
	var pageSize int64
	pageSize = 4
	var totalPageNo int64
	if totalRecord%pageSize == 0 {
		totalPageNo = totalRecord / pageSize
	} else {
		totalPageNo = totalRecord/pageSize + 1
	}
	sql1 := "select id, title,author,price,sales,stock,img_path from books limit ? ,? "
	stmt, err := utils.Db.Prepare(sql1)
	if err != nil {
		log.Fatal("prepare select pageSize books failed; err = ", err)
	}
	rows, _ := stmt.Query((fpageNo-1)*pageSize, pageSize)
	books := make([]*model.Book, 0)
	for rows.Next() {
		book := &model.Book{}
		rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImgPath)
		books = append(books, book)
	}
	page := &model.Page{
		Books:       books,
		PageNo:      fpageNo,
		PageSize:    pageSize,
		TotalPageNo: totalPageNo,
		TotalRecord: totalRecord,
	}
	return page, nil
}

//带有分页的查询
func GetPageBooksByPrice(pageNo string, minPrice string, maxPrice string) (*model.Page, error) {
	fpageNo, _ := strconv.ParseInt(pageNo, 10, 0)
	sql := "select count(*) from books where price between ? and ?"
	var totalRecord int64
	stmt, _ := utils.Db.Prepare(sql)
	row, _ := stmt.Query(minPrice, maxPrice)
	row.Scan(&totalRecord)
	//设置每页只显示4条激励
	var pageSize int64
	pageSize = 4
	var totalPageNo int64
	if totalRecord%pageSize == 0 {
		totalPageNo = totalRecord / pageSize
	} else {
		totalPageNo = totalRecord/pageSize + 1
	}
	sql1 := "select id, title,author,price,sales,stock,img_path from books where price between ? and ? limit ? ,? "
	stmt, err := utils.Db.Prepare(sql1)
	if err != nil {
		log.Fatal("prepare select pageSize books failed; err = ", err)
	}
	rows, _ := stmt.Query(minPrice, maxPrice, (fpageNo-1)*pageSize, pageSize)
	books := make([]*model.Book, 0)
	for rows.Next() {
		book := &model.Book{}
		rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImgPath)
		books = append(books, book)
	}
	page := &model.Page{
		Books:       books,
		PageNo:      fpageNo,
		PageSize:    pageSize,
		TotalPageNo: totalPageNo,
		TotalRecord: totalRecord,
	}
	return page, nil
}
