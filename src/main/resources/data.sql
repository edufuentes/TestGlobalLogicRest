
/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('eduardo','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Eduardo', 'Rondon','profesor@google.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@google.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

/* TEST */
INSERT INTO user (CREATED,EMAIL, IS_ACTIVE,LAST_LOGIN,MODIFIED,NAME,PASSWORD )
VALUES ('2020-02-10', 'aaA2a2aaa23@hotmail.com', TRUE, '2020-02-10','2020-02-10','Alberto Matta','aaA2a2aaa1a');

INSERT INTO phone (CITY_CODE,COUNTRY_CODE,NUMBER,ID_USER)
VALUES ('1','69','55555555',1)