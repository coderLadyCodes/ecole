INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (1,'student1','image1','2023-01-02',true, true);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (2,'student2','image2','2023-02-02',false, false);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (3,'student3','image3','2023-05-02',true, false);
INSERT INTO students (id,name, profile_image,birthday,presence,cantine) VALUES (4,'student4','image4','2023-06-02',false, true);

INSERT INTO posts (id,title, post_content,image_post,local_date_time) VALUES (1,'title1','content1','imagepost1','2023-06-04 04:45');
INSERT INTO posts (id,title, post_content,image_post,local_date_time) VALUES (2,'title2','content2','imagepost2','2023-04-05 02:43');
INSERT INTO posts (id,title, post_content,image_post,local_date_time) VALUES (3,'title3','content3','imagepost3','2023-08-02 01:20');
INSERT INTO posts (id,title, post_content,image_post,local_date_time) VALUES (4,'title4','content4','imagepost4','2023-05-02 02:50');

INSERT INTO comments (id,comment_content, local_date_time) VALUES (1,'comment1', '2023-04-01 10:44:00');
INSERT INTO comments (id,comment_content, local_date_time) VALUES (2,'comment2', '2023-05-02 11:22:00');
INSERT INTO comments (id,comment_content, local_date_time) VALUES (3,'comment3', '2023-06-03 09:10:00');
INSERT INTO comments (id,comment_content, local_date_time) VALUES (4,'comment4', '2023-07-04 08:40:00');

INSERT INTO users (id, name, email,phone, password, profile_image, role, student) VALUES (1,'user1', 'usero@cool.fr','01010101010', 'password1', 'photo1', 1, 1);
INSERT INTO users (id, name, email,phone, password, profile_image, role, student) VALUES (2,'user2', 'usert@cool.fr','02020202020',, 'password2', 'photo2', 1, 2);
INSERT INTO users (id, name, email,phone, password, profile_image, role, student) VALUES (3,'user3', 'usertr@cool.fr','03030303030',, 'password3', 'photo3', 1, 3);
INSERT INTO users (id, name, email,phone, password, profile_image, role, student) VALUES (4,'user4', 'userfr@cool.fr','04040404040',, 'password4', 'photo4', 1, 4);

