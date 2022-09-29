
-- public.person definition

-- Drop table

-- DROP TABLE public.person;

CREATE TABLE public.person (
	person_id bigserial NOT NULL,
	dni int8 NOT NULL,
	first_name varchar(30) NOT NULL,
	last_name varchar(50) NOT NULL,
	phone int8 NOT NULL,
	CONSTRAINT person_pkey PRIMARY KEY (person_id),
	CONSTRAINT uk_elp5gmwf3ngplkew2g20s0nra UNIQUE (dni),
	CONSTRAINT uniquedni UNIQUE (dni)
);

-- public."role" definition

-- Drop table

-- DROP TABLE public."role";

CREATE TABLE public."role" (
	role_id bigserial NOT NULL,
	is_enabled bool NOT NULL,
	role_name varchar(255) NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (role_id),
	CONSTRAINT uniquerole UNIQUE (role_name)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	user_id bigserial NOT NULL,
	email varchar(255) NOT NULL,
	is_enabled bool NOT NULL,
	"password" varchar(255) NOT NULL,
	person_person_id int8 NULL,
	role_role_id int8 NULL,
	CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


-- public.users foreign keys

ALTER TABLE public.users ADD CONSTRAINT fk7dd36gc14q3fjywsnk9konknt FOREIGN KEY (role_role_id) REFERENCES public."role"(role_id) ON DELETE CASCADE;
ALTER TABLE public.users ADD CONSTRAINT fkacv9ad1mao3373q1tk339yi8h FOREIGN KEY (person_person_id) REFERENCES public.person(person_id) ON DELETE CASCADE;

-- public.company definition

-- Drop table

-- DROP TABLE public.company;

CREATE TABLE public.company (
	company_id bigserial NOT NULL,
	company_name varchar(255) NOT NULL,
	phone int8 NOT NULL,
	CONSTRAINT company_pkey PRIMARY KEY (company_id),
	CONSTRAINT uk_46jubpbtfae2gfb74a3x6qug7 UNIQUE (company_name),
	CONSTRAINT uniquecompanyname UNIQUE (company_name)
);

-- public.apartment definition

-- Drop table

-- DROP TABLE public.apartment;

CREATE TABLE public.apartment (
	apartment_id bigserial NOT NULL,
	building_name varchar(255) NOT NULL,
	apartment_number varchar(255) NOT NULL,
	CONSTRAINT apartment_pkey PRIMARY KEY (apartment_id),
	CONSTRAINT uniqueapartment UNIQUE (building_name, apartment_number)
);

-- public.resident definition

-- Drop table

-- DROP TABLE public.resident;

CREATE TABLE public.resident (
	resident_id bigserial NOT NULL,
	apartment_apartment_id int8 NULL,
	person_person_id int8 NULL,
	CONSTRAINT resident_pkey PRIMARY KEY (resident_id),
	CONSTRAINT uniqueperson UNIQUE (person_person_id)
);


-- public.resident foreign keys

ALTER TABLE public.resident ADD CONSTRAINT fk2ewofgh6ocd6wyswx3g38n5u2 FOREIGN KEY (person_person_id) REFERENCES public.person(person_id) ON DELETE CASCADE;
ALTER TABLE public.resident ADD CONSTRAINT fkqlpulw27fb4q5cj9ixscniwgx FOREIGN KEY (apartment_apartment_id) REFERENCES public.apartment(apartment_id) ON DELETE CASCADE;

-- public.security_guard definition

-- Drop table

-- DROP TABLE public.security_guard;

CREATE TABLE public.security_guard (
	security_guard_id bigserial NOT NULL,
	company_company_id int8 NULL,
	person_person_id int8 NULL,
	CONSTRAINT security_guard_pkey PRIMARY KEY (security_guard_id)
);


-- public.security_guard foreign keys

ALTER TABLE public.security_guard ADD CONSTRAINT fkbtulhgxbdf4o5uxj4hofk3o00 FOREIGN KEY (company_company_id) REFERENCES public.company(company_id) ON DELETE CASCADE;
ALTER TABLE public.security_guard ADD CONSTRAINT fkg0dsdd5117sqe6l5n88k186d7 FOREIGN KEY (person_person_id) REFERENCES public.person(person_id) ON DELETE CASCADE;


INSERT INTO public."role" (is_enabled, role_name) VALUES(true, 'ROLE_ADMIN');
INSERT INTO public."role" (is_enabled, role_name) VALUES(true, 'ROLE_RESIDENT');
INSERT INTO public."role" (is_enabled, role_name) VALUES(true, 'ROLE_GUARD');

INSERT INTO public.apartment (building_name, apartment_number) VALUES('A', '101');
INSERT INTO public.apartment (building_name, apartment_number) VALUES('A', '102');


