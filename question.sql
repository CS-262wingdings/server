DROP TABLE IF EXISTS Question CASCADE;

CREATE TABLE Question (
    ID integer PRIMARY KEY,
    --Contents is the string of the question itself.
    contents varchar(200),
    --Timestamp lets us list the questions by new.
    time timestamp NOT NULL,
    --Downloads tracks the popularity of a question based on how many people downloaded it.
    downloads integer NOT NULL);

-- Make sure people can access table
GRANT SELECT ON Question TO PUBLIC;

INSERT INTO Question VALUES (
        1,
        'Sample question',
        NOW(),
        0);

SELECT * FROM Question;
