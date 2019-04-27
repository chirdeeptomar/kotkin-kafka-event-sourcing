CREATE TABLE IF NOT EXISTS Trade (
  id varchar,
  trade_date timestamp,
  trade_by varchar,
  currency varchar,
  trade_amount decimal,
  PRIMARY KEY (id)
) WITH "template=partitioned,backups=1";