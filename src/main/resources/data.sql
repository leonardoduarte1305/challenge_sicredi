insert into associado (id, nome) values (default, 'Obi Wan Kenobi');
insert into associado (id, nome) values (default, 'Darth Vader');
insert into associado (id, nome) values (default, 'Luke Skywalker');
insert into associado (id, nome) values (default, 'Leia Organa');
insert into associado (id, nome) values (default, 'Darth Sidious');

insert into pauta (id, nome_assembleia, temp_limite_votacao) values (default, 'Assembléia de Consórcio nº 1234', '2030-08-25T14:30:00');
insert into pauta (id, nome_assembleia, temp_limite_votacao) values (default, 'Assembléia de Consórcio nº 8080', '2030-07-24T14:30:00');
insert into pauta (id, nome_assembleia, temp_limite_votacao) values (default, 'Assembléia de Consórcio nº 443', '2030-06-23T14:30:00');

insert into pauta_associados (pauta_id, associados_id) values (1, 1);
insert into pauta_associados (pauta_id, associados_id) values (1, 2);
insert into pauta_associados (pauta_id, associados_id) values (1, 3);

insert into pauta_associados (pauta_id, associados_id) values (2, 1);
insert into pauta_associados (pauta_id, associados_id) values (2, 3);

insert into voto_na_pauta (voto, associado_id, pauta_id) values ('SIM', 1, 1);
insert into voto_na_pauta (voto, associado_id, pauta_id) values ('NAO', 2, 1);
insert into voto_na_pauta (voto, associado_id, pauta_id) values ('SIM', 3, 1);

insert into voto_na_pauta (voto, associado_id, pauta_id) values ('NAO', 1, 2);
insert into voto_na_pauta (voto, associado_id, pauta_id) values ('NAO', 2, 2);
