CREATE TABLE USERS
(
  "Login" VARCHAR2(150),
  "Password" VARCHAR2(150),
  "Authorization_Level" NUMERIC
);

ALTER TABLE
  USERS
ADD CONSTRAINT
  users_pk
PRIMARY KEY
  ("Login");

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Valentin", "Valentin", 1);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Anges", "Anges", 1);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Mohammed", "Mohammed", 1);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Lynda", "Lynda", 2);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Boubacar", "Boubacar", 2);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("Marc", "Marc", 3);

INSERT INTO
  USERS("Login", "Password", "Authorization_Level")
VALUES
  ("DSI", "DSI", 4);


