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
                setUpDatePicker();
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

    function setUpDatePicker() {
        $('#profile-detailed-info .input-group.date').datepicker({
            format: "yyyy-mm-dd"
        });
    };
    setUpDatePicker();

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

    $(document).mouseup(function (e) {
        var calendar = $("#profile-calendar")
        if (!calendar.is(e.target) && calendar.has(e.target).length === 0) {
            $("#createEventForm").hide();
            $("#updateEventForm").hide();
        }
    });

    function cleanUpForm(form) {
        form.find("input:text").val("");
        form.hide();
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