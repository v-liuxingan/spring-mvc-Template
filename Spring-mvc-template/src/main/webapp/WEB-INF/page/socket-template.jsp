<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>聊天室</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
    <link href="https://cdn.bootcdn.net/ajax/libs/semantic-ui/2.4.1/semantic.min.css" rel="stylesheet">

</head>

<style>
    #main {
        position: absolute;
        left: 0px;
        right: 0px;
        top: 0px;
        bottom: 0px;
        background-color: rgb(247, 247, 247);
    }

    .message.show {
        position: absolute;
        top: 0px;
        width: 500px;
        bottom: 200px;
        border: solid 1px #d4d4d5;
        background-color: white;
    }

    .message.show > .list {
        position: absolute;
        bottom: 0px;
        top: 30px;
        left: 0;
        right: 0;
        overflow-y: auto;
        margin-left: 10px;
        margin-right: 10px;
    }
    .message.show > .list .content {
        max-width: 350px;
    }
    .message.send {
        position: absolute;
        height: 200px;
        bottom: 10px;
        width: 500px;
        border: solid 1px #d4d4d5;
        background-color: white;
    }

    .user.list {
        position: absolute;
        bottom: 0px;
        top: 0px;
        margin-left: 500px;
        width: 150px;
        bottom: 10px;
        border: solid 1px #d4d4d5;
        background-color: gray;
        color: white;
    }

    .user.list > .list {
        position: absolute;
        bottom: 0px;
        top: 10px;
        left: 0;
        right: 0;
        overflow-y: auto;
        margin: 5px;
    }
    .user.list > .list>.item>.content{
        margin-top: 10px;
    }
    .content .name{
        color: darkgrey;
    }
    #main .container {

    }
</style>
<body>
<div id="main">
    <div class="ui text  container">
        <div class="message show ">
            <h5 class="ui top attached header">
                交流群(<span id="onlineCount"></span>)
            </h5>
            <div class="ui list">

            </div>
        </div>
        <div class="message send">

            <div class="ui form">
                <div class="field">
                    <textarea rows="6" style="border: none;resize: none;"></textarea>
                </div>
                <div class="ui submit right button " style="float: right">提交</div>
            </div>
        </div>
        <!--用户列表        -->
        <div class="user list">
            <div class="ui  list">

            </div>

        </div>
    </div>
</div>

<script>
    var user_item_template = '<div class="item">\n' +
        '                    <img class="ui avatar image" src="{0}">\n' +
        '                    <div class="content">\n' +
        '                        {1}\n' +
        '                    </div>\n' +
        '                </div>';

    var msg_item_template = ' <div class="item">\n' +
        '                    <img class="ui avatar image" src="{0}">\n' +
        '                    <div class="content">\n' +
        '                        <div class="user name" style="font-size: 0.9rem">{1}</div>\n' +
        '                        <div class="ui left pointing label white" >\n' +
        '{2}' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>';
    var msg_item_template_current='<div class="item">\n' +
    '\n' +
     '                  <img class="ui  right floated avatar image" src="{1}">\n' +
    '                    <div class="right floated content">\n' +
    '                        <div class="ui right pointing label blue">\n' +
    '                           {0}' +
    '                        </div>\n' +

    '\n' +
    '                    </div>\n' +
    '                </div>';
    let ws;
    let currentUser;
    <!--初始化链接-->
    $(function () {
        currentUser = prompt("输入用户名", "");
        ws = new WebSocket("ws://127.0.0.1:8080/chat?" + currentUser)
        ws.onmessage = receive;
        $(".submit.button").click(function () {
            ws.send($("textarea").val());// 发送消息
            $("textarea").val("");
        });
    });

    function receive(event) {
        let data = JSON.parse(event.data);
        if (data.type == "users") { // 当前在线用户
            $(".user.list>.list").empty();
            data.content.forEach((user) => {
                let $item = $(format(user_item_template, [user.head,user.name]));
                $item[0].data = user;
                $(".user.list>.list").append($item);
                $("#onlineCount").html(""+data.content.length);
            })
        } else if (data.type == "msg") { //接收消息
            let $item;
            if (data.content.user.name == currentUser) {
                $item = $(format(msg_item_template_current, [data.content.msg,data.content.user.head]));
            } else {
                $item = $(format(msg_item_template, [data.content.user.head,data.content.user.name, data.content.msg]));
            }
            $(".message.show>.list").append($item)
            //滚动条至底
            $(".message.show>.list")[0].scrollTop = $(".message.show>.list")[0].scrollHeight;

        }
    }


    function format(format, args) {
        return format.replace(/\{(\d+)\}/g, function (m, n) {
            return args[n] ? args[n] : m;
        });
    }

</script>
</body>
</html>
