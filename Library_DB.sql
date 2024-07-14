CREATE TABLE book (
    isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(255)
);

CREATE TABLE tags (
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(255)
);


CREATE TABLE book_tags (
	book_isbn VARCHAR(13),
    tag_id int,
	PRIMARY KEY(book_isbn,tag_id),
	FOREIGN KEY (book_isbn) REFERENCES book(isbn),
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id)
	   
);

CREATE INDEX idx_Book_Tags_BookISBN ON Book_Tags(BookISBN);
CREATE INDEX idx_Book_Tags_TagID ON Book_Tags(TagID);
CREATE INDEX idx_Tags_TagName ON Tags(TagName);



