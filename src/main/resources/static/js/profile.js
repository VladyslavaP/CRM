var fullCalendarHelper = {
    timeFormat: "h(:mm)t",
    midnight: "0:00:00",
    midnightFormat: "H:mm:ss",
    styleDayElement:function(date, cell){
       if (date.isBefore(moment())){
           cell.addClass('disabled');
       } else {
           cell.css("cursor", "cell");
       }
    },
    isMidnight:function(mom) {
        return mom.format(this.midnightFormat) == this.midnight;
    },
    formatEventElementTitle:function(event) {
        var prefix = this.isMidnight(event.start) ? "" : event.start.format(this.timeFormat) + " ";
        return prefix + event.title;
    },
    styleEventElement: function(event, element) {
       element.attr('title', fullCalendarHelper.formatEventElementTitle(event));
       element.css("cursor", "pointer")
    }
}

$(document).ready(function() {
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
	    $('#profile-detailed-info .date').toggle();
	});

	$('#profile-detailed-info').on('click', '.save-button', function() {
	    var component = $(this).parents("#profile-detailed-info");
        $.ajax({
            url: '/updateDetails',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                phoneNumber: component.find("input[name='phoneNumber']").val(),
                birthDate: component.find("input[name='birthDate']").val()
            }),
            success: function(data){
                component.empty().append($(data).contents());
                setUpBirthDatePicker();
            }
        });
    });

    $('#user-profile').on('click', '.save-button', function() {
	    var component = $(this).parents("#user-profile");
        $.ajax({
            url: '/updateName',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                firstName: component.find("input[name='firstName']").val(),
                lastName: component.find("input[name='lastName']").val(),
                position: component.find("input[name='position']").val(),
                email: profileUser.email
            }),
            success: function(data){
                component.empty().append($(data).contents());
            }
        });
    });

    function setUpDatePicker(element, startDate) {
        element.datepicker({
            startDate: startDate,
            format: "yyyy-mm-dd",
            weekStart: 1
        });
    }
    function setUpBirthDatePicker() {
        setUpDatePicker($('#profile-detailed-info .input-group.date'));
    }
    function setUpGoalDueDatePicker() {
        setUpDatePicker($("#profile-goals-info input[name='dueDate']"), '-0d');
    }
    setUpBirthDatePicker();
    setUpGoalDueDatePicker();

    function setUpTimePicker() {
        $('input.time-input').mask("99:99");
    };
    setUpTimePicker();

    $("#calendar").fullCalendar({
        selectable: false,
        editable: false,
        eventLimit: true,
        timeFormat: fullCalendarHelper.timeFormat,
        dayRender: fullCalendarHelper.styleDayElement,
        eventRender: fullCalendarHelper.styleEventElement,
        dayClick: function(date) {
            if(!this.hasClass('fc-past')) {
                $("#createEventForm input[name='date']").val(date.format("YYYY-MM-DD"));
                $("#createEventForm .show-date").html('for ' +  date.format("YYYY-MM-DD"));
                $("#createEventForm #createEvent").removeClass('disabled');
                $("#createEventForm").show();
                $("#updateEventForm").hide();
            }
        },
        eventClick: function(event) {
            if(event.start.isSameOrAfter(moment())) {
                $("#updateEventForm input[name='title']").val(event.title);
                $("#updateEventForm input[name='id']").val(event.id);
                $("#updateEventForm").show();
                $("#createEventForm").hide();
            }
        },
        events: profileUser.events.map(function(event) {
            return {id: event.id, title: event.title, start: getEventDateTime(event)}
        }),
        eventColor: "#5BC0DE"
    });

    $("#createEventForm").submit(function(e) {
        e.preventDefault();
        var form = $(this);
        $.ajax({
            url: '/users/' + profileUser.id + "/events/",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify({
                title: form.find("input[name='title']").val(),
                date: form.find("input[name='date']").val(),
                time: form.find("input[name='time']").val()
            }),
            success: function(data){
                cleanUpForm($(form));
                $(form).find(".show-date").html("");
                $(form).find("#createEvent").addClass('disabled');
                $('#calendar').fullCalendar('unselect');
                $('#calendar').fullCalendar('renderEvent', createCalendarEventData(data), true);
            }
        });
    });

    $("#updateEventForm #deleteEvent").click(function(e) {
        e.preventDefault();
        var form = $(this).parent();
        var eventId = form.find("input[name='id']").val();
        $.ajax({
            url: '/users/' + profileUser.id + "/events/" + eventId,
            type: "DELETE",
            success: function(){
                cleanUpForm($(form));
                $('#calendar').fullCalendar('removeEvents', eventId);
            }
        });
    });

    $("#updateEventForm #updateEvent").click(function(e) {
        e.preventDefault();
        var form = $(this).parent();
        var eventId = form.find("input[name='id']").val();
        $.ajax({
            url: '/users/' + profileUser.id + "/events/" + eventId,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                title: form.find("input[name='title']").val()
            }),
            success: function(data){
                cleanUpForm($(form));
                var calendarEvent = $('#calendar').fullCalendar('clientEvents', eventId)[0];
                calendarEvent.title = data.title;
                $('#calendar').fullCalendar('updateEvent', calendarEvent);
            }
        });
    });

    $("#profile-goals-info").on("submit", "#createGoalForm", function(e){
        e.preventDefault();
        var component = $(this).parents("#profile-goals-info");
        var form = $(this);
        $.ajax({
            url: '/users/' + profileUser.id + "/goals/",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify({
                title: form.find("input[name='title']").val(),
                dueDate: form.find("input[name='dueDate']").val(),
                parent: form.find("input[name='parent']").val()
            }),
            success: function(data){
                renderGoalsComponent(component, data);
            }
        });
    });

    function renderGoalsComponent(component, data) {
        component.empty().append($(data).contents());
        setTimeLeft();
        setUpTimePicker();
        setUpGoalDueDatePicker();
    }

    $("#profile-goals-info").on("click", "#updateGoalForm #deleteGoal", function(e) {
        e.preventDefault();
        var component = $(this).parents("#profile-goals-info");
        var form = $(this).parent();
        $.ajax({
            url: '/users/' + profileUser.id + "/goals/" + form.find("input[name='id']").val(),
            type: "DELETE",
            success: function(data){
                renderGoalsComponent(component, data);
            }
        });
    });

    $("#profile-goals-info").on("click", "#updateGoalForm #updateGoal", function(e) {
        e.preventDefault();
        var component = $(this).parents("#profile-goals-info");
        var form = $(this).parent();
        $.ajax({
            url: '/users/' + profileUser.id + "/goals/" + form.find("input[name='id']").val(),
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                title: form.find("input[name='title']").val(),
                dueDate: form.find("input[name='dueDate']").val(),
            }),
            success: function(data){
                renderGoalsComponent(component, data);
            }
        });
    });

    $("#profile-goals-info").on("click", "#updateGoalForm #createSubGoal", function(e) {
        e.preventDefault();
        $(this).parent().hide();
        $("#createGoalForm").show()
            .find("input[name='parent']").val($(this).siblings("input[name='id']").val());
    });

   $("#profile-goals-info").on("click", "#in-progress-goals .progress.editable", function(e){
        var goalId = $(this).closest(".goal-block").data("id");
        var posX = $(this).offset().left;
        var progress = Math.round((e.pageX - posX) / $(this).width() * 100);
        if(progress != 100 || confirm("Are you sure, that this goal is completed?"))
            updateGoalProgress(goalId, progress);
    });


    function updateGoalProgress(goalId, progress) {
        var component = $("#profile-goals-info");
        $.ajax({
            url: '/users/' + profileUser.id + "/goals/" + goalId + "/progress/" + progress,
            type: "POST",
            success: function(data){
                renderGoalsComponent(component, data);
            }
        });
    }

    $("#profile-goals-info").on("click", "#in-progress-goals .goal-block .goal-title", function(e) {
        $("#createGoalForm").hide();
        $("#updateGoalForm").show();
        var goalRow = $(this).closest(".goal-block");
        $("#updateGoalForm input[name='id']").val(goalRow.data("id"));
        $("#updateGoalForm input[name='title']").val(goalRow.find(".goal-title").html());
        $("#updateGoalForm input[name='dueDate']").val(goalRow.find(".goal-due-date").data('due-date'));

        if(goalRow.is(".child"))
            $("#updateGoalForm #createSubGoal").hide();
        else
            $("#updateGoalForm #createSubGoal").show();
    });

    $("#profile-goals-info").on("click", "#in-progress-goals .expand-icon", function(e){
        var childGoals = $(this).closest(".goal-block").next(".child-goals");
        if(childGoals.is(':visible'))
            childGoals.fadeOut();
        else
            childGoals.fadeIn();
    });

    function setTimeLeft() {
        var now = moment().today;
        $("#in-progress-goals .goal-due-date").each(function() {
            var originalValue = $(this).html();
            var dueDate = moment(originalValue);
            $(this).html(dueDate.diff(now, 'days') + ' days left').data('due-date', originalValue);
        });
    }
    setTimeLeft();

    $(document).mouseup(function (e) {
        var calendar = $("#profile-calendar");
        var goals = $("#profile-goals-info");
        var datepicker = $(".datepicker");
        if (!calendar.is(e.target) && calendar.has(e.target).length === 0) {
            $("#updateEventForm").hide();
            $("#createEventForm").show();
        }
        if (!goals.is(e.target) && goals.has(e.target).length === 0
                && !datepicker.is(e.target) && datepicker.has(e.target).length === 0) {
            $("#updateGoalForm").hide();
            $("#createGoalForm").show();
        }

    });

    function cleanUpForm(form) {
        form.find("input:text").val("");
        $("#createEventForm").show();
        $("#updateEventForm").hide();
    }

    function createCalendarEventData(data) {
        return {
            id: data.id,
            title: data.title,
            start: getEventDateTime(data)
        };
    }

    function getEventDateTime(data) {
        var result = data.date;
        if(data.time)
            result += "T" + data.time;
        return result;
    }
	
});