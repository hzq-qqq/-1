package model

type Cart struct {
	CartId      string      // 购物车ID
	CartItems   []*CartItem //购物车中所有的购物项
	TotalCount  int64       //购物车中图书的总数量，
	TotalAmount float64     //购物车总金额
	UserId      int         // 当前购物车所属用户
}

//得到购物车中图书总金额
func (c *Cart) GetTotalAmount() float64 {
	var totalAmount float64
	for _, v := range c.CartItems {
		totalAmount += v.Amount
	}
	return totalAmount
}

//得到购物车中图书总数量
func (c *Cart) GetTotalCount() int64 {
	var totalCount int64
	for _, v := range c.CartItems {
		totalCount += v.Count
	}
	return totalCount
}
