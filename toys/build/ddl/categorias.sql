insert into pub.categoria (id, descricao, ordinal, id_pai) values ('periodicidade', 'Periodicidades', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_DIARIO', 'Diário', 1, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_SEMANAL', 'Semanal', 2, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_QUINZENAL', 'Quinzenal', 3, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_MENSAL', 'Mensal', 4, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_BIMESTRAL', 'Bimestral', 5, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_TRIMESTRAL', 'Trimestral', 6, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_SEMESTRAL', 'Semestral', 7, 'periodicidade');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('PERIODICIDADE_ANUAL', 'Anual', 8, 'periodicidade');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('grauInstrucao', 'Grau de instrução', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_ANALFABETO', 'Analfabeto', 1, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_ATE_4_INCOMPLETO', 'Até 4ª série incompleta', 2, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_4_COMPLETO', '4ª série completa', 3, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_5_A_8', '5ª a 8ª série', 4, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_FUNDAMENTAL_COMPLETO', 'Ensino fundamental completo', 5, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_MEDIO_INCOMPLETO', 'Ensino médio incompleto', 6, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_MEDIO_COMPLETO', 'ensino médio completo', 7, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_SUPERIOR_INCOMPLETO', 'Ensino superior incompleto', 8, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_SUPERIOR_COMPLETO', 'Ensino superior completo', 9, 'grauInstrucao');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('GRAU_INSTRUCAO_POS_GRADUACAO', 'Pós-graduação', 10, 'grauInstrucao');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('necessidadesEspeciais', 'Nessecidades especiais', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('NECESSIDADE_ESPECIAL_VISUAL', 'Visual', 1, 'necessidadesEspeciais');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('NECESSIDADE_ESPECIAL_MOTORA', 'Motora', 2, 'necessidadesEspeciais');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('NECESSIDADE_ESPECIAL_AUDITIVA', 'Auditiva', 3, 'necessidadesEspeciais');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('NECESSIDADE_ESPECIAL_FISICA', 'Física', 4, 'necessidadesEspeciais');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('NECESSIDADE_ESPECIAL_MULTIPLA', 'Múltipla', 5, 'necessidadesEspeciais');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('turnos', 'Turnos', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TURNO_DIURNO', 'Diurno', 1, 'turnos');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TURNO_NOTURNO', 'Noturno', 2, 'turnos');
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TURNO_INTEGRAL', 'Integral', 3, 'turnos');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('origem', 'Origem geral de informações', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('origem.inscricao', 'Origem de inscrições', 0, 'origem');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('origem.inscricao.interno', 'Interno', 1, 'origem.inscricao');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('origem.inscricao.internet', 'Internet', 2, 'origem.inscricao');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ps', 'Processos Seletivos', 0, null);
	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ps.linguaEstrangeira', 'Línguas Estrangeiras', 0, 'ps');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ps.linguaEstrangeira.ingles', 'Inglês', 1, 'ps.linguaEstrangeira');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ps.linguaEstrangeira.espanhol', 'Espanhol', 2, 'ps.linguaEstrangeira');

insert into pub.categoria (id, descricao, ordinal, id_pai) values ('tipo', 'Tipos comuns', 0, null);

	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('tipo.endereco', 'Tipos de endereço', 0, 'tipo');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ENDERECO_RESIDENCIAL', 'Residencial', 1, 'tipo.endereco');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ENDERECO_COMERCIAL', 'Comercial', 2, 'tipo.endereco');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('ENDERECO_COBRANCA', 'Cobrança', 3, 'tipo.endereco');

	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('tipo.contato', 'Tipos de contato', 0, 'tipo');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_TELEFONE_RESIDENCIAL', 'Telefone residencial', 1, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_TELEFONE_COMERCIAL', 'Telefone comercial', 2, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_TELEFONE_CELULAR', 'Telefone celular', 3, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_EMAIL', 'E-mail', 4, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_MENSAGEM_INSTANTANEA', 'Mensagem instantânea', 5, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_FAX', 'Fax', 6, 'tipo.contato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('CONTATO_PAGER', 'Pager', 7, 'tipo.contato');

	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('tipo.curso', 'Tipos de cursos', 0, 'tipo');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TIPO_CURSO_GRADUACAO', 'Cursos de graduação', 1, 'tipo.curso');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TIPO_CURSO_POS_GRADUACAO', 'Cursos de pós-graduação', 2, 'tipo.curso');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TIPO_CURSO_CAPACITACAO', 'Cursos de capacitação', 3, 'tipo.curso');

	insert into pub.categoria (id, descricao, ordinal, id_pai) values ('tipo.candidato', 'Tipos de candidatos do processo seletivo', 0, 'tipo');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TIPO_CANDIDATO_REGULAR', 'Candidato regular', 1, 'tipo.candidato');
		insert into pub.categoria (id, descricao, ordinal, id_pai) values ('TIPO_CANDIDATO_TREINEIRO', 'Treineiro', 2, 'tipo.candidato');
