CREATE table client_credentials(
	id int not null AUTO_INCREMENT,
	name VARCHAR(50) not null,
	client_code VARCHAR(50) not null,
	father_name VARCHAR(100) not null,
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
	UNIQUE INDEX(client_code),
	PRIMARY KEY(id)
)ENGINE=InnoDB;

CREATE TABLE return_credentials(
	return_id int not null AUTO_INCREMENT,
	id int not null,
	assessment_year VARCHAR(15) not null,
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
	ON UPDATE CASCADE ON DELETE CASCADE
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
	periodicity VARCHAR(20) not null,
	monthly_day_occurrence int,
	yearly_month_occurrence int,
	yearly_day_occurrence int,
	first_quarter_month_occurrence int,
	first_quarter_day_occurrence int,
	second_quarter_month_occurrence int,
	second_quarter_day_occurrence int,
	third_quarter_month_occurrence int,
	third_quarter_day_occurrence int,
	fourth_quarter_month_occurrence int,
	fourth_quarter_day_occurrence int,
	UNIQUE INDEX(form_name),
	INDEX(return_type),
	PRIMARY KEY(form_id)
)ENGINE=InnoDB;

CREATE TABLE client_return_form_applicability(
    id int NOT NULL AUTO_INCREMENT,
    form_name VARCHAR(50) NOT NULL,
    assessment_year VARCHAR(15) NOT NULL,
    return_credentials_id INT NOT NULL,
    acknowledgement_no VARCHAR(20),
    date_of_filing DATETIME,
    date_of_physical_deposit DATETIME,
    INDEX(form_name),
    INDEX(assessment_year),
    FOREIGN KEY(form_name) REFERENCES return_forms(form_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(return_credentials_id) REFERENCES return_credentials(return_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(id),
    UNIQUE INDEX(form_name, assessment_year, return_credentials_id)
)ENGINE=InnoDB;

CREATE TABLE due_date_scheduler(
    id int NOT NULL AUTO_INCREMENT,
    form_name VARCHAR(50) NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    due_date_of_filing DATE,
    revised_due_date_of_filing DATE,
    to_be_delete INT NOT NULL default 0,
    PRIMARY KEY(id),
    FOREIGN KEY(form_name) REFERENCES return_forms(form_name)
    ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB;