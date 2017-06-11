

var rootURL = "http://localhost:8080/ServerWeb/UserResource/users";


function readUser(){
	$.ajax({
		cache: false,
        crossDomain: true,
		url: rootURL+"/read",
		type: 'POST',
		dataType: "json",//response
		data: formToJSON(),
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function(data, textStatus, jqXHR){
			alert('User found!');
			var usercontent = renderDetailsOfUser(data);
			var htmlContent = $( "#data" ).html( ) + "<br>Content: <br> " + usercontent ;
            $("#data").html( htmlContent );
		},
		error: function( jqXHR, textStatus, errorThrown){
			alert(jqXHR.responseText);
		}
	});
}

function createUser(){
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
			window.location.href="http://localhost:8080/ClientWeb/index.html";
		},
		error: function( jqXHR, textStatus, errorThrown){
			alert('create user error: '+jqXHR.responseText);
		}
	});
}

function create(){
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
			window.location.href="http://localhost:8080/ClientWeb/adminUser.html";
		},
		error: function( jqXHR, textStatus, errorThrown){
			alert('create user error: '+jqXHR.responseText);
		}
	});
}

function sendMail(){
	$.ajax({
		cache: false,
        crossDomain: true,
		url: rootURL+"/sendMessage",
		type: 'POST',
		dataType: "text",//response
		data: formToJSON(),
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function(user, textStatus, jqXHR){
			alert('Mail sent successfully!');
			window.location.href="http://localhost:8080/ClientWeb/regularUser.html";
		},
		error: function( jqXHR, textStatus, errorThrown){
			alert('Mail specified cannot be found! Please try again: ');
		}
	});
}


function seeInbox(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/inbox",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = $( "#seeInbox" ).html( ) + "Inbox: <br> "  ;
            $("#seeInbox").append( htmlContent );
            renderListInbox(data);
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent = $( "#seeInbox" ).html( ) + "No messages found!" ;
            $("#seeInbox").html( htmlContent );
        }
    } );
}

function seeOutbox(){
	$.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: rootURL+"/outbox",
        type: "GET",
        success: function( data, textStatus, jqXHR ) {
        	var htmlContent = $( "#seeOutbox" ).html( ) + "Outbox: <br> "  ;
            $("#seeOutbox").append( htmlContent );
            renderListOutbox(data);
        },
        error: function( data, textStatus, errorThrown ) {
        	var htmlContent = $( "#seeOutbox" ).html( ) + "No messages found!" ;
            $("#seeOutbox").html( htmlContent );
        }
    } );
}

function login(){
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
			alert('login user error! ');
		}
	});
}

function updateUser(){
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ '/update',
		dataType: "text",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert(jqXHR.responseText);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateUser error: '+jqXHR.responseText);
		}
	});
}

function deleteUser(){
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ '/delete',
		dataType: "text",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('User deleted successfully!');
			window.location.href = jqXHR.responseText;
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteUser error: '+jqXHR.responseText);
		}
	});
}

function renderListOutbox(data) {
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	var $table = $("<table></table>");
	var $line = $("<tr></tr>")
	$line.append( $("<th></th>").html("Message") );
	$line.append( $("<th></th>").html("Id of Receiver") );
	$line.append( $("<th></th>").html("Date & Time") );
	$table.append($line);
	$.each(list, function(index, message) {
		$line = $("<tr></tr>")
		$line.append( $("<td></td>").html(message.message) );
		$line.append( $("<td></td>").html(message.toId) );
		$line.append( $("<td></td>").html(message.timestamp) );
		$table.append($line);
	});
	$table.appendTo($("#seeOutbox"));
}

function renderListInbox(data) {
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	var $table = $("<table></table>");
	var $line = $("<tr></tr>")
	$line.append( $("<th></th>").html("Message") );
	$line.append( $("<th></th>").html("Id of Sender") );
	$line.append( $("<th></th>").html("Date & Time") );
	$table.append($line);
	$.each(list, function(index, message) {
		$line = $("<tr></tr>")
		$line.append( $("<td></td>").html(message.message) );
		$line.append( $("<td></td>").html(message.fromId) );
		$line.append( $("<td></td>").html(message.timestamp) );
		$table.append($line);
	});
	$table.appendTo($("#seeInbox"));
}



function renderDetailsOfUser(user){
	$('#username').val(user.username);
	$('#password').val(user.password);
	$('#email').val(user.email);
	$('#name').val(user.name);
	$('#surname').val(user.surname);
	$('#type').val(user.type);
	var contentofuser = "";
	contentofuser+="Username: "+ user.username+"<br>Password: "+ user.password+"<br>Email: "+ user.email+"<br>Name: "+ user.name+"<br>Surname: "+user.surname+"<br>Type: "+user.type;
	return contentofuser;
}

function formToJSON(){
	var jsonstring = JSON.stringify({
		"username": $('#username').val(),
		"password": $('#password').val(),
		"email": $('#email').val(),
		"name": $('#name').val(),
		"surname": $('#surname').val(),
		"type": $("input[name=type]:checked").val(),
		"to": $('#to').val(),
		"message": $('#message').val(),
		"oldusername": $('#oldusername').val(),
	});
	return jsonstring;
}
