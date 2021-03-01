use strict;
use warnings;
use Test::More;
use 5.10.0;
# tests => 23;
use Data::Dumper;
use REST::Client;
use JSON;

# copy from Firefox network tab headers
my $play_session = '
PLAY_FLASH=; PLAY_SESSION=1c13cdcd589fb3358ed75e2252690abb5169ed48-ix.session=8b31669e-500d-49e8-9e5b-4742b938c758
';

$play_session =~s/PLAY_FLASH=;//;
# remove all linefeeds 
$play_session =~s/\015?\012?$//;

#  Test data to load into GSRS 
#  .\inxight\modules\ginas\test\testdumps\repo90.ginas

# More notes at end: 
my $repo90 = 0; # if this is true a set of substances in repo90 will be used.
				# if not true and you don't have a full dataset things might not work as expected. 

my $baseUrl = 'http://localhost:8080';
my $basePath = '/api/v2';
# $basePath = '/ginas/app';


my $test_user_connection=0;



my $client = REST::Client->new();
$client->setHost($baseUrl);

$client->addHeader('charset', 'UTF-8');
$client->addHeader('Cookie', $play_session);


my $json = JSON->new->allow_nonref;

my $trialNumber_get = 'NCT04075565';
my $trialNumber_ne = 'NCT00000000';
my $trialNumber_create = 'NCT99999999';
my $trialNumber_delete = $trialNumber_create;

my $substanceUuid_ne = '3345eae8-2d84-4a19-acf7-x6xx3xx022x5';  # made up uuid
my $substanceUuid1;
my $substanceUuid2;
my $substanceUuid3;
my $substanceUuid4;



# These substances have to exists and be indexed to work!!!!!
# These ones are in the ncts repo90.ginas test data set
if ($repo90) {
  $substanceUuid1="1db30542-0cc4-4098-9d89-8340926026e9"; #aspirin calcium
  $substanceUuid2="fb0bb85e-36a8-49f5-b48c-fe84db45923c"; #LEUCOMYCIN A1 ACETATE
  $substanceUuid3="cf8df6ce-b0c6-4570-a07a-118cd58a4e90"; #VERBESINA SATIVA WHOLE
  $substanceUuid4="d42fd6c5-f0d5-49c0-bd81-502f161c1297"; #PANDANUS LOUREIROI WHOLE
} else {
  # make edits here as needed.
  $substanceUuid1="09b782d8-d7bf-4099-b432-7da505d81459"; #pentane
  $substanceUuid2="46fca64d-6ff3-42d2-a2c0-92a1c6fc65ed"; #zinc acetate
  $substanceUuid3="3fce2c72-3383-4511-9bc6-fcc646d0462f"; #palmitic acid
  $substanceUuid4="bd3f6640-1845-472f-b743-c8d77367576f"; #CANNABIDIOL
}

my $base_json = '
{
	"trialNumber": "NCTNUMBER",
	"title": "The Influence of GINkGo Biloba on the Pharmacokinetics of the UGT Substrate raltEgraviR (GINGER)",
	"recruitment": "Completed",
	"phases": "Phase 1",
	"fundedBys": "Other|Industry",
	"studyTypes": "Interventional",
	"studyDesigns": "Allocation: Randomized|Intervention Model: Crossover Assignment|Masking: None (Open Label)",
	"studyResults": "No Results Available",
	"gender": "All",
	"enrollment": "18",
	"otherIds": "UMCN-AKF 10.02",
	"acronym": "GINGER",
	"completionDate": 990331200000,
	"primaryCompletionDate": 1551502800000,
	"url": "https://ClinicalTrials.gov/show/NCTNUMBER",
	"locations": "CRCN, Radboud University Medical Centre, Nijmegen, Netherlands",
	"locationList": [],
	"sponsorList": [],
	"clinicalTrialDrug": [{
		"trialNumber": "NCTNUMBER",
		"substanceUuid": "SUBSTANCEUUID1"
	}, {
		"trialNumber": "NCTNUMBER",
		"substanceUuid": "SUBSTANCEUUID2"
	}],
	"clinicalTrialApplicationList": []
}
';
# 		"id": 104162,
#		"id": 104163,

$base_json =~ s/NCTNUMBER/NCT99999999/g;
$base_json =~ s/SUBSTANCEUUID1/$substanceUuid1/g;
$base_json =~ s/SUBSTANCEUUID2/$substanceUuid2/g;

#print $base_json;
#	my $decoded = $json->decode($base_json);
# exit;


# delete_helper($trialNumber_1, $substanceUuid_1);
# delete_helper($trialNumber_1, $substanceUuid_2);
# delete_helper($trialNumber_1, $substanceUuid_3);

# delete to make sure it does not exist.




delete_helper($trialNumber_create);

# exit;


# {
#  "message": "java.lang.reflect.InvocationTargetException",
#  "status": 500
# }

## Test user connection ##
if (0) {

	my $condition = sub {
		my $decoded = shift;
		return (defined($decoded->{messages}) && $decoded->{messages}->[0] eq 'All is good!'); 
	};
	
	my $args = {
		expected_status => 200, 
		dump => 0, 
		dump_json => 0,
		url => "/ginas/app/testUP",
		condition => $condition,
		message => 'Test user profile'
	};
	if($test_user_connection) { 
	 test_get($args);
	}
}


## GET 1 and 2 ##
if (0) {


	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_get); 
	};
	
	my $args = {
		expected_status => 200, 
		dump => 0, 
		dump_json => 0,
		url => "$basePath/clinicaltrial($trialNumber_get)",
		condition => $condition,
		message => 'Get one that probably exists.'
	};
	test_get($args);
		
}

# Get a non-existing record. 
# Error 404 is probably not right but that is what gsrs returns and with an unhelpful message.
{
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{message}) && $decoded->{message} eq "not found"); 
	};	
	my $args = {
		expected_status => 404, 
		dump => 0, 
		url => "$basePath/clinicaltrial($trialNumber_ne)",
		condition => $condition,
		message => 'Get a non-existing trialNumber'
	};
	test_get($args);
}


# Get many. 
# Error 404 is probably not right but that is what gsrs returns and with an unhelpful message.
if (1) {
	my $condition = sub { 
		my $decoded = shift; 
		return (
				defined($decoded->{content}) 
				&& ref $decoded->{content} eq 'ARRAY'
	#			&& scalar(@{$decoded->{content}}) > 1
		); 
	};
	my $args = {
		expected_status => 200, 
		dump => 0, 
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Get many (page)'
	};
	
	test_get($args);
}


# Create a new record (using post)
{	
	# to Danny: if record alredy exists should get 409 but we're getting a 500 in this case. https://stackoverflow.com/questions/3825990/http-response-code-for-post-when-resource-already-exists
	# 'status' => 500,
    #        'message' => 'Object of class [gov.nih.ncats.gsrsspringcv2.ClinicalTrial] with identifier [NCT99999999]: optimistic locking failed; nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect) : [gov.nih.ncats.gsrsspringcv2.ClinicalTrial#NCT99999999]'
    #      };
	
    # 500 Internal Server Error if no change is made.
	my $data_string = $base_json;
	my $decoded = $json->decode($data_string);
	my $condition = sub {
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_create); 
	};
	
	# print Data::Dumper->Dump([$decoded]);
	
	my $args = {
		expected_status => 201, 
		data => $decoded,
		dump => 0, 
		dump_json => 0,
			url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test post - Create a new record.'
	};	
	test_post($args);
}
# stop at post

# Update the record just changing the recruitment value
{
    # 500 Internal Server Error if not change is made.
	# my $data_string = $base_json;
	my $data_string = get_to_json($trialNumber_create);
	
	my $decoded = $json->decode($data_string);
	$decoded->{recruitment} = 'XXXX';
	# $decoded->{gsrsUpdated} = 0;
	
	
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_create && $decoded->{recruitment} eq 'XXXX'); 
	};
	my $args = {
		expected_status => 200, 
		data => $decoded,
		dump_json => 0, 
		dump => 0,
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put - Update the record just created changing recruitment value.'
	};
	test_put($args);
}
# exit;

# Try to update with duplicate substanceUuids
{    
    # 500 Internal Server Error if not change is made.
	# my $data_string = $base_json;
	my $data_string = get_to_json($trialNumber_create);

	my $decoded = $json->decode($data_string);
	$decoded->{recruitment} = 'XXXX';
	# $decoded->{gsrsUpdated} = 0;

	my $ctd = $json->decode('{	
		"clinicalTrialDrug": [{
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid3.'"
		},
		{
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid3.'"
		}
	]}');
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	# print $json->encode($decoded);
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{errors}) && $decoded->{errors}->[0] =~ /Duplicate substanceUuids/i); 
	};
	my $args = {
		expected_status => 400, 
		data => $decoded,
		dump => 0, 
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put - Try to update with duplicate substanceUuids'
	};
	test_put($args);
}


# Try to update with badly formatted NctNumber
{   

	my $trialNumber_badly = 'iambad.';
    # 500 Internal Server Error if not change is made.
	my $data_string = $base_json;
	$data_string =~ s/$trialNumber_create/$trialNumber_badly/g;
	
	my $decoded = $json->decode($data_string);
	$decoded->{recruitment} = 'XXXX';
	my $ctd = $json->decode('{	
		"clinicalTrialDrug": [{
		"trialNumber": "'.$trialNumber_badly.'",
		"substanceUuid": "'.$substanceUuid1.'"
		}
	]}');
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	
	
	# print $json->encode($decoded);
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{errors}) && $decoded->{errors}->[0] =~ /Invalid trialNumber/i); 
	};
	my $args = {
		expected_status => 400, 
		data => $decoded,
		dump => 0, 
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put - Try to update with badly formatted NctNumber.'
	};
	test_put($args);
}

# Update a record that does not exist.
{    
    # 500 Internal Server Error if no change is made.
	my $data_string = get_to_json($trialNumber_create);
	$data_string =~ s/$trialNumber_create/$trialNumber_ne/g;
	my $decoded = $json->decode($data_string);


	my $ctd = $json->decode('{
	"clinicalTrialDrug": [{
		"trialNumber": "'.$trialNumber_ne.'",
		"substanceUuid": "'.$substanceUuid1.'"
	},{
		"trialNumber": "'.$trialNumber_ne.'",
		"substanceUuid": "'.$substanceUuid2.'"
	}
	]}');
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	
	my $condition = sub { 
		my $decoded = shift;
		
		return (defined($decoded->{errors}) && $decoded->{errors}->[0] =~ /ClinicalTrial does not exist/i); 
		# return (defined($decoded->{message}) && $decoded->{message} =~ /Error updating record/i); 
		
		
	};
	
	my $args = {
		expected_status => 400, 
		data => $decoded,
		dump => 0,		
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put -- Update a record that does not exist.'
	};
	test_put($args);
}

# Update again with changed recruitment value but change the ClinicalTrialDrug Object by removing one ct drug
{    
    # 500 Internal Server Error if not change is made.
	# my $data_string = $base_json;
	my $data_string = get_to_json($trialNumber_create);
	my $decoded = $json->decode($data_string);
	$decoded->{recruitment} = 'XXXX';
	my $ctd = $json->decode('{	
		"clinicalTrialDrug": [{
		"id": 104162,
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid1.'"
	}
	]}');
	
	
	# $decoded->{primaryCompletionDate} = '12/1/2019';
	# $decoded->{completionDate} = '12/1/2019'; 	
	
	# $decoded->{internalVersion} = 3; 	
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	# print $json->encode($decoded);
	# exit;
	
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_create && $decoded->{recruitment} eq 'XXXX'); 
	};
	my $args = {
		expected_status => 200, 
		data => $decoded,
		dump_json => 0,
		dump => 0, 
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put - Update again by removing one ct drug'
	};
	test_put($args);
}



# Update again with changed recruitment value but change the ClinicalTrialDrug Object by adding one ct drug
{
    
    # 500 Internal Server Error if no change is made.

	# my $data_string = $base_json;
	my $data_string = get_to_json($trialNumber_create);

	my $decoded = $json->decode($data_string);
	$decoded->{recruitment} = 'XXXX';
	
	
	my $ctd = $json->decode('{
		"clinicalTrialDrug": [{
		"id": 104162,
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid1.'"
	},{
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid2.'"
	}
	]}');
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	
	
	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_create && $decoded->{recruitment} eq 'XXXX'); 
	};
	
	my $args = {
		expected_status => 200, 
		data => $decoded,
		dump => 0,
		dump_json => 0,		
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put - Update again by adding one ct drug'
	};
	test_put($args);
}



# Get the record, then update the substanceUuids to something completely different, note these ctdrugs don't have ids, so old rows should be deleted and new rows with new ids created.  

{
    
	my $data_string = get_to_json($trialNumber_create);
	
	my $decoded = $json->decode($data_string);


	my $ctd = $json->decode('{
	"clinicalTrialDrug": [{
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid3.'"
	},{
		"trialNumber": "'.$trialNumber_create.'",
		"substanceUuid": "'.$substanceUuid4.'"
	}
	]}');
	$decoded->{clinicalTrialDrug} = $ctd->{clinicalTrialDrug}; 	
	
	

	my $condition = sub { 
		my $decoded = shift;
		return (defined($decoded->{trialNumber}) && $decoded->{trialNumber} eq $trialNumber_create && $decoded->{recruitment} eq 'XXXX'
		and ($decoded->{clinicalTrialDrug}->[0]->{substanceUuid} eq $substanceUuid3 or $decoded->{clinicalTrialDrug}->[1]->{substanceUuid} eq $substanceUuid3)
		
		); 
	};
	
	my $args = {
		expected_status => 200, 
		data => $decoded,
		dump => 0, 
		dump_json => 0,
		url => "$basePath/clinicaltrial",
		condition => $condition,
		message => 'Test put -- Get the record, then update the substanceUuids to something different.'
	};
	test_put($args);
}




# delete the post just created.
# delete_helper($trialNumber_create);


# Danny: internalValue seems to be incremented on GET.

sub test_get {
    my $args = shift;
	$client->addHeader('Content-Type', 'application/x-www-form-urlencoded');
	$client->GET($args->{url});	
	# print "$args->{url}\n";
	my $content = $client->responseContent();
	print $content if($args->{dump_json});
	my $decoded = $json->decode($content);
	print Data::Dumper->Dump([\$decoded]) if($args->{dump});
	ok($args->{condition}($decoded), $args->{message});
	my $rc = $client->responseCode();
	my $expected = $args->{expected_status};
	ok($client->responseCode() == $args->{expected_status}, "Status matched expected_status. EC: $expected / RC: $rc");
}

sub test_post {
    my $args = shift;
	$client->addHeader('Accept', 'application/json');
	$client->addHeader('Content-Type', 'application/json');
	$client->POST($args->{url}, $json->encode($args->{data}));
	my $content = $client->responseContent();
	print $content if($args->{dump_json});
	my $decoded = $json->decode($content);
	print Data::Dumper->Dump([\$decoded]) if($args->{dump});	
	ok($args->{condition}($decoded), $args->{message});
	my $rc = $client->responseCode();
	my $expected = $args->{expected_status};
	ok($client->responseCode() == $args->{expected_status}, "Status matched expected_status. EC: $expected / RC: $rc");
}

sub test_put {
    my $args = shift;
	# print $json->encode($args->{data});
	$client->addHeader('Accept', 'application/json');
	$client->addHeader('Content-Type', 'application/json');
	$client->PUT($args->{url}, $json->encode($args->{data}));
	
	# print "\nDATA\n";
	# print Data::Dumper->Dump([$args->{data}]);
	# print "\n";
	
	
	print "test_put $args->{url}\n";
	my $rc = $client->responseCode();
	my $expected = $args->{expected_status};
	ok($client->responseCode() == $args->{expected_status}, "Status matched expected_status. EC: $expected / RC: $rc");
	my $content = $client->responseContent();
	if(!$content) { print "Content is empty\n" }
	print $content if($args->{dump_json});
	my $decoded = $json->decode($client->responseContent());
	print Data::Dumper->Dump([\$decoded]) if($args->{dump});
	ok($args->{condition}($decoded), $args->{message});
}


sub test_delete {
    my $args = shift;
	$client->addHeader('Content-Type', 'application/x-www-form-urlencoded');
	$client->DELETE($args->{url});
	my $decoded = $json->decode($client->responseContent());
	print Data::Dumper->Dump([\$decoded]) if($args->{dump});	
	ok($args->{condition}($decoded), $args->{message});
	my $rc = $client->responseCode();
	my $expected = $args->{expected_status};
	ok($client->responseCode() == $args->{expected_status}, "Status matched expected_status. EC: $expected / RC: $rc");
}

# Danny on successful delete there is no JSON message returned. 
# Danny deleting a substance that does not exist returns 500 which is probably not right. {"message":"No class gov.nih.ncats.gsrsspringcv2.ClinicalTrial entity with id NCT99999999 exists!","status":500}
sub delete_helper { 
    my $trialNumber = shift;
	my $dump = 1;
	$client->addHeader('Accept', 'application/json');
	$client->addHeader('Content-Type', 'application/x-www-form-urlencoded');
	$client->DELETE("$basePath/clinicaltrial($trialNumber)");
	if($client->responseContent()) {
		print $client->responseContent();
		my $decoded = $json->decode($client->responseContent());
		print Data::Dumper->Dump([\$decoded]) if ($dump);
		# ok(defined($decoded->{deleted}) && $decoded->{deleted} eq 'true', 'Delete helper.');
	} else { 
		print "No reponse content !!!!\n";
	}
	my $rc = $client->responseCode();
	my $expected = 200;
	ok($client->responseCode() == 200, "Status matched expected_status. EC: $expected / RC: $rc");
}

sub get_to_json { 
    my $trialNumber=shift;
	my $dump=1;
	$client->addHeader('Accept', 'application/json');
	$client->addHeader('Content-Type', 'application/x-www-form-urlencoded');
	# print "URL: $basePath/clinicaltrial($trialNumber)\n";
	$client->GET("$basePath/clinicaltrial($trialNumber)");
	return $client->responseContent();
}



__END__


