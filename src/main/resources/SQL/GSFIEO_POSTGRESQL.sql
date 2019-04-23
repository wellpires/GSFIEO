--PERMISSOES PAI DA TABELA USUARIO
--> PERMISSOES: Administrador, Moderador e Padrão
CREATE TABLE PERMISSAO (
		id_permissao SERIAL PRIMARY KEY,
		permissao VARCHAR (20) not null,
		status VARCHAR(1) -- STATUS: 0 = Desativado; 1 = Ativado //REVISAR, POSSIVEL REMOCAO DE CAMPO
		);
		


-- Usuarios RELACIONADA COM TABELA 'PERMISSOES'
CREATE TABLE LOGIN (
		id_login SERIAL PRIMARY KEY,
		usuario VARCHAR(50) UNIQUE,
		senha VARCHAR(16),
		id_permissao_FK INT,
		FOREIGN KEY (id_permissao_FK) REFERENCES permissao(id_permissao)
		);

-- CRIACAO TABELA TIPO_SERVICO 
-- TIPO SERVICO: Lanchonetes, Papelaria
CREATE TABLE TIPO_SERVICO (
		id_tipo_serv SERIAL PRIMARY KEY,
		tipo_serv_desc VARCHAR (70) NOT NULL,
		status VARCHAR(1) );
		
-- CRIACAO TABELA SERVICO
-- SERVICO: Lanchonete Jailson Mendes

CREATE TABLE SERVICO (
		id_serv SERIAL PRIMARY KEY,
		serv_desc VARCHAR (70) NOT NULL,
		status VARCHAR(1),
		id_tipo_serv_FK INT  NOT NULL,
		FOREIGN KEY (id_tipo_serv_FK) REFERENCES TIPO_SERVICO(id_tipo_serv) ON DELETE CASCADE ON UPDATE CASCADE,
		id_login_FK INT NOT NULL,
		FOREIGN KEY (id_login_FK) REFERENCES LOGIN(id_login) ON DELETE CASCADE ON UPDATE CASCADE);


-- CRIACAO TABELA TIPO_PRODUTO
-- TIPOS: Comes/Bebes, Papelaria etc
CREATE TABLE TIPO_PRODUTO (
		id_tipo_produto SERIAL PRIMARY KEY, 
		tipo_produto_desc VARCHAR (20) NOT NULL );


-- CRIACAO TABELA PRODUTO
-- PRODUTO -> Comidas: Hamburgão, Pão de batata
CREATE TABLE PRODUTO (
		id_produto SERIAL NOT NULL PRIMARY KEY,
		produto_desc VARCHAR (50) NOT NULL,
		id_tipo_produto_FK INT,
		valor_unitario DECIMAL(5,2) DEFAULT(0.00),
		FOREIGN KEY(id_tipo_produto_FK) REFERENCES tipo_produto(id_tipo_produto),
		id_serv_FK INT, 
		FOREIGN KEY(id_serv_FK) REFERENCES servico(id_serv)    ON DELETE CASCADE ON UPDATE CASCADE );

	
CREATE TABLE ESTOQUE(
		id_estoque SERIAL PRIMARY KEY,
		id_serv_FK INT NOT NULL,
	    FOREIGN KEY (id_serv_FK) REFERENCES servico(id_serv) ON DELETE CASCADE ON UPDATE CASCADE );
		

		
--> GRAVAR PRODUTO versus ESTOQUE
CREATE TABLE ESTOQUEXPRODUTO(
		id_estoque_x_produto SERIAL PRIMARY KEY,
		qtdEstoque INT DEFAULT(0),
		qtdMinima INT DEFAULT(0),
		qtdMaxima INT DEFAULT(0),
		id_produto_FK INT NOT NULL,
		FOREIGN KEY (id_produto_FK) REFERENCES produto(id_produto) ON DELETE NO ACTION ON UPDATE NO ACTION,
		id_estoque_FK INT NOT NULL,
		FOREIGN KEY (id_estoque_FK) REFERENCES estoque(id_estoque) ON DELETE CASCADE ON UPDATE CASCADE  );

		
--> TABELA 'FORMA DE PAGTO'
CREATE TABLE FORMA_PAGAMENTO(
		id_forma_pagamento SERIAL PRIMARY KEY,
		forma_pagamento VARCHAR(20),
		CHECK (forma_pagamento = 'DINHEIRO' OR forma_pagamento = 'CARTAO_DE_DEBITO' OR forma_pagamento = 'CARTAO_DE_CREDITO') 
);
		

--> TABELA 'STATUS'
CREATE TABLE STATUS(
		id_status SERIAL PRIMARY KEY,
		status VARCHAR(13),
		CHECK (id_status = 0 OR id_status = 1 OR id_status = 2),
		CHECK (status = 'APROVADO' OR status = 'REPROVADO' OR status = 'PRE_APROVADO')
);


-- TABELA 'COMANDA'
CREATE TABLE COMANDA(
		id_comanda VARCHAR(11) NOT NULL PRIMARY KEY,
		totalCompra DECIMAL(5,2),
		qtdTotal INT,
		dataVenda VARCHAR(11),
		dataConfirmacao VARCHAR(11),
		id_forma_pagamento_FK INT NOT NULL,
	    FOREIGN KEY (id_forma_pagamento_FK) REFERENCES FORMA_PAGAMENTO(id_forma_pagamento) ON DELETE CASCADE ON UPDATE CASCADE,
		id_serv_FK INT NOT NULL,
		FOREIGN KEY (id_serv_FK) REFERENCES servico(id_serv) ON DELETE CASCADE ON UPDATE CASCADE,
		id_login_FK INT NOT NULL,
		FOREIGN KEY (id_login_FK) REFERENCES Login(id_login) ON DELETE NO ACTION ON UPDATE NO ACTION,
		id_status_FK INT NOT NULL,
	    FOREIGN KEY (id_status_FK) REFERENCES Status(id_status) ON DELETE CASCADE ON UPDATE CASCADE );
		
		

-- TABELA 'PEDIDOS'
CREATE TABLE PEDIDOS(
		id_pedido SERIAL PRIMARY KEY,
		qtd INT,
		subTotal DECIMAL(5,2),
		id_produto_FK INT NOT NULL,
		FOREIGN KEY (id_produto_FK) REFERENCES PRODUTO(id_produto) ON DELETE CASCADE ON UPDATE CASCADE,
		id_comanda_FK VARCHAR(11) NOT NULL,
		FOREIGN KEY (id_comanda_FK) REFERENCES COMANDA(id_comanda) ON DELETE NO ACTION ON UPDATE NO ACTION );
		
		
INSERT INTO permissao (permissao, status) VALUES('ADMINISTRADOR', '1');
INSERT INTO permissao (permissao, status) VALUES('MODERADOR', '1');
INSERT INTO permissao (permissao, status) VALUES('PADRÃO', '1');

--> INSERIR LOGIN
												
INSERT INTO LOGIN(usuario,senha,id_permissao_FK) VALUES('ADM','123',1);

		
---> INSERIR AS FORMAS DE PAGAMENTO
INSERT INTO FORMA_PAGAMENTO (forma_pagamento) VALUES('DINHEIRO');
INSERT INTO FORMA_PAGAMENTO (forma_pagamento) VALUES('CARTAO_DE_DEBITO');
INSERT INTO FORMA_PAGAMENTO (forma_pagamento) VALUES('CARTAO_DE_CREDITO');

--> INSERT TABELA STATUS

INSERT INTO status VALUES(0,'APROVADO');
INSERT INTO status VALUES (1,'REPROVADO');
INSERT INTO status VALUES (2,'PRE_APROVADO');

--  STORAGE PROCEDURE
--> DÁ BAIXA NO ESTOQUE E NA TABELA PRODUTOS
CREATE PROCEDURE SP_DA_BAIXA_ESTOQUE(qtd integer, servico_desc varchar, produto_desc varchar)
LANGUAGE SQL
AS $$
	UPDATE ESTOQUEXPRODUTO SET qtdEstoque = qtdEstoque - qtd WHERE id_estoque_FK = (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = servico_desc)) AND id_produto_FK = (SELECT P.id_produto FROM PRODUTO P WHERE P.produto_desc = produto_desc)
$$;