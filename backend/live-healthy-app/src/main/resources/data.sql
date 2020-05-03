INSERT INTO body_type (id, bmi, description, type) VALUES (1, 10, 'Ectomorphs are good at processing carbohydrates into energy', 1);

INSERT INTO user(id, age, email, enabled, first_name, height, last_name, last_password_reset_date, password, username, weight, body_type_id)
    VALUES (1, 22, 'ljubicjanko1@gmail.com', true, 'Janko', 188, 'Ljubic', 22/02/2020, 'sifraTrebaHes', 'janko_lj', 113, 1);