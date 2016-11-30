<!DOCTYPE html>
<html>
<head>
	<title>Login page | CMR system</title>
	<script src="./js/jquery-2.2.4.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="./css/bootstrap.min.css">

	<script src="./js/main.js"></script>
	<script src="./js/login.js"></script>

	<link rel="stylesheet" href="./css/login.css">
</head>

<body>
	<div class="login-page">
		<img class="login-bg" src="./images/background.jpg">
		<div class="login-popup-container">
			<div class="login-popup">
				<div class="login-inner">
					<div class="header-logo">
						<div class="logo">CRM system</div>
					</div>
					<div class="form">
                        <p class="info">Please enter your login information:</p>
						<form id="login-form" action="/login" method="post">
							<div class="field">
                                <input class="input" data-val="true" data-val-length="Username must be less than 75 characters" data-val-length-max="75" data-val-required="It looks like you forgot to include your username or email. Please try re-entering." id="email" name="email" placeholder="Email" tabindex="1" type="text" value="">
                            </div>
                            <div class="field">
                                <input autocomplete="off" class="input" data-val="true" data-val-required="It looks like you forgot to include your password. Please try re-entering." id="password" name="password" placeholder="Password" tabindex="2" type="password">
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="submit">
                                <button class="submit-button" tabindex="3">Log in</button>
                            </div>     
						</form>                                
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>