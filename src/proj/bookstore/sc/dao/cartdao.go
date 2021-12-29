package dao

import (
	"log"
	"newPro/src/proj/bookstore/sc/model"
	"newPro/src/proj/bookstore/sc/utils"
)

//添加购物车
func AddCart(cart *model.Cart) error {
	sql := "insert into carts(id,total_count,total_amount,user_id) values(?,?,?,?)"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("prepare insert carts failed err = ", err)
		return err
	}
	_, err = stmt.Exec(cart.CartId, cart.TotalCount, cart.TotalAmount, cart.UserId)
	if err != nil {
		log.Fatal("exe insert carts failed err = ", err)
		return err
	}
	for _, v := range cart.CartItems {
		//保存购物项,将购物项插入到购物项表中
		AddCartItem(v)
	}
	return nil
}

//根据userId 查询cart —— 购物项
func GetCartByUserId(userId int) (*model.Cart, error) {
	sql := "select id,total_count,total_amount,user_id from carts where user_id = ?"
	stmt, err := utils.Db.Prepare(sql)
	if err != nil {
		log.Fatal("pre select cart by user_id failed err = ", err)
		return nil, err
	}
	row := stmt.QueryRow(userId)
	cart := &model.Cart{
		CartId:      "",
		TotalCount:  0,
		TotalAmount: 0,
		UserId:      0,
	}
	err = row.Scan(&cart.CartId, &cart.TotalCount, &cart.TotalAmount, &cart.UserId)
	if err != nil {
		//log.Fatal("scan select cart by user_id failed")
		return nil, err
	}
	cartItems, _ := GetCartItemsByCartId(cart.CartId)
	cart.CartItems = cartItems
	return cart, nil
}
