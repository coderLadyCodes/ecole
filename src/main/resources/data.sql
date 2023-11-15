INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (1,'student1','image1','2023-01-02',true, true);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (2,'student2','image2','2023-02-02',false, false);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (3,'student3','image3','2023-05-02',true, false);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (4,'student4','image4','2023-06-02',false, true);

INSERT INTO users (id,name, email,phone, password, profile_image,role, student) VALUES (1,'user1', 'user1@cool.fr','01010101010', '111111111', 'photo1',0, 1);
INSERT INTO users (id,name, email,phone, password, profile_image,role,student) VALUES (2,'user2', 'user2@cool.fr','02020202020', '222222222', 'photo2',1, 2);
INSERT INTO users (id,name, email,phone, password, profile_image,role, student) VALUES (3,'user3', 'user3@cool.fr','03030303030', '333333333', 'photo3',0, 3);
INSERT INTO users (id,name, email,phone, password, profile_image,role,student) VALUES (4,'user4', 'user4@cool.fr','04040404040', '444444444', 'photo4',1, 4);

INSERT INTO posts (id,title, post_content,image_post,local_date_time,user) VALUES (1,'title1','content1','imagepost1','2023-06-07 04:45',1);
INSERT INTO posts (id,title, post_content,image_post,local_date_time,user) VALUES (2,'title2','content2','imagepost2','2023-04-05 02:43',2);
INSERT INTO posts (id,title, post_content,image_post,local_date_time,user) VALUES (3,'title3','content3','imagepost3','2023-08-02 01:20',3);
INSERT INTO posts (id,title, post_content,image_post,local_date_time,user) VALUES (4,'title4','content4','imagepost4','2023-05-02 02:50',4);

INSERT INTO comments (id,comment_content, local_date_time,post, user) VALUES (1,'comment1', '2023-04-01 10:44:00', 1,1);
INSERT INTO comments (id,comment_content, local_date_time,post, user) VALUES (2,'comment2', '2023-05-02 11:22:00', 2,2);
INSERT INTO comments (id,comment_content, local_date_time,post, user) VALUES (3,'comment3', '2023-06-03 09:10:00', 3,3);
INSERT INTO comments (id,comment_content, local_date_time,post, user) VALUES (4,'comment4', '2023-07-04 08:40:00', 4,4);



