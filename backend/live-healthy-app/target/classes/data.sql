insert into authority (id, name) values (1, "ROLE_REGISTERED");

INSERT INTO body_type (id, bmi, description, type) VALUES (1, 10, 'Ectomorphs are good at processing carbohydrates into energy', 1);

INSERT INTO user(id, age, email, enabled, first_name, height, last_name, last_password_reset_date, password, username, weight, body_type_id)
    VALUES (1, 22, 'ljubicjanko1@gmail.com', true, 'Janko', 188, 'Ljubic', '2020-01-01 01:01:01', '$2a$10$kmZD4NcJRD4wIE2tmudhAuZK05jTGMpTKBeEPQ8RNW52Fe6EeZMya', 'janko1janko01', 113, null);
    -- password User123!

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1)