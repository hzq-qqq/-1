package dao

import (
	"log"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
)

//向购物项中插入购物项
func AddCartItem(cart *model.CartItem) error {
	sql := "insert into cart_items(count,amount,book_id,cart_id) values(?,?,?,?)"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare insert into cart_items failed err = ", err)
		return err
	}
	_, err = stmt.Exec(cart.Count, cart.GetAmount(), cart.Book.Id, cart.CartId)
	if err != nil {
		log.Fatal("exe insert into cart_items failed err = ", err)
		return err
	}
	return nil
}

//根据bookId 拿到购物项
func GetCartItemByBookId(bookId int) (*model.CartItem, error) {
	sql := "select id,count,amount,book_id,cart_id from cart_items where book_id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare select cartItem failed err = ", err)
		return nil, err
	}
	row := stmt.QueryRow(bookId)
	cartItem := &model.CartItem{
		CartItemId: 0,
		Book:       &model.Book{},
		Count:      0,
		Amount:     0,
		CartId:     "",
	}
	err = row.Scan(&cartItem.CartItemId, &cartItem.Count, &cartItem.Amount, &cartItem.Book.Id, &cartItem.CartId)
	if err != nil {
		return nil, err
	}
	return cartItem, nil
}

//根据购物车id拿到所有的购物项
func GetCartItemsByCartId(cartId string) ([]*model.CartItem, error) {
	sql := "select id,count,amount,book_id,cart_id from cart_items where cart_id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare select cartItem failed err = ", err)
		return nil, err
	}
	rows, err := stmt.Query(cartId)
	if err != nil {
		log.Fatal("exe select cartItem failed err = ", err)
		return nil, err
	}
	var cartItems []*model.CartItem
	for rows.Next() {
		cartItem := &model.CartItem{
			CartItemId: 0,
			Book:       &model.Book{},
			Count:      0,
			Amount:     0,
			CartId:     "",
		}
		err = rows.Scan(&cartItem.CartItemId, &cartItem.Count, &cartItem.Amount, &cartItem.Book.Id, &cartItem.CartId)
		if err != nil {
			return nil, err
		}
		cartItems = append(cartItems, cartItem)
	}
	return cartItems, nil
}
