package controller

import (
	"net/http"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
)

//添加图书到购物车
func AddBook2Cart(resp http.ResponseWriter, req *http.Request) {
	bookId := req.FormValue("bookId")
	book := dao.GetBookById(bookId)
	_, sess := dao.IsLogin(req)
	userId := sess.UserId
	cart, _ := dao.GetCartByUserId(userId)
	if cart != nil {

	} else {
		cartId := utils.CreateUUID()
		cart := &model.Cart{
			CartId: cartId,
			UserId: userId,
		}
		cartItems := make([]*model.CartItem, 0)
		cartItem := &model.CartItem{
			Book:   book,
			Count:  1,
			CartId: cartId,
		}
		cartItems = append(cartItems, cartItem)
		cart.CartItems = cartItems
		//将购物车保存到数据库
		dao.AddCart(cart)
	}
}
