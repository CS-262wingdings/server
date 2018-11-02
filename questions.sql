DROP TABLE IF EXISTS Question CASCADE;

CREATE TABLE Question (
	ID integer PRIMARY KEY,
	--Contents is the string of the question itself.
	contents varchar(200),
	--Timestamp lets us list the questions by new.
        author varchar(20),
	time timestamp,
	--Downloads tracks the popularity of a question based on how many people downloaded it.
	downloads integer,
	--ratings stores how many people have rated the question.
	ratings integer,
	--currentRating is the decimal rating of the question.
	currentRating real);

-- Make sure people can access table
GRANT SELECT ON Question TO PUBLIC;

INSERT INTO Question VALUES (
        DEFAULT,
        'On an average day, how many pigeons do you think you could reasonably carry?',
        'XxPigeonMaster99Xx',
        298,
        49,
        9.89,
        NOW());

SELECT * FROM Question;
