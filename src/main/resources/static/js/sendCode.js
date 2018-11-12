var countdown = 60;
$('#regis_code').click(function () {
    var email = $('#email').val();
    var reg = /\w+[@]{1}\w+[.]\w+/;
    var obj = $("#regis_code");
    if (!!!reg.test($('#email').val())) {
        alert('请输入正确的email地址');
        return;
    } else {
        alert("邮件发送成功，请注意查收");
        $.ajax({
            url: SendCodeUrl,
            type: 'POST',
            datatype: "json",
            data: {
                email: email
            },
            success: function (res) {
              trueCode=res;
            },
            error : function () {
                alert("邮件发送失败");
            },
            complete : function () {
                settime(obj);
            }
        });
    }
});
function settime(obj) { //发送验证码倒计时
    if (countdown == 0) {
        obj.attr('disabled',false);
        //obj.removeattr("disabled");
        obj.val("免费获取验证码");
        countdown = 60;
        return;
    } else {
        obj.attr('disabled',true);
        obj.val("重新发送(" + countdown + ")");
        countdown--;
    }
    setTimeout(function() {settime(obj) },1000)
}
