package daotest

import (
	"fmt"
	"newPro/src/proj/bookstore/sc/dao"
	"newPro/src/proj/bookstore/sc/model"
	"testing"
)

func TestSave(t *testing.T) {
	dao.SaveUser("zhangsan", "123", "175@qq.com")
}

func TestLogin(t *testing.T) {
	u1, _ := dao.CheckUserNameAndPassword("zhangsan", "123")
	fmt.Println(*u1)
}

func TestAddCart(t *testing.T) {
	book := &model.Book{
		Id:    0,
		Price: 27.2,
	}
	book2 := &model.Book{
		Id:    1,
		Price: 23.00,
	}
	cartItem := &model.CartItem{
		Book:   book,
		Count:  10,
		CartId: "66668888",
	}
	cartItem.Amount = cartItem.GetAmount()
	cartItem2 := &model.CartItem{
		Book:   book2,
		Count:  10,
		CartId: "66668888",
	}
	cartItem2.Amount = cartItem2.GetAmount()
	var cartItems []*model.CartItem
	cartItems = append(cartItems, cartItem)
	cartItems = append(cartItems, cartItem2)
	cart := &model.Cart{
		CartId:     "66668888",
		CartItems:  cartItems,
		TotalCount: 20,
		UserId:     4,
	}
	cart.TotalAmount = cart.GetTotalAmount()
	dao.AddCart(cart)
}

func TestGetCartItemByBookId(t *testing.T) {
	cartItem, _ := dao.GetCartItemByBookId(0)
	fmt.Printf("%#v \n", cartItem)
}

func TestGetCartItemsByCartId(t *testing.T) {
	cartItems, _ := dao.GetCartItemsByCartId("66668888")
	for _, v := range cartItems {
		fmt.Println(v)
	}
}

func TestGetCartByUserId(t *testing.T) {
	cart, _ := dao.GetCartByUserId(3)
	fmt.Println(cart)
}
