package main

import (
	"net/http"
	"newPro/src/proj/bookstore/sc/controller"
)

func main() {
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("src/proj/bookstore/static"))))

	http.Handle("/pages/", http.StripPrefix("/pages/", http.FileServer(http.Dir("src/proj/bookstore/pages"))))

	http.HandleFunc("/index", controller.IndexHandler)

	http.HandleFunc("/login", controller.Login)

	http.HandleFunc("/regist", controller.Regist)

	http.HandleFunc("/checkUserName", controller.CheckUserName)

	http.HandleFunc("/getPageBooks", controller.GetPageBooks)

	http.HandleFunc("/UpdateOrAddBook", controller.UpdateOrAddBook)

	http.HandleFunc("/deleteBook", controller.DeleteBook)

	http.HandleFunc("/toUpdateBookPage", controller.ToUpdateBookPage)

	http.HandleFunc("/getPageBooksByPrice", controller.GetPageBooksByPrice)

	http.HandleFunc("/logout", controller.Logout)

	http.HandleFunc("/addBook2Cart", controller.AddBook2Cart)

	http.ListenAndServe(":8080", nil)
}
