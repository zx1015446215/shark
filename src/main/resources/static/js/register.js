
$('#regis_btn').click(function () {
    var $k = $('.form-control'),
        username = $('#username').val(),
        email = $('#email').val(),
        pass = $('#password').val(),
        role = 'user';
    // console.log($k);
    for (let i = 0; i < $k.length; i++) {
        if ($k[i].value === '') {
            alert('请输入完整的信息');
            return;
        }
    }
    var reg = /\w+[@]{1}\w+[.]\w+/;
    if (!!!reg.test($('#email').val())) {
        alert('请输入正确的email地址');
        return;
    } else if ($('#password').val() !== $('#sure_pass').val()) {
        alert('请重新确认密码');
        return;
    } else if($('#code').val() !== trueCode) {
        alert('验证码错误');
        return;
    }else{
        $.ajax({
            url: RegisterUrl,
            type: 'POST',
            datatype: "json",
            data: {
                username: username,
                email: email,
                password: pass,
                role: role
            },
            success: function (res) {
                var num = res.status;
                if(num == 200){
                    alert("注册成功，即将返回登录页面");
                    location.href="/index/login";
                }else{
                     alert("用户名已存在，请重新输入");
                }
            },
        });
    }
});
//打开会员注册
$("#Regist_start_").click(function() {
    $("#regist_container").show(500);
    $("#_close").animate({
        height: '40px',
        width: '40px'
    }, 500);
});
