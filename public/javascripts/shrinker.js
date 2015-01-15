$(function () {
	var urlinput = $('#my-url-input');
	var urloutput = $('#my-url-output');
	var actionButton = $('#actionButton');

	urloutput.css({display: "none"});
	//urloutput.attr({onfocus: "this.select();",
	//	onmouseup: "return false;"});
	urloutput
		.focus(function () { $(this).select(); } )
		.mouseup(function (e) {e.preventDefault(); });

	actionButton.click(function () {
		$.ajax({
			type: 'POST',
			url: '/getShortUrl',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			processData: 'false',
			timeout: 1000,
			data: '{ "originalUrl": ' + JSON.stringify(urlinput.val()) + ' }'
		}).done(function (data) {
			urloutput.val(data.shortUrl);
			urloutput.css({display: "block"});
			urloutput.focus();
		});
	});
});