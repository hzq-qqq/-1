package model

type CartItem struct {
	CartItemId int64   //购物项ID
	Book       *Book   //购物项中的图书信息
	Count      int64   //购物项中的图书信息
	Amount     float64 //购物车中图书的总价格
	CartId     string
}

//获取当前购物物品总价格
func (c *CartItem) GetAmount() float64 {
	price := c.Book.Price
	return float64(c.Count) * price
}
