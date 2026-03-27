select * from faculty where firstName = "Scott" and lastName = "Weiss";
select certificateName from certificate join facultyCert on id = certID where personID = 1;
select eventName from facultyEvent join attendid on id = eventID where facultyID = 1;
select * from facultyevent where eventName = "The Corcerns of AI";