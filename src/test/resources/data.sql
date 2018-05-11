SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE sb_user;
TRUNCATE TABLE customer;
TRUNCATE TABLE role;
TRUNCATE TABLE user_role;
COMMIT;

SET REFERENTIAL_INTEGRITY TRUE;
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO sb_user (id, username, email, enabled, confirmed, password) VALUES
  (1, 'cicciopasticcio', 'a@b.com', TRUE, TRUE, '$2a$10$B/dmAz4IXat0ieO0s6pRkuOY1kelPOn2YNLJqYfdqA96dof85Y9D2');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO customer (id, user_id) VALUES (1, 1);
COMMIT;
