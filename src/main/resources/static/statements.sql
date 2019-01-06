CREATE table client_credentials(
	id int not null AUTO_INCREMENT,
	name VARCHAR(50) not null,
	flat_no VARCHAR(50) not null,
	area VARCHAR(50) not null,
	city VARCHAR(50) not null,
	state VARCHAR(50) not null,
	PIN VARCHAR(10) not null,
	client_type VARCHAR(50) not null,
	mobile VARCHAR(20) not null,
	client_email_id VARCHAR(50) not null,
	PAN VARCHAR(20) not null,
	DOB_or_DOI VARCHAR(50) not null,
	responsible_person_name VARCHAR(50),
	responsible_person_PAN VARCHAR(20),
	responsible_person_DOB VARCHAR(50),
	responsible_person_aadhaar VARCHAR(20),
	PRIMARY KEY(id)
)ENGINE=InnoDB;