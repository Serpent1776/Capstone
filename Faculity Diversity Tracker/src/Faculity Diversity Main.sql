SET foreign_key_checks = 0;
truncate table eventCert;
truncate table facultyCert;
truncate table attendID;
truncate table facultyevent;
truncate table certificate;
truncate table faculty;
truncate table employment;
Set foreign_key_checks = 1;
/* Dummy data! */
insert into faculty (lastName, firstName, email, personsRole, dept_div, BIPOC, gender) 
values ("Weiss", "Scott", "sweiss@msmary.edu", "professor", "Computer science", false, "M");
/*update faculty
set lastname = "Weiss", firstname = "Scott"
where id = 1;*/
/*update faculty
set lastname = "Weiss", firstname = "Scott"
where id = 1;*/
insert into certificate (certificateName, certType) values ("Ethics", "Ethics");
insert into facultyEvent (eventName, eventDate, eventType, requirement, notes)
values ("The Corcerns of AI", "2024-12-01", "Ethics", "None", "");
insert into eventCert (certID, eventID) 
values ((select id from certificate), 
(select id from facultyevent));
insert into facultyCert (personID, bronzeDate, certID) 
values ((select id from faculty),"2024-01-18",
(select id from certificate));
insert into attendID (facultyID, eventID) 
values ((select id from faculty), (select id from facultyEvent));

