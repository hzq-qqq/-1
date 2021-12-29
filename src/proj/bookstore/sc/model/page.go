package model

type Page struct {
	Books       []*Book //每页的图书
	PageNo      int64   //当前也
	PageSize    int64   //每页显示条数
	TotalPageNo int64   //总页数
	TotalRecord int64   //总记录数
	MinPrice    string
	MaxPrice    string
	IsLogin     bool
	UserName    string
}

//是否有上一页
func (p Page) IsHasPrev() bool {
	return p.PageNo > 1
}

//是否有下一页
func (p Page) IsHasNext() bool {
	return p.PageNo < p.TotalPageNo
}
func (p Page) GetPrevPageNo() int64 {
	if p.IsHasPrev() {
		return p.PageNo - 1
	}
	return p.PageNo
}

func (p Page) GetNextPageNo() int64 {
	if p.IsHasNext() {
		return p.PageNo + 1
	}
	return p.PageNo
}
