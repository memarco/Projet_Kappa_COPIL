/* Employees : now points to the Users table instead of having login inormation within */
ALTER TABLE EMPLOYEES
DROP COLUMN LOGIN;
ALTER TABLE EMPLOYEES
DROP COLUMN PASSWORD;
ALTER TABLE EMPLOYEES
ADD (User_Login VARCHAR2(150)); 
ALTER TABLE EMPLOYEES
ADD CONSTRAINT employees_users_fk
FOREIGN KEY (User_Login) REFERENCES Users("Login");

/* Loans : now has a name, and an insurance parameter */
ALTER TABLE LOANS
ADD (Name VARCHAR2(150));
ALTER TABLE LOANS
ADD (Insurance FLOAT);

/* Customers : now points to the Users table, as well as to their advisor */
ALTER TABLE CUSTOMERS
ADD (Advisor_Id VARCHAR2(150));
/*ALTER TABLE EMPLOYEES
ADD CONSTRAINT EMPLOYEE_PK
PRIMARY KEY (EMPLOYEE_ID);*/ /* My table didn't have a primary key constraint on employees, check if yous does */
ALTER TABLE CUSTOMERS
ADD CONSTRAINT customers_advisors_fk
FOREIGN KEY (Advisor_Id) REFERENCES EMPLOYEES("EMPLOYEE_ID");
ALTER TABLE CUSTOMERS
ADD (User_login VARCHAR2(150));
ALTER TABLE CUSTOMERS
ADD CONSTRAINT customers_users_fk
FOREIGN KEY (User_login) REFERENCES Users("Login");

/* REPAYMENTS : changed the type for the id field */
DROP TABLE REPAYMENTS;
CREATE TABLE REPAYMENTS
(
  "REPAYMENT_ID" VARCHAR2(10) PRIMARY KEY,
  "Loan_Id" VARCHAR2(2),
  "Date" DATE NOT NULL,
  "CAPITAL" FLOAT NOT NULL,
  "INTEREST" FLOAT NOT NULL,
  "INSURANCE" FLOAT NOT NULL
);
ALTER TABLE REPAYMENTS
ADD CONSTRAINT repayment_loan_fk
FOREIGN KEY (Loan_Id) REFERENCES Loans("LOAN_ID");