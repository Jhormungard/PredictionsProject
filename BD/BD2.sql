-- permitir que o usuário faça as alteracoes no banco de dados:

GRANT SELECT ON ALL TABLES IN SCHEMA public TO alguem2
;


-- copia as tuplas do arquivo CSV no windows

COPY public.EMBR3 (	Date,
			Open,
			High,
			Low,
			Close,
			Volume,
			Adj_Clo		)
	FROM 'C:\table_embr3.csv'
	WITH CSV HEADER DELIMITER ','
;


COPY public.ABEV3 (	Date,
			Open,
			High,
			Low,
			Close,
			Volume,
			Adj_Clo		)
	FROM 'C:\table_abev3.csv'
	WITH CSV HEADER DELIMITER ','
;

COPY public.PETR4 (	Date,
			Open,
			High,
			Low,
			Close,
			Volume,
			Adj_Clo		)
	FROM 'C:\table_petr4.csv'
	WITH CSV HEADER DELIMITER ','
;

COPY public.VALE5 (	Date,
			Open,
			High,
			Low,
			Close,
			Volume,
			Adj_Clo		)
	FROM 'C:\table_vale5.csv'
	WITH CSV HEADER DELIMITER ','
;
