alter table medicos add ativo tinyint;
update medicos set medicos.ativo = 1 where medicos.ativo IS NULL;
