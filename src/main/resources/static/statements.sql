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
	CIN VARCHAR(30),
	PRIMARY KEY(id)
)ENGINE=InnoDB;

CREATE TABLE return_credentials(
	return_id int not null AUTO_INCREMENT,
	id int not null,
	return_type VARCHAR(10) not null,
	GST_no VARCHAR(20),
	TAN_no VARCHAR(20),
	flat_no VARCHAR(50),
	area VARCHAR(50),
	city VARCHAR(50),
	state VARCHAR(50),
	PIN VARCHAR(10),
	user_id VARCHAR(50) not null,
	password VARCHAR(50) not null,
	traces_user_id VARCHAR(50),
	traces_password VARCHAR(50),
	PRIMARY KEY(return_id),
	FOREIGN KEY(id) REFERENCES client_credentials(id)
)ENGINE=InnoDB;

CREATE TABLE login(
	login_id int not null AUTO_INCREMENT,
	user_id VARCHAR(50) not null,
	password VARCHAR(50) not null,
	PRIMARY KEY(login_id)
)ENGINE=InnoDB;

CREATE TABLE return_forms(
	form_id int not null AUTO_INCREMENT,
	form_name VARCHAR(50) not null,
	return_type VARCHAR(10) not null,
	due_date_of_filing DATETIME not null,
	periodicity VARCHAR(20) not null,
	UNIQUE INDEX(form_name),
	INDEX(return_type),
	PRIMARY KEY(form_id)
)ENGINE=InnoDB;