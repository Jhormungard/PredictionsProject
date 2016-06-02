-- Nome do banco de dados para a parte em java: "Cotacoes"

-- Usuario generico:
CREATE USER alguem2 WITH PASSWORD '1234'
;

-- Embraer
CREATE TABLE public.EMBR3	(
		Date		DATE	NOT NULL PRIMARY KEY,
		Open		DOUBLE PRECISION,
		High		DOUBLE PRECISION,
		Low		DOUBLE PRECISION,
		Close		DOUBLE PRECISION,
		Volume		INTEGER, /*BIGINT equivalent to long int,*/
		Adj_Clo		DOUBLE PRECISION	)
;

-- Ambev
CREATE TABLE public.ABEV3	(
		Date		DATE	NOT NULL PRIMARY KEY,
		Open		DOUBLE PRECISION,
		High		DOUBLE PRECISION,
		Low		DOUBLE PRECISION,
		Close		DOUBLE PRECISION,
		Volume		INTEGER, /*BIGINT equivalent to long int,*/
		Adj_Clo		DOUBLE PRECISION	)
;

-- Petrobras
CREATE TABLE public.PETR4	(
		Date		DATE	NOT NULL PRIMARY KEY,
		Open		DOUBLE PRECISION,
		High		DOUBLE PRECISION,
		Low		DOUBLE PRECISION,
		Close		DOUBLE PRECISION,
		Volume		INTEGER, /*BIGINT equivalent to long int,*/
		Adj_Clo		DOUBLE PRECISION	)
;

-- Vale do Rio Doce
CREATE TABLE public.VALE5	(
		Date		DATE	NOT NULL PRIMARY KEY,
		Open		DOUBLE PRECISION,
		High		DOUBLE PRECISION,
		Low		DOUBLE PRECISION,
		Close		DOUBLE PRECISION,
		Volume		INTEGER, /*BIGINT equivalent to long int,*/
		Adj_Clo		DOUBLE PRECISION	)
;
