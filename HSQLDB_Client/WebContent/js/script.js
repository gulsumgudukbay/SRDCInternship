// @author Gulsum Gudukbay

var rootURL = "http://localhost:8080/HSQLDB_Server/UserResource/users";

function createUser(){
	var isvalidated = validateFormCreate();
	if(isvalidated== true){
		$.ajax({
			cache: false,
	        crossDomain: true,
			url: rootURL,
			type: 'POST',
			dataType: "json",//response
			data: formToJSON(),
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function(user, textStatus, jqXHR){
				alert('User created successfully!');
				window.location.href="http://localhost:8080/HSQLDB_Client/index.html";
			},
			error: function( jqXHR, textStatus, errorThrown){
				alert('create user error: '+jqXHR.responseText);
			}
		});
	}
	
}

function seeUserSummary(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/info",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = renderListSummary(data) ;
            $("#home").html( htmlContent );
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent =  "No info found!" ;
            $("#home").html( htmlContent );
        }
    } );
}

function seeUserContact(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/info",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = renderListContact(data) ;
            $("#menu2").html( htmlContent );
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent = "No info found!" ;
            $("#menu2").html( htmlContent );
        }
    } );
}

function seeUserDetailed(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/info",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = renderListDetailed(data) ;
            $("#menu1").html( htmlContent );
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent = "No info found!" ;
            $("#menu1").html( htmlContent );
        }
    } );
}

function seeUserTeam(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/info",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = renderListTeamRole(data) ;
            $("#menu3").html( htmlContent );
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent = "No info found!" ;
            $("#menu3").html( htmlContent );
        }
    } );
}


jQuery(document).ready(function() {
	window.onload=seeUserSummary;

    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
});


function validateFormCreate() {
    var un = document.forms["formCreate"]["preferredUsername"].value;
    var em = document.forms["formCreate"]["email"].value;
    var pass = document.forms["formCreate"]["password"].value;
    var bool = true;
    if (un == null || un == "") {
        alert("Username must be filled out");
        bool= false;
    }
    if (em == null || em == "") {
        alert("Email must be filled out");
        bool= false;
    }
    if (pass == null || pass == "") {
        alert("Password must be filled out");
        bool= false;
    }
    return bool;
}


function validateFormLogin() {
    var unlogin = document.forms["loginform"]["preferredUsername"].value;
    var passlogin = document.forms["loginform"]["password"].value;
    var b = true;
    if (unlogin == null || unlogin == "") {
        alert("Username must be filled out");
        b= false;
    }
    if (passlogin == null || passlogin == "") {
        alert("Password must be filled out");
        b= false;
    }
    return b;
}



function login(){
	var isvalidatedLogin = validateFormLogin();
	if(isvalidatedLogin== true){
		$.ajax({	
			cache: false,
	        crossDomain: true,
			url: rootURL+"/login",
			type: 'POST',
			dataType: "text",//response
			data: formToJSON(),
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function(user, textStatus, jqXHR){
				alert('User logged in successfully!');
				window.location.href = jqXHR.responseText;
			},
			error: function( jqXHR, textStatus, errorThrown){
				alert(jqXHR.responseText);
			}
		});
	}
}

function renderListSummary(data){
	var $table = $("<table class='table table-hover'></table>");
	var $preferredUsername = $("<tr></tr>");
	$preferredUsername.append($("<th></th>").html("Username"));
	$preferredUsername.append($("<td></td>").html(data.preferredUsername));
	$table.append($preferredUsername);
	
	var $picture = $("<tr></tr>");
	$picture.append($("<th></th>").html("Picture"));
	$picture.append($("<td></td>").html("<img src='"+data.picture +"' alt='' border='3' height='100' width='100' />"));
	$table.append($picture);
	
	var $profile = $("<tr></tr>");
	$profile.append($("<th></th>").html("Profile"));
	$profile.append($("<td></td>").html(data.profile));
	$table.append($profile);
	
	var $gender = $("<tr></tr>");
	$gender.append($("<th></th>").html("Gender"));
	$gender.append($("<td></td>").html(data.gender));
	$table.append($gender);
	
	var $sub = $("<tr></tr>");
	$sub.append($("<th></th>").html("Sub"));
	$sub.append($("<td></td>").html(data.sub));
	$table.append($sub);
	
	return $table;
}

function renderListDetailed(data){
	var $table = $("<table class='table table-hover'></table>");
	var $name = $("<tr></tr>");
	$name.append($("<th></th>").html("Name"));
	$name.append($("<td></td>").html(data.name));
	$table.append($name);
	
	var $givenname = $("<tr></tr>");
	$givenname.append($("<th></th>").html("Given Name"));
	$givenname.append($("<td></td>").html(data.givenName));
	$table.append($givenname);
	
	var $familyname = $("<tr></tr>");
	$familyname.append($("<th></th>").html("Family Name"));
	$familyname.append($("<td></td>").html(data.familyName));
	$table.append($familyname);
	
	var $middlename = $("<tr></tr>");
	$middlename.append($("<th></th>").html("Middle Name"));
	$middlename.append($("<td></td>").html(data.middleName));
	$table.append($middlename);

	var $birthdate = $("<tr></tr>");
	$birthdate.append($("<th></th>").html("Birthdate"));
	$birthdate.append($("<td></td>").html(data.birthdate));
	$table.append($birthdate);
	return $table;
}

function renderListContact(data){
	var $table = $("<table class='table table-hover'></table>");
	var $website = $("<tr></tr>");
	$website.append($("<th></th>").html("Website"));
	$website.append($("<td></td>").html(data.website));
	$table.append($website);
	
	var $email = $("<tr></tr>");
	$email.append($("<th></th>").html("Email"));
	$email.append($("<td></td>").html(data.email));
	$table.append($email);
	
	var $zoneinfo = $("<tr></tr>");
	$zoneinfo.append($("<th></th>").html("Zone Info"));
	$zoneinfo.append($("<td></td>").html(data.zoneinfo));
	$table.append($zoneinfo);
	
	var $locale = $("<tr></tr>");
	$locale.append($("<th></th>").html("Locale"));
	$locale.append($("<td></td>").html(data.locale));
	$table.append($locale);
	
	var $phoneNumber = $("<tr></tr>");
	$phoneNumber.append($("<th></th>").html("Phone Number"));
	$phoneNumber.append($("<td></td>").html(data.phoneNumber));
	$table.append($phoneNumber);
	
	var $address = $("<tr></tr>");
	$address.append($("<th></th>").html("Address"));
	$address.append($("<td></td>").html(data.address));
	$table.append($address);
	
	var $updatedTime = $("<tr></tr>");
	$updatedTime.append($("<th></th>").html("Updated Time"));
	$updatedTime.append($("<td></td>").html(data.updatedTime));
	$table.append($updatedTime);

	return $table;
}

function renderListTeamRole(data){
	var $table = $("<table class='table table-hover table-responsive'></table>");
	var $team = $("<tr></tr>");
	$team.append($("<th></th>").html("Team"));
	$team.append($("<td></td>").html(data.team));
	$table.append($team);
	
	var $role = $("<tr></tr>");
	$role.append($("<th></th>").html("Role"));
	$role.append($("<td></td>").html(data.role));
	$table.append($role);
	
	var $teamRole = $("<tr></tr>");
	$teamRole.append($("<th></th>").html("Team Role"));
	$teamRole.append($("<td></td>").html(data.teamRole));
	$table.append($teamRole);
	

	return $table;
}

function formToJSON(){
	var jsonstring = JSON.stringify({
		"id": $('#id').val(),
		"password": $('#password').val(),
		"sub": $('#sub').val(),
		"preferredUsername": $('#preferredUsername').val(),
		"name": $('#name').val(),
		"givenName": $("#givenName").val(),
		"familyName": $('#familyName').val(),
		"middleName": $('#middleName').val(),
		"nickname": $('#nickname').val(),
		
		"profile": $('#profile').val(),
		"picture": $('#picture').val(),
		"website": $('#website').val(),
		"email": $('#email').val(),
		"emailVerified": $('#emailVerified').val(),
		"gender": $("#gender").val(),
		"zoneinfo": $('#zoneinfo').val(),
		"locale": $('#locale').val(),
		
		"phoneNumber": $('#phoneNumber').val(),
		"phoneNumberVerified": $('#phoneNumberVerified').val(),
		"address": $('#address').val(),
		"updatedTime": $('#updatedTime').val(),
		"birthdate": $('#birthdate').val(),
		"role": $("#role").val(),
		"team": $('#team').val(),
		"teamRole": $('#teamRole').val(),
	});
	return jsonstring;
}
