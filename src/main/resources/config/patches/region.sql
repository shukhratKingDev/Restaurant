INSERT INTO "region" (id,code,name_ru,name_uz) VALUES
(gen_random_uuid(),3, 'Андижанская Область', 'Andijon Viloyati'),
(gen_random_uuid(),6, 'Бухарская Область', 'Buxoro Viloyati'),
(gen_random_uuid(),8, 'Джизакская Область', 'Jizzax Viloyati'),
(gen_random_uuid(),10, 'Кашкадарьинская Область', 'Qashqadaryo Viloyati'),
(gen_random_uuid(),12, 'Навоийская Область', 'Navoiy Viloyati'),
(gen_random_uuid(),14, 'Наманганская Область', 'Namangan Viloyati'),
(gen_random_uuid(),18, 'Самаркандская Область', 'Samarqand Viloyati'),
(gen_random_uuid(),22, 'Сурхандарьинская Область', 'Surxondaryo Viloyati'),
(gen_random_uuid(),24, 'Сырдарьинская Область', 'Sirdaryo Viloyati'),
(gen_random_uuid(),26, 'Город Ташкент', 'Toshkent Shahar'),
(gen_random_uuid(),27, 'Ташкентская Область', 'Toshkent Viloyati'),
(gen_random_uuid(),30, 'Ферганская Область', 'Fargona Viloyati'),
(gen_random_uuid(),33, 'Хорезмская Область', 'Xorazm Viloyati'),
(gen_random_uuid(),35, 'Респ. Каракалпакстан', 'Qoraqalpogiston Resp.');


--set chart codes

update region set chart_code = 'uz-ta' where region.code=27;
update region set chart_code = 'uz-qa' where region.code=10;
update region set chart_code = 'uz-sa' where region.code=18;
update region set chart_code = 'uz-qr' where region.code=35;
update region set chart_code = 'uz-fa' where region.code=30;
update region set chart_code = 'uz-si' where region.code=24;
update region set chart_code = 'uz-bu' where region.code=6;
update region set chart_code = 'uz-ji' where region.code=8;
update region set chart_code = 'uz-su' where region.code=22;
update region set chart_code = 'uz-kh' where region.code=33;
update region set chart_code = 'uz-ng' where region.code=14;
update region set chart_code = 'uz-nw' where region.code=12;
update region set chart_code = 'uz-tk' where region.code=26;
update region set chart_code = 'uz-an' where region.code=3;
