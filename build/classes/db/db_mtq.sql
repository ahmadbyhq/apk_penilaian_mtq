CREATE TABLE users (
    id_user INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    role ENUM('panitia', 'juri') NOT NULL
);

CREATE TABLE lomba (
    id_lomba INT PRIMARY KEY AUTO_INCREMENT,
    nama_lomba VARCHAR(50) NOT NULL
);

CREATE TABLE peserta (
    id_peserta INT PRIMARY KEY AUTO_INCREMENT,
    nama_peserta VARCHAR(100) NOT NULL,
    asal VARCHAR(50) NOT NULL,
    id_lomba INT NOT NULL,
    FOREIGN KEY (id_lomba) REFERENCES lomba(id_lomba) ON DELETE CASCADE
);

CREATE TABLE aspek_penilaian (
    id_aspek INT PRIMARY KEY AUTO_INCREMENT,
    id_lomba INT NOT NULL,
    nama_aspek VARCHAR(50) NOT NULL,
    presentase INT NOT NULL CHECK (presentase >= 0 AND presentase <= 100),
    FOREIGN KEY (id_lomba) REFERENCES lomba(id_lomba) ON DELETE CASCADE
);

CREATE TABLE nilai (
    id_nilai INT PRIMARY KEY AUTO_INCREMENT,
    id_peserta INT NOT NULL,
    id_juri INT NOT NULL,
    id_lomba INT NOT NULL,
    id_aspek INT NOT NULL,
    skor INT NOT NULL CHECK (skor >= 0 AND skor <= 100),
    FOREIGN KEY (id_peserta) REFERENCES peserta(id_peserta) ON DELETE CASCADE,
    FOREIGN KEY (id_juri) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_lomba) REFERENCES lomba(id_lomba) ON DELETE CASCADE,
    FOREIGN KEY (id_aspek) REFERENCES aspek_penilaian(id_aspek) ON DELETE CASCADE
);



/*
INSERT INTO users (username, password, nama, role) VALUES
('panitia1', 'belanegara', 'Panitia', 'panitia'),
('juri1', 'mtqupnyjt', 'Juri1', 'juri'),
('juri2', 'mhqupnyjt', 'Juri2', 'juri'),
('juri3', 'mtqupnyjt', 'Juri3', 'juri'),
('juri4', 'mhqupnyjt', 'Juri4', 'juri');


INSERT INTO lomba (nama_lomba) VALUES
('MTQ Tartil'),
('MHQ 2 Juz');

INSERT INTO peserta (nama_peserta, asal, id_lomba) VALUES
('Ahmad','UPN Veteran Jawa Timur', 1),
('Muhammad', 'Universitas Airlangga', 2);


INSERT INTO aspek_penilaian (id_lomba, nama_aspek, presentase) VALUES
(2, 'Tajwid', 20),
(2, 'Fashahah', 20),
(2, 'Adab', 20),
(2, 'Suara dan Lagu', 15),
(2, 'Penguasaan Juz', 25),
(1, 'Tajwid', 30),
(1, 'Fashahah', 25),
(1, 'Suara dan Lagu', 30),
(1, 'Adab dan Penampilan', 15);

-- Nilai Juri 1(id_juri = 2), MTQ Tartil, Peserta Ahmad (id_peserta = 1)
INSERT INTO nilai (id_peserta, id_juri, id_lomba, id_aspek, skor) VALUES
(1, 2, 1, 6, 90), -- Tajwid
(1, 2, 1, 7, 85), -- Fashahah
(1, 2, 1, 8, 88), -- Suara dan Lagu
(1, 2, 1, 9, 87), -- Adab dan Penampilan

-- Nilai Juri 2 (id_juri = 3), MHQ 2 Juz, Peserta Muhammad (id_peserta = 2)
(2, 3, 2, 1, 92), -- Tajwid
(2, 3, 2, 2, 90), -- Fashahah
(2, 3, 2, 3, 88), -- Adab
(2, 3, 2, 4, 85), -- Suara dan Lagu
(2, 3, 2, 5, 95), -- Penguasaan Juz

-- Nilai Juri 3 (id_juri = 4), MTQ Tartil, Peserta Ahmad (id_peserta = 1)
(1, 4, 1, 6, 89), -- Tajwid
(1, 4, 1, 7, 86), -- Fashahah
(1, 4, 1, 8, 87), -- Suara dan Lagu
(1, 4, 1, 9, 90), -- Adab dan Penampilan

-- Nilai Juri 4 (id_juri = 5), MHQ 2 Juz, Peserta Muhammad (id_peserta = 2)
(2, 5, 2, 1, 90), -- Tajwid
(2, 5, 2, 2, 88), -- Fashahah
(2, 5, 2, 3, 89), -- Adab
(2, 5, 2, 4, 86), -- Suara dan Lagu
(2, 5, 2, 5, 93); -- Penguasaan Juz

*/

