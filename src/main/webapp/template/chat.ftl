<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чат</title>
    <#include "parts/script.ftl">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script>
        var webSocket;
        function connect() {
            webSocket = new SockJS("http://localhost:8080/ws/chat");
            webSocket.onopen = function(){
                sendMessage("/Login");
            };
            webSocket.onmessage = function receiveMessage(response) {

                let data = response['data'];
                let json = JSON.parse(data);
                var decoded = jwt_decode(json['from']);


                console.log(decoded.username);
                $('#messagesList').first().after("<li>" + decoded.username  + ' : ' + json['text'] + "</li>")
            };


            // sendMessage("/Login");
        }

        function getCookie(name) {
            let matches = document.cookie.match(new RegExp(
                "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : undefined;
        }

        function sendMessage(text) {
            let message = {
                "text": text,
                "from": getCookie('X-Authorization'),
                "room": ${room}
            };

            // alert(JSON.stringify(message))
            webSocket.send(JSON.stringify(message));
            document.getElementById('message').value = "";
        }
    </script>
</head>
<body onload="connect()" onpagehide="sendMessage('/Exit')">
<div>
    <label for="message">Текст сообщения</label>
    <input name="message" id="message" placeholder="Сообщение">
    <button onclick="sendMessage($('#message').val())">Отправить</button>
    <h3>Сообщения</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>
</html>
