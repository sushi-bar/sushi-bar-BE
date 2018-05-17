SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE sb_user;
TRUNCATE TABLE customer;
TRUNCATE TABLE role;
TRUNCATE TABLE user_role;
TRUNCATE TABLE sb_order;
TRUNCATE TABLE menu;
TRUNCATE TABLE order_details;
COMMIT;

SET REFERENTIAL_INTEGRITY TRUE;
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO sb_user (id, username, email, enabled, confirmed, password)
VALUES (1, 'cicciopasticcio', 'a@b.com', TRUE, TRUE, '$2a$10$B/dmAz4IXat0ieO0s6pRkuOY1kelPOn2YNLJqYfdqA96dof85Y9D2');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO customer (id, user_id) VALUES (1, 1);
INSERT INTO sb_order (id, customer_id, time_order, status)
VALUES (1, 1, TO_DATE('2017-11-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'CREATED');
INSERT INTO sb_order (id, customer_id, time_order, status)
VALUES (2, 1, TO_DATE('2017-11-09 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'CANCELLED');
INSERT INTO menu (id, name, description, available) VALUES (1, 'COKE', 'Coca Cola 33cl', TRUE);
INSERT INTO menu (id, name, description, available) VALUES (2, 'SUSHI PARTY', '48 PEZZI. 12 Nigiri, 36 Hosomaki', TRUE);
INSERT INTO order_details (id, order_id, menu_id, quantity) VALUES (1, 1, 1, 2);
INSERT INTO order_details (id, order_id, menu_id, quantity) VALUES (2, 1, 2, 1);
COMMIT;

