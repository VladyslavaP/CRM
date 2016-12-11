var current_user = null;
var users = [];
var reviews = [];

$(document).ready(function() {
	$('.tooltipster').tooltipster({
		theme: 'tooltipster-shadow'
	});

    $('.main-info-container').on('click', '.edit-button', function() {
        $(this).siblings('.save-button').toggle();
    })

	$('#user-profile').on('click', '.edit-button', function() {
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

	$('#profile-detailed-info').on('click', '.edit-button', function() {
	    $('#profile-detailed-info div.medium-value.editable').toggle();
	    $('#profile-detailed-info input.medium-value').toggle();
	});

	$('#profile-detailed-info').on('click', '.save-button', function() {
	    var component = $(this).parents("#profile-detailed-info");
        $.ajax({
            url: '/updateDetails',
            type: "POST",
            contentType: "application/json",
            beforeSend: setUpCsrf,
            data: JSON.stringify({
                phoneNumber: component.find("input[name='phoneNumber']").val(),
                birthDate: component.find("input[name='birthDate']").val()
            }),
            success: function(data){
                component.empty().append($(data).contents());
            }
        });
    });

    $('#user-profile').on('click', '.save-button', function() {
	    var component = $(this).parents("#user-profile");
        $.ajax({
            url: '/updateName',
            type: "POST",
            contentType: "application/json",
            beforeSend: setUpCsrf,
            data: JSON.stringify({
                firstName: component.find("input[name='firstName']").val(),
                lastName: component.find("input[name='lastName']").val(),
                position: component.find("input[name='position']").val()
            }),
            success: function(data){
                component.empty().append($(data).contents());
            }
        });
    });
	
});

function setUpCsrf(xhr) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
}


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