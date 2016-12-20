$( document ).ajaxSend(function( event, request, settings ) {
   setUpCsrf(request);
})

function setUpCsrf(xhr) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
}