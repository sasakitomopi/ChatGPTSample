window.onload = function(){
	$('#send-button').on('click', function() {
		const message = $('#input-field').val();
		sendRequest(message);
	});
};
//OpenAI APIにリクエストを送信する処理
function sendRequest(message) {
	$.ajax({
		url: "/openAiChat",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			message : message
		})
	}).done(function(response) {
		$('.chat-response-area').html('<p>' + insertNewlines(response) + '</p>');
	}).fail(function() {
		console.log("きてない");
	});
}

// 。(句点)ごとに改行コードを挿入する。
function insertNewlines(str) {
  var result = '';
  var len = str.length;
  var i = 0;
  while (i < len) {
    var substr = str.substring(i, i + 10);
    var idx = substr.lastIndexOf('。');
    if (idx !== -1) {
      result += substr.substring(0, idx + 1) + '<br>';
      i += idx + 1;
    } else {
      result += substr;
      i += 10;
    }
  }
  return result;
}