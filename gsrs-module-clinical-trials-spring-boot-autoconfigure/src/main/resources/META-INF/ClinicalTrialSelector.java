

Had wierd issue with string conversion and maybe circular references with lombok.
Adding @ToString.Exclude above clinicalTrialDrug declaration helped. 



delete from CT_clinical_trial;
delete from CT_clinical_trial_drug;

curl -X GET http://localhost:8080/api/v2/clinicaltrial/search


# incase it exists. 

curl -X DELETE http://localhost:8080/api/v2/clinicaltrial/NCT99999991


curl -X POST -d '{"trialNumber":"NCT99999991", "title":"xyz"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial

curl -X POST -d '{"trialNumber":"NCT99999991", "title":"xyzb"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial

# Danny it seems a POST is allowed to update data (Should this not fail?).


curl -X PUT -d '{"trialNumber":"NCT99999991", "title":"xyzc"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial

# Danny it seems that internal version is not checked. I can PUT without specifying the internalVersion
# Danny it seems that if I PUT and the creation date is missing, there is no error.  Should there be a check in the starter?  

curl -X PUT -d '{"trialNumber":"NCT99999991", "title":"xyzd"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial


/// ======= ////

$ curl -X POST -d '{"trialNumber":"NCT99999991", "title":"xyz"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   288    0   244  100    44    316     57 --:--:-- --:--:-- --:--:--   374{"trialNumber":"NCT99999991","title":"xyz","locationList":[],"sponsorList":[],"clinicalTrialDrug":[],"internalVersion":0,"gsrsMatchingComplete":false,"gsrsCreated":0,"gsrsUpdated":0,"lastModifiedDate":1614610802737,"creationDate":1614610802737}

$ curl -X POST -d '{"trialNumber":"NCT99999991", "title":"xyzb"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   261    0   216  100    45   3483    725 --:--:-- --:--:-- --:--:--  4209{"trialNumber":"NCT99999991","title":"xyzb","locationList":[],"sponsorList":[],"clinicalTrialDrug":[],"internalVersion":1,"gsrsMatchingComplete":false,"gsrsCreated":0,"gsrsUpdated":0,"lastModifiedDate":1614610805198}

$ curl -X PUT -d '{"trialNumber":"NCT99999991", "title":"xyzc"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   261    0   216  100    45   1028    214 --:--:-- --:--:-- --:--:--  1248{"trialNumber":"NCT99999991","title":"xyzc","locationList":[],"sponsorList":[],"clinicalTrialDrug":[],"internalVersion":2,"gsrsMatchingComplete":false,"gsrsCreated":0,"gsrsUpdated":0,"lastModifiedDate":1614610886020}

$ curl -X PUT -d '{"trialNumber":"NCT99999991", "title":"xyzd"}' -H 'Content-Type: application/json' http://localhost:8080/api/v2/clinicaltrial
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   261    0   216  100    45   3000    625 --:--:-- --:--:-- --:--:--  3676{"trialNumber":"NCT99999991","title":"xyzd","locationList":[],"sponsorList":[],"clinicalTrialDrug":[],"internalVersion":3,"gsrsMatchingComplete":false,"gsrsCreated":0,"gsrsUpdated":0,"lastModifiedDate":1614610925537}





