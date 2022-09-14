ALTER TABLE rents
ADD CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE rents
ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients (id);