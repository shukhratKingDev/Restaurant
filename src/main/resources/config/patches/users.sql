insert  into users (id,login,password_hash,first_name,last_name,phone)
values(gen_random_uuid()::varchar ,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','998909014458');

insert  into users (id,login,password_hash,first_name,last_name,phone)
values(gen_random_uuid()::varchar,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','998997814721');
