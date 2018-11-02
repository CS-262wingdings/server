DROP TABLE IF EXISTS Question CASCADE;

CREATE TABLE Question (
	ID integer PRIMARY KEY,
	--Contents is the string of the question itself.
	contents varchar(200),
	--Timestamp lets us list the questions by new.
	time timestamp,
	--Downloads tracks the popularity of a question based on how many people downloaded it.
	downlads integer,
	--ratings stores how many people have rated the question.
	ratings integer,
	--currentRating is the decimal rating of the question.
	currentRating real
	);