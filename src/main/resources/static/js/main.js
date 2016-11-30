var requestManager = new RequestManager();

function RequestManager() {

	this.dispatcherBaseUrl = 'http://dispatcher.service.local/request.php?';
	this.serviceName = '';
	this.endpointName = '';
	this.method = 'get';
	this.params = [];

	this.success = function(response) {
		console.log('Success : ' + response);
	};

	this.fail = function(response) {
		console.log('Fail : ' + response);
	};

	this.setServiceName = function (service) {
		this.serviceName = service;
	};

	this.setEndpointName = function (endpoint) {
		this.endpointName = endpoint;
	};

	this.setMethod = function (requestMethod) {
		this.method = requestMethod;
	};

	this.setParams = function (requestParams) {
		this.params = requestParams;
	};

	this.call = function (successFunction, failFunction) {
		if (successFunction) {
			this.success = successFunction;
		}

		if (failFunction) {
			this.fail = failFunction;
		}

		var requestUrl = this.dispatcherBaseUrl;
		if (!this.serviceName || !this.endpointName) {
			return false;
		}

		requestUrl += 'service=' + this.serviceName + '&endpoint=' + this.endpointName;


		$.ajax(requestUrl,
			{
				type: this.method.toUpperCase(),
				data: this.params,
				contentType: "application/json",
				success: this.success,
				statusCode: {
					403 : function() {
						alert('Forbidden. Insuficient permissions.');
					}
				},
				error: this.error
			}
		);
	};

}