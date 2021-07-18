-- Creating the sequence for the account IDs. This is not automatically handled by Hibernate
create sequence account_sequence;

-- Inserting data
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99991','user1','password1','1');
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99992','user2','password2','2');
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99993','user3','password3','3');
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99994','user4','password4','4');
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99995','user5','password5','5');
insert into ACCOUNT(ID, USER_ID, PASSWORD, PERSON_ID) values('99996','user6','password6','6');
