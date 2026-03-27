-- Seed data loaded by Spring Boot on startup.
-- Works with spring.jpa.hibernate.ddl-auto=create and H2 in-memory DB.

-- Insert persons first (T_PERSONS)
-- ID_PERSON is identity, so we omit it and let H2 generate it (1..N).
INSERT INTO T_PERSONS (NAME, LASTNAME, DNI) VALUES ('Ana', 'Garcia', '12345678Z');
INSERT INTO T_PERSONS (NAME, LASTNAME, DNI) VALUES ('Luis', 'Martinez', '87654321X');
INSERT INTO T_PERSONS (NAME, LASTNAME, DNI) VALUES ('Marta', 'Lopez', '11223344B');

-- Insert contacts (T_CONTACTS)
-- In your entity mapping:
--  - ID_PERSON is PK and FK -> T_PERSONS.ID_PERSON (shared primary key)
--  - DNI is FK -> T_PERSONS.DNI (unique)
--
-- We use subqueries to fetch the generated ID_PERSON values by DNI.

INSERT INTO T_CONTACTS (ID_PERSON, TELEPHONE, STREET, EMAIL, DNI)
VALUES (
  (SELECT ID_PERSON FROM T_PERSONS WHERE DNI = '12345678Z'),
  600111222,
  'Calle Mayor 1',
  'ana.garcia@example.com',
  '12345678Z'
);

INSERT INTO T_CONTACTS (ID_PERSON, TELEPHONE, STREET, EMAIL, DNI)
VALUES (
  (SELECT ID_PERSON FROM T_PERSONS WHERE DNI = '87654321X'),
  600333444,
  'Avenida del Sol 22',
  'luis.martinez@example.com',
  '87654321X'
);

INSERT INTO T_CONTACTS (ID_PERSON, TELEPHONE, STREET, EMAIL, DNI)
VALUES (
  (SELECT ID_PERSON FROM T_PERSONS WHERE DNI = '11223344B'),
  600555666,
  'Plaza España 3',
  'marta.lopez@example.com',
  '11223344B'
);
