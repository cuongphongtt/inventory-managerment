insert into menu(parent_id,url,name,order_index) values (0,'/product','Sản phẩm',1),
(0,'/stock','Kho',2),
(0,'/management','Quản lý',3),
(1,'/product-info/list','Danh sách sản phẩm',2),
(1,'/category/list','Danh sách category',1),
(1,'/category/edit','Sửa',-1),
(1,'/category/view','Xem',-1),
(1,'/category/add','Thêm mới',-1),
(1,'/category/save','Lưu',-1),
(1,'/category/delete','Xoá',-1),

(1,'/product-info/edit','Sửa',-1),
(1,'/product-info/view','Xem',-1),
(1,'/product-info/add','Thêm mới',-1),
(1,'/product-info/save','Lưu',-1),
(1,'/product-info/delete','Xoá',-1),

(2,'/goods-receipt/list','Danh sách nhập kho',1),
(2,'/goods-receipt/view','Xem',-1),
(2,'/goods-receipt/add','Thêm mới',-1),
(2,'/goods-receipt/save','Lưu',-1),
(2,'/goods-receipt/export','Xuất báo cáo',-1),

(2,'/goods-issue/list','Danh sách xuất kho',2),
(2,'/product-in-stock/list','Sản phẩm trong kho',3),
(2,'/history','Lịch sử kho',4),

(3,'/user/list','Danh sách user',1),
(3,'/menu/list','Danh sách menu',2),
(3,'/role/list','Danh sách quyền',3)

(3, '/user/save', 'Save', -1),
(3, '/user/edit', 'Edit', -1),
(3, '/user/view', 'View', -1),
(3, '/user/add', 'Add', -1),

(3, '/role/save', 'Save', -1),
(3, '/role/edit', 'Edit', -1),
(3, '/role/view', 'View', -1),
(3, '/role/add', 'Add', -1)
