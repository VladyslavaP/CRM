<!DOCTYPE html>
<html>
<head>
	<title>Your profile | CRM system</title>
	<script src="./js/jquery-2.2.4.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="./css/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="./tooltipster-master/css/tooltipster.css" />
	<script type="text/javascript" src="./tooltipster-master/js/jquery.tooltipster.min.js"></script>

	<script src="./js/main.js"></script>
	<script src="./js/profile.js"></script>

	<link rel="stylesheet" href="./css/main.css">
	<link rel="stylesheet" href="./css/profile.css">
</head>

<body>

	<div class="page-container container-fluid">
		<div class="header container-fluid">
			<h1 class="ja-logo">CRM system</h1>
			<h1 class="profile-sublogo">Profile</h1>
			<div id="main-menu">
				<div class="small-menu">
					<div class="small-menu-item">
						Your profile
					</div>

					<div class="small-menu-item active">
						<a href="/coworkers.html">All coworkers</a>
					</div>

					<div class="clearfix"></div>
				</div>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="main-info-container container-fluid">
			<!-- MAIN PAGE INFO CONTAINER -->

			<div class="row block-panel">
				<div class="block-panel-inner" id="user-profile">
					<div class="col-lg-6 col-md-6 col-sm-6" id="profile-main-info">
						<div class="block-panel-inner">

							<div class="profile-user">
								<img src="./images/oleksiy.png" class="profile-photo" />

								<div class="profile-userinfo-block" role="info">
									<div class="profile-fullname" id="fullname-display">Oleksiy Kravchenko</div>
									<div class="profile-position" id="fullname-position">Junior Full-stack Developer</div>
								</div>

								<div class="profile-userinfo-block" role="form">
									<div class="profile-fullname"><input type="text" id="fullname" class="input input-small" value="Oleksiy Kravchenko"/></div>
									<div class="profile-position"><input type="text" id="position" class="input input-small" value="Junior Full-stack Developer"/></div>
								</div>

							</div>

						</div>
					</div>

					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="block-panel-inner" id="badges">

							<div class="badge_ tooltipster" title="Thank you for support during onboarding! Anton."><img src="./images/badge.png" /></div>
							<div class="clearfix"></div>

						</div>
					</div>

					<div class="edit-button">
						<img src="./images/pencil.png" />
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

			<style>
			#badges {
				margin-right: 60px;
			}
			#badges > .badge_ {
				float: right;
			}
			</style>


			<div class="col-lg-4 col-md-4 col-sm-4 block-panel" id="profile-detailed-info">
				<div class="block-panel-inner">
					<div class="small-heading" style="margin-bottom:25px;">Your information</div>

					<div class="medium-heading">Phone:</div>
					<div class="medium-value">+38 (099) 123-45-67</div>

					<div class="medium-heading">Email:</div>
					<div class="medium-value">alexiskravchenko@gmail.com</div>

					<div class="medium-heading">Date of birth:</div>
					<div class="medium-value" style="margin-bottom:100px;">
						18 November 1995
						<div class="small-heading" style="font-size:13px;">(your birthday is in 161 days)</div>
					</div>

					<div class="edit-button">
						<img src="./images/pencil.png" />
					</div>

				</div>
			</div>

			<div class="col-lg-8 col-md-8 col-sm-8 block-panel" id="profile-calendar">
				<div class="block-panel-inner">
					<div class="small-heading">Your calendar</div>

					<div class="calendar-header">
						<div class="calendar-month-picker">
							<div class="month-arrow">
								<img src="./images/arrow-left.png" />
							</div>
							<div class="month-current">
								June 2016
							</div>
							<div class="month-arrow">
								<img src="./images/arrow-right.png" />
							</div>
						</div>

						<div class="calendar-add-event">
							Add Event <img src="./images/add-icon.png" />
						</div>
						<div class="clearfix"></div>
					</div>

					<div class="calendar">
						<hr>

						<div class="row">
							<div class="week-day-cell">SUN</div>
							<div class="week-day-cell">MON</div>
							<div class="week-day-cell">TUE</div>
							<div class="week-day-cell">WED</div>
							<div class="week-day-cell">THU</div>
							<div class="week-day-cell">FRI</div>
							<div class="week-day-cell">SAT</div>
						</div>

						<hr>

						<div class="row">
							<div class="week-day-cell passed">29</div>
							<div class="week-day-cell passed">30</div>
							<div class="week-day-cell passed">31</div>
							<div class="week-day-cell passed">1</div>
							<div class="week-day-cell passed tooltipster" title="Holiday Whooho!" >2
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
									<span class="orange-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell passed">3</div>
							<div class="week-day-cell passed">4</div>
						</div>

						<hr>

						<div class="row">
							<div class="week-day-cell passed tooltipster" title="Company Day 2016">5
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell passed">6</div>
							<div class="week-day-cell passed">7</div>
							<div class="week-day-cell">8</div>
							<div class="week-day-cell tooltipster" title="Diploma preparation">9
								<div class="bullets">
									<span class="orange-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell tooltipster" title="Diploma defence">10
								<div class="bullets">
									<span class="orange-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell">11</div>
						</div>

						<hr>

						<div class="row">
							<div class="week-day-cell" title="Summer Vacation">12</div>
							<div class="week-day-cell tooltipster" title="Summer Vacation">13
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell tooltipster" title="Summer Vacation">14
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell tooltipster" title="Summer Vacation">15
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell tooltipster" title="Summer Vacation">16
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell tooltipster" title="Summer Vacation">17
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell">18</div>
						</div>

						<hr>

						<div class="row">
							<div class="week-day-cell">19</div>
							<div class="week-day-cell">20</div>
							<div class="week-day-cell">21</div>
							<div class="week-day-cell tooltipster" title="Tooltip message...sad.as.dsa.d.sad.sad.sa.dasdasdkas asd askd aks">22
								<div class="bullets">
									<span class="green-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell">23</div>
							<div class="week-day-cell">24</div>
							<div class="week-day-cell">25</div>
						</div>

						<hr>

						<div class="row">
							<div class="week-day-cell">26</div>
							<div class="week-day-cell">27</div>
							<div class="week-day-cell tooltipster" title="Tooltip message...sad.as.dsa.d.sad.sad.sa.dasdasdkas asd askd aks">28
								<div class="bullets">
									<span class="blue-bullet">&nbsp;</span>
								</div>
							</div>
							<div class="week-day-cell">29</div>
							<div class="week-day-cell">30</div>
							<div class="week-day-cell passed">1</div>
							<div class="week-day-cell passed">2</div>
						</div>

						<hr>
					</div>

				</div>
			</div>

			<style>
			.input {
			    margin-top: 10px;
			    width: 100%;
			    height: 48px;
			    -webkit-border-radius: 3px;
			    border-radius: 3px;
			    color: #333;
			    border: 0;
			    padding: 10px 8px;
			    font-size: 16px;
			    line-height: 24px;
			    border: 1px solid #ccc;
			    -webkit-transition: border .1s linear,box-shadow .1s linear;
			    -moz-transition: border .1s linear,box-shadow .1s linear;
			    -o-transition: border .1s linear,box-shadow .1s linear;
			    transition: border .1s linear,box-shadow .1s linear;
			}
			.input.input-small {
				height: 25px;
			}

			.profile-userinfo-block[role="form"] {
				display: none;
				width: 250px;
			}


			.block-panel-inner {
				position: relative;
			}
			.block-panel-inner > .edit-button {
				position: absolute;
				top: 10px;
				right: 10px;
				cursor: pointer;
			}

			.calendar-header {
				margin-top: 10px;
				margin-bottom: 20px;
			}
			.calendar-month-picker {
				display: inline-block;
			}
			.month-arrow, .month-current {
				display: inline-block;
			}
			.month-arrow {
				height: 25px;
			}
			.month-arrow > img {
				margin-bottom: 5px
			}
			.month-arrow:first-child {
				margin-right: 10px;
			}
			.month-arrow:last-child {
				margin-left: 10px;
			}
			.month-current {
				font-size: 18px;
				margin-top: 4px;
			}
			.calendar-add-event {
				float:right;
				margin-top: 10px;
				color: #5dc369;
				cursor: pointer;
			}
			.calendar-add-event > img {
				margin-left: 10px;
			}
			.calendar > hr {
				margin-top: 10px;
				margin-bottom: 15px;
			}
			.week-day-cell {
				width: 14.25%;
				float: left;
				text-align: center;
				color: #333;
				position: relative;
			}
			.week-day-cell.passed {
				opacity: 0.5;
			}
			.bullets {
				position: absolute;
			    width: 100%;
			}
			.green-bullet {
				display: inline-block;
				height: 6px;
				width: 6px;
				background-image: url('./images/green-bullet.png');
			}
			.orange-bullet {
				display: inline-block;
				height: 6px;
				width: 7px;
				background-image: url('./images/orange-bullet.png');
			}
			.blue-bullet {
				display: inline-block;
				height: 6px;
				width: 6px;
				background-image: url('./images/blue-bullet.png');
			}
			.tooltipster {
				cursor: pointer;
			}
			</style>


			<div class="col-lg-12 col-md-12 col-sm-12 block-panel" id="profile-goals-info">
				<div class="block-panel-inner">
					<div class="small-heading">Your goals</div>

					<div class="small-menu">
						<div class="small-menu-item active">
							Goals in progress
							<span class="badge red-badge" id="goals-in-progress">2</span>
						</div>

						<div class="small-menu-item">
							Completed
						</div>

						<div class="clearfix"></div>
					</div>

					<div class="goal-block row">
						<div class="goal-image col-lg-1 col-md-1 col-sm-1">
							<img src="./images/angular.png" />
						</div>

						<div class="goal-title col-lg-3 col-md-3 col-sm-3">
							Improve in AngularJS
						</div>

						<div class="goal-progress col-lg-6 col-md-6 col-sm-6">
							<div class="progress">
							    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="55" aria-valuemin="0" aria-valuemax="100" style="width:55%">
							      55%
							    </div>
							</div>
						</div>

						<div class="goal-due-date col-lg-2 col-md-2 col-sm-2 due-date-red">
							1 week left
						</div>
					</div>

					<hr>

					<div class="goal-block row">
						<div class="goal-image col-lg-1 col-md-1 col-sm-1">
						<img src="./images/mountains.png" />
						</div>

						<div class="goal-title col-lg-3 col-md-3 col-sm-3">
							Climb even higher
						</div>

						<div class="goal-progress col-lg-6 col-md-6 col-sm-6">
							<div class="progress">
							    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:70%">
							      70%
							    </div>
							</div>
						</div>

						<div class="goal-due-date col-lg-2 col-md-2 col-sm-2">
							2 months left
						</div>
					</div>

				</div>
			</div>


			<div class="col-lg-12 col-md-12 col-sm-12 block-panel" id="profile-questionnaires-info">
				<div class="block-panel-inner">
					<div class="small-heading">Your questionnaires</div>

					<div class="small-menu">
						<div class="small-menu-item active">
							Pending questionnaires
							<span class="badge red-badge" id="pending-questionnaires">2</span>
						</div>

						<div class="clearfix"></div>
					</div>

					<div class="questionnaire-block row">
						<div class="profile-small-user col-lg-4 col-md-4 col-sm-4">
							<img src="./images/raven.png" class="profile-small-photo" />

							<div class="profile-userinfo-block">
								<div class="profile-small-fullname">Raven Darkholme</div>
								<div class="profile-small-position">HR manager</div>
							</div>
						</div>

						<div class="questionnaire-info col-lg-6 col-md-6 col-sm-6">
							We would love to know your opinion about our company outling last week. Help us so improve next time!
						</div>

						<div class="questionnaire-due-date col-lg-2 col-md-2 col-sm-2 due-date-red">
							3 days left
						</div>
					</div>

					<hr>

					<div class="questionnaire-block row">
						<div class="profile-small-user col-lg-4 col-md-4 col-sm-4">
							<img src="./images/kurt.png" class="profile-small-photo" />

							<div class="profile-userinfo-block">
								<div class="profile-small-fullname">Kurt Wagner</div>
								<div class="profile-small-position">Head of management</div>
							</div>
						</div>

						<div class="questionnaire-info col-lg-6 col-md-6 col-sm-6">
							Couple of week ago we had Scrum masters changes in most of the teams. Was this helpful? Do we need to do this on ...
						</div>

						<div class="questionnaire-due-date col-lg-2 col-md-2 col-sm-2">
							10 days left
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>

</body>
</html>