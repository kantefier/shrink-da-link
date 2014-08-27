$(function () {
	var urlinput = $('#my-url-input');
	var urloutput = $('#my-url-output');
	var actionButton = $('#actionButton');

	actionButton.click(function () {
		$.ajax({
			type: 'POST',
			url: '/getShortUrl',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			processData: 'false',
			timeout: 2500,
			data: '{ "originalUrl": ' + JSON.stringify(urlinput.val()) + ' }'
		}).done(function (data) {
			urloutput.text(data.shortUrl);
		});
	});
})