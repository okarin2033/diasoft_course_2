INSERT INTO authors(name) VALUES
  ('Лев Толстой'),
  ('Фёдор Достоевский'),
  ('Александр Пушкин');

INSERT INTO genres(name) VALUES
  ('Роман'),
  ('Повесть'),
  ('Поэма');

INSERT INTO books(title, author_id, genre_id) VALUES
  ('Война и мир', 1, 1),
  ('Преступление и наказание', 2, 1),
  ('Евгений Онегин', 3, 3);

INSERT INTO book_comment(book_id, text) VALUES
  (1, 'Великолепная книга'),
  (2, 'Классика'),
  (3, 'Читается на одном дыхании');


