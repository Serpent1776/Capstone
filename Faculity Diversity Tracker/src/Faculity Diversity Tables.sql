create table certificate(
id integer primary key auto_increment,
certificateName varchar(200),
certType varchar(50)
);

create table employment(
id integer primary key auto_increment,
ApplicationID integer,
employmentDesc varchar(2000)
);

create table facultyEvent(
id integer primary key auto_increment,
eventName varchar(200),
eventDate date,
eventType varchar(50),
requirement varchar(200),
notes varchar(2000)
);
create table faculty(
id integer primary key auto_increment,
lastName varchar(200),
firstName varchar(200),
email varchar(200),
personsRole varchar(200),
dept_div varchar(200),
employmentID integer,
BIPOC boolean,
gender varchar(2), 
foreign key (employmentID) references employment(id)
);

create table facultyCert(
personID integer,
bronzeDate date,
silverDate date,
goldDate date,
certID integer,
foreign key (personID) references faculty(id),
foreign key (certID) references certificate(id)
);
create table eventCert(
certID integer,
eventID integer,
foreign key (certID) references certificate(id),
foreign key (eventID) references facultyEvent(id)
);

create table attendID(
attendID integer primary key auto_increment,
facultyID integer,
eventID integer,
foreign key (facultyID) references faculty(id),
foreign key (eventID) references facultyEvent(id)
);