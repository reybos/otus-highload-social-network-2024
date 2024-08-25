--liquibase formatted sql

--changeset reybos:create_test_table
CREATE TABLE test
(
    id   bigint NOT NULL PRIMARY KEY,
    data text   NOT NULL
);

SELECT create_distributed_table('test', 'id');

insert into test(id, data)
select
    i,
    md5(random()::text)
from generate_series(1, 10000) as i;