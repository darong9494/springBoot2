insert into user_tb (username, password, email, created_at) values ('ssar', '$2y$04$0yDwb5VSijD7z8Wj3lFlwu50bcZRkUwqZQWekol9g.h1eCEto02VK', 'ssar@metacoding.com', now());
insert into user_tb (username, password, email, created_at) values ('cos', '$2y$04$0yDwb5VSijD7z8Wj3lFlwu50bcZRkUwqZQWekol9g.h1eCEto02VK', 'cos@metacoding.com', now());

insert into user_role_tb(user_id, role) values (1, 'USER');
insert into user_role_tb(user_id, role) values (2, 'USER');
insert into user_role_tb(user_id, role) values (2, 'ADMIN');

insert into board_tb (title, content, user_id,created_at) values ('title1', 'content1', 1,now());
insert into board_tb (title, content, user_id,created_at) values ('title2', 'content2', 1,now());
insert into board_tb (title, content, user_id,created_at) values ('title3', 'content3', 1,now());
insert into board_tb (title, content, user_id,created_at) values ('title4', 'content4', 2,now());
insert into board_tb (title, content, user_id,created_at) values ('title5', 'content5', 2,now());

insert into reply_tb (comment, board_id, user_id,created_at) values ('comment1', 4, 2,now());
insert into reply_tb (comment, board_id, user_id,created_at) values ('comment2', 4, 2,now());
insert into reply_tb (comment, board_id, user_id,created_at) values ('comment3', 4, 2,now());
insert into reply_tb (comment, board_id, user_id,created_at) values ('comment4', 5, 1,now());
insert into reply_tb (comment, board_id, user_id,created_at) values ('comment5', 5, 1,now());