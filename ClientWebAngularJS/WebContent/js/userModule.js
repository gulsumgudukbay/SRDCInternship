/**
 * 
 */
var app = angular.module('UserManagement');

app.controller("HttpCtrl", function($scope, $http){
	var app = this;
	var rootURL = "http://localhost:8080/ServerWeb/UserResource/users";

	$scope.login = function(){
		$scope.jsonObj = formToJSON();
		alert("LOGIN: "+$scope.jsonObj);
		var response = $http.post(rootURL+"/login", $scope.jsonObj);
		response.success(function(user, textStatus, jqXHR){
				alert('User logged in successfully!');
				window.location.href = jqXHR.responseText;
			});
		response.error(function( jqXHR, textStatus, errorThrown){
				alert('login user error! ')});
	}
	
	
})