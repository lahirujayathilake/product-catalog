-- PRODUCT
INSERT INTO product(id, name, unit_price) VALUES('MBPRO-15', 'MacBook Pro 2015', 1989.50);
INSERT INTO product(id, name, unit_price) VALUES('MBPRO-14', 'MacBook Pro 2014', 1650.40);
INSERT INTO product(id, name, unit_price) VALUES('IPHONE-X', 'iPhoneX', 999.0);

-- CATEGORY
INSERT INTO category(id, name, description) VALUES('MAC', 'MacBook', 'MacBook Pro 2015, 2014');
INSERT INTO category(id, name, description) VALUES('IPHONE', 'iPhoneX', 'iPhoneX, 999USD');
INSERT INTO category(id, name, description) VALUES('SMARTPHONE', 'Smart Phone', 'Category of Smart Phones');

-- PRODUCT_CATEGORY
INSERT INTO product_category(product_id, category_id) VALUES('MBPRO-15', 'MAC');
INSERT INTO product_category(product_id, category_id) VALUES('MBPRO-14', 'MAC');
INSERT INTO product_category(product_id, category_id) VALUES('IPHONE-X', 'IPHONE');
INSERT INTO product_category(product_id, category_id) VALUES('IPHONE-X', 'SMARTPHONE');

-- ROLE
INSERT INTO role(id, role_name, description) VALUES (1, 'CREATE_CONTENT', 'Create Resources Rights');
INSERT INTO role(id, role_name, description) VALUES (2, 'UPDATE_CONTENT', 'Update Resources Rights');
INSERT INTO role(id, role_name, description) VALUES (3, 'DELETE_CONTENT', 'Delete Resource Rights');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO user(id, first_name, last_name, password, username) VALUES (1, 'User', 'Operator', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'user');
INSERT INTO user(id, first_name, last_name, password, username) VALUES (2, 'Admin', 'Admin', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'admin.admin');

-- USER_ROLE
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
INSERT INTO user_role(user_id, role_id) VALUES(2,3);
INSERT INTO user_role(user_id, role_id) VALUES(1,1);