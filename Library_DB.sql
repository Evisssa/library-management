/*
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


 */



CREATE INDEX idx_book_tags_book_isbn_tag_id ON book_tags(book_isbn, tag_id);
CREATE INDEX idx_tags_tag_name ON tag(tag_name);
CREATE INDEX idx_book_tags_tag_id_book_isbn ON book_tags(tag_id, book_isbn);



