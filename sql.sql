create database infamus;

use infamus;

CREATE TABLE professor (
   codigo_professor INT AUTO_INCREMENT PRIMARY KEY,
   nome_professor VARCHAR(255) NOT NULL,
   email_professor VARCHAR(255) UNIQUE NOT NULL,
   senha_professor VARCHAR(255) NOT NULL
);

CREATE TABLE estudante (
   codigo_estudante INT AUTO_INCREMENT PRIMARY KEY,
   matricula_estudante VARCHAR(20) UNIQUE NOT NULL,
   nome_estudante VARCHAR(255) NOT NULL,
   endereco_estudante VARCHAR(255),
   telefone_estudante VARCHAR(20),
   email_estudante VARCHAR(255) UNIQUE NOT NULL,
   ano_entrada INT NOT NULL
);

CREATE TABLE relato (
    codigo_relato INT AUTO_INCREMENT PRIMARY KEY,
    data_relato DATE NOT NULL,
    descricao TEXT NOT NULL,
    codigo_fk_estudante INT NOT NULL,
    FOREIGN KEY (codigo_fk_estudante) REFERENCES estudante(codigo_estudante) ON DELETE CASCADE,
    FOREIGN KEY (codigo_fk_professor) REFERENCES professor(codigo_professor) ON DELETE CASCADE
);