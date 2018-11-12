
$("#message_btn").click(function() {
  var name = $("#name").val(),
    stu_card = $("#stu_card").val(),
    phone = $("#phone").val(),
    sex = $("#sex").val(),
    department = $("#department").val(),
    introduction = $("#introduction").val();
  $.ajax({
    url: UserMessageUrl,
    type: "POST",
    dataType: 'json',
    data: {
      name: name,
      stu_card: stu_card,
        phone: phone,
        sex: sex,
        department: department,
        introduction: introduction
    },
    error: function(e) {
      console.log("error", e.statusText);
    },
    success: function(res) {
      var num = res.status;
        alert("信息更改成功，即将跳转到主界面")
        location.href = "/yummy/index";

    },
    complete: function(d) {
      console.log("done");
    }

  });

});
