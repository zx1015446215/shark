
function addbook() {
    $('.login-form-mask').fadeIn(100);
    $('.login-form').slideDown(200);
}
function closebookform() {
    $('.login-form-mask').fadeOut(100);
    $('.login-form').slideUp(200);
    location.reload();
}

//删除书籍
function deleteBooks() {
    var selects = document.getElementsByName("checkbook");
    var tbbody = document.getElementById("tbody");
    var arr = new Array();
    var num = 0;
    var id = null;
    for(var i=selects.length-1;i>=0;i--){
        if(selects[i].checked){
            id=selects[i].value;
            arr[num++]=id;
            tbbody.deleteRow(i);
        }
    }
    if(num==0){
        alert("未选中任何书籍");
        return;
    }else{
        if(!confirm("确认删除嘛")){
            return;
        }
    }
    $.ajax({
        url: deleteBookUrl,
        type: "POST",
        datatype: "json",
        data: {
            ids : arr.toString()
        },
        error: function(e) {
            console.log("error", e.statusText);
            alert("删除失败")
        },
        success: function(res) {
            alert("删除成功")
        },
        complete: function(d) {
            console.log("done");
        }
    });

}
//添加书籍
function savebook() {
    var name = $("#name").val(),
        author = $("#author").val(),
        company = $("#company").val(),
        publishtime = $("#publishtime").val(),
        total = $("#total").val();
    var type = document.getElementById("booktype").value;
    var mytable = document.getElementById("mytable").insertRow(1);
    var a = mytable.insertCell(0),
        x = mytable.insertCell(1),
        o = mytable.insertCell(2),
        y = mytable.insertCell(3),
        z = mytable.insertCell(4),
        e = mytable.insertCell(5),
        f = mytable.insertCell(6),
        g = mytable.insertCell(7);
    $.ajax({
        url: addbookUrl,
        type: "POST",
        datatype: "json",
        data: {
            name: name,
            type: type,
            author: author,
            company : company,
            publishtime : publishtime,
            total : total
        },
        error: function(e) {
            console.log("error", e.statusText);
        },
        success: function(res) {
            var num = res.status;
            if (num === 200) {
                a.innerHTML='<input type="checkbox" name="checkbook" value="1">';
                x.innerHTML=name;
                o.innerHTML=type;
                y.innerHTML=author;
                z.innerHTML=company;
                e.innerHTML=publishtime;
                f.innerHTML=total;
                g.innerHTML=total;
                alert("添加成功");
                location.reload();
            } else{
                alert("添加出错");
            }
        },
        complete: function(d) {
            console.log("done");
        }
    });
}

function funSelAll(){
    var selects=document.getElementsByName("checkbook");
    if(document.getElementsByName("checkAll")[0].checked==true){
        for(var i=0;i<selects.length;i++){
            selects[i].checked=true;
        }
    }else{
        for(var i=0;i<selects.length;i++){
            selects[i].checked=false;
        }
    }
}