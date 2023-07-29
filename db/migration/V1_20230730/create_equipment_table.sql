CREATE TABLE IF NOT EXISTS equipment (
     id INTEGER PRIMARY KEY,
     name VARCHAR(32) NOT NULL,
     well_id INTEGER,
     FOREIGN KEY (well_id) REFERENCES well (id)
)