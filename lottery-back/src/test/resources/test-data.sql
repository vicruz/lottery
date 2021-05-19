INSERT INTO USER (PROVIDER_USER_ID, email, enabled, DISPLAY_NAME, created_date, modified_date, provider, password) 
	VALUES (null, 'vic@vic.com', 1, 'Victor', now(), now(), 'local', 'pass');
INSERT INTO USER (PROVIDER_USER_ID, email, enabled, DISPLAY_NAME, created_date, modified_date, provider, password) 
	VALUES (null, 'ivan@vic.com', 1, 'Ivan', now(), now(), 'local', 'pass');
INSERT INTO USER (PROVIDER_USER_ID, email, enabled, DISPLAY_NAME, created_date, modified_date, provider, password) 
	VALUES (null, 'cruz@vic.com', 1, 'Cruz', now(), now(), 'local', 'pass');
	
INSERT INTO ROLE (name) values ('USER');
INSERT INTO ROLE (name) values ('ADMIN');