function sendLoginRequest(username, password) {
	$.ajax({
		type: "GET",
		url: "http://dispatcher.service.local/login.php",
		dataType: "json",
		headers: {
			"Authorization": "Basic " + btoa(username + ":" + password)
		},
		success: function (data) {
			if (data.success) {
				window.location = '/';
			} else {
				alert('Wrong username or password.');
			}
		}
	});
}

$(document).ready(function() {
	/*$('#login-form').submit(function(e) {
		sendLoginRequest($('#username').val(), $('#password').val());
		return false;
	});*/
});