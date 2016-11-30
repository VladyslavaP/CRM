var current_user = null;
var users = [];
var reviews = [];

$(document).ready(function() {
	// Getting info about current user

	/*requestManager.setServiceName('UserService');
	requestManager.setEndpointName('getUserInfo.php');
	requestManager.call(
		function (response) { // SUCCESS

			var userAccount = JSON.parse(response);
			current_user = userAccount;

			if (userAccount.photo.length > 1) {
				$('.profile-user .profile-photo').attr('src', userAccount.photo);
			}

			$('.profile-user .profile-fullname').text(userAccount.full_name);
			$('.profile-user .profile-position').text(userAccount.position);
		},
		function (response) { // FAIL
			alert('Unable to get User data.');
		}
	);

	var login_interval = setInterval(function() {
		if (current_user) {
			clearInterval(login_interval);
			loadTeamInfo();
		}
	},100);*/

	$('.tooltipster').tooltipster({
		theme: 'tooltipster-shadow'
	});

	$('#user-profile .edit-button').click(function() {
		if ($('.profile-userinfo-block[role="form"]').css('display') == 'none') {
			$('.profile-userinfo-block[role="info"]').hide();
			$('.profile-userinfo-block[role="form"]').css('display','inline-block');
		} else {
			$('#fullname-display').text($('#fullname').val());
			$('#fullname-position').text($('#position').val());
			$('.profile-userinfo-block[role="form"]').hide();
			$('.profile-userinfo-block[role="info"]').css('display','inline-block');
		}
	});
	
});



function loadTeamInfo() {
	// Getting all users info

	requestManager.setServiceName('UserService');
	requestManager.setEndpointName('getAllUsersInfo.php');
	requestManager.call(
		function (response) { // SUCCESS

			var userAccounts = JSON.parse(response);

			if (!userAccounts || userAccounts.length < 1) {
				return;
			}

			users = userAccounts;

			var $emptyTeamMemberBlock = $('#profile-team-info .profile-team-member');

			for (var i = 0; i < userAccounts.length; i++) {

				if (current_user.login == userAccounts[i].login) {
					continue;
				}

				var $clone = $emptyTeamMemberBlock.clone();
				if (userAccounts[i].photo.length > 1) {
					$emptyTeamMemberBlock.find('.profile-small-photo').attr('src', userAccounts[i].photo);
				}
				$emptyTeamMemberBlock.find('.profile-small-fullname').text(userAccounts[i].full_name);
				$emptyTeamMemberBlock.find('.profile-small-position').text(userAccounts[i].position);

				if (i < userAccounts.length - 1) {
					$emptyTeamMemberBlock.after($clone);
					$emptyTeamMemberBlock = $clone;
				}
			}
		},
		function (response) { // FAIL
			alert('Unable to get User data.');
		}
	);

	var all_users_interval = setInterval(function() {
		if (current_user) {
			clearInterval(all_users_interval);
			loadReviewsInfo();
		}
	},100);
}


function loadReviewsInfo() {
	// Getting reviews info

	requestManager.setServiceName('ReviewService');
	requestManager.setEndpointName('getAssignedReviews.php');
	requestManager.call(
		function (response) { // SUCCESS

			var assignedReviews = JSON.parse(response);

			if (!assignedReviews || assignedReviews.length < 1) {
				return;
			}

			reviews = assignedReviews;
			$('#reviews-in-progress').text(assignedReviews.length);

			var $emptyReviewBlock = $('#profile-reviews-info .review-block');

			for (var i = 0; i < assignedReviews.length; i++) {
				var $clone = $emptyReviewBlock.clone();

				var userAccount = getUserByLogin(assignedReviews[i].review_for);

				if (userAccount.photo.length > 1) {
					$emptyReviewBlock.find('.profile-small-photo').attr('src', userAccount.photo);
				}
				$emptyReviewBlock.find('.profile-small-fullname').text(userAccount.full_name);
				$emptyReviewBlock.find('.profile-small-position').text(userAccount.position);

				$emptyReviewBlock.find('.review-info').text(assignedReviews[i].description);

				if (i < assignedReviews.length - 1) {
					$emptyReviewBlock.after($clone);
					$emptyReviewBlock.after('<hr>');
					$emptyReviewBlock = $clone;
				}
			}

		},
		function (response) { // FAIL
			alert('Unable to get User data.');
		}
	);
}


function getUserByLogin(login) {
	if(!users) {
		return false;
	}

	for (var i = 0; i < users.length; i++) {
		if (users[i].login == login) {
			return users[i];
		}
	}

	return false;
}