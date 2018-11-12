function borrowbook(book_id) {
    // 完成预约书籍的动作
    //1.页面显示数量减少1，不重新从数据库中读取
    var remain = document.getElementById(book_id);
    var name = document.getElementsByName(book_id).item(0);
    if(remain.innerHTML == 0){
        alert("库存为0，不可借阅");
        return;
    }
    $.ajax({
        //与后台交互
        url: borrowBookUrl,
        type: "get",
        datatype: "json",
        data: {
            id : book_id
        },
        error: function(e) {
            console.log("error", e.statusText);
            alert("预约失败"+e.msg);
        },
        success: function(res) {
            name.value="取消预约";   //改变值
            remain.innerHTML = remain.innerHTML-1;
            name.onclick = function (ev) {   //改变动作
                cancelbook(book_id);
            };
            alert("预约成功");
        },
        complete: function(d) {
            console.log("done");
        }
    });
}

/**
 * 取消预约
 * @param book_id
 */
function cancelbook(book_id) {
    //将图标改成预约
    var remain = document.getElementById(book_id);
    var name = document.getElementsByName(book_id).item(0);
    $.ajax({
        //与后台交互
        url: cancelBookUrl,
        type: "GET",
        datatype: "json",
        data: {
            id : book_id
        },
        error: function(e) {
            console.log("error", e.statusText);
            alert("取消预约失败");
        },
        success: function(res) {
            name.value="预约";   //改变值
            remain.innerHTML = remain.innerHTML-(-1);
            name.onclick = function (ev) {   //改变动作
                borrowbook(book_id);
            };
            alert("取消预约成功");
        },
        complete: function(d) {
            console.log("done");
        }
    });
}


//显示添加书籍页面
function addbook() {
    $('.login-form-mask').fadeIn(100);
    $('.login-form').slideDown(200);
}
//关闭添加书籍页面
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

/**
 * 查看是否全部选中
 */
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

/**
 * 条件查询书籍
 */
function selectNeedBook(selecttype) {
    var name = document.getElementById("selectName");
    var author = document.getElementById("selectAuthor");
    var type = document.getElementById("selectType");
    if(selecttype!=undefined){
        name.value = "";
        author.value = "";
        type.value = "";
    }
    var cls = "";
    $.ajax({
        url: selectNeedBookUrl,
        type: "POST",
        datatype: "json",
        data: {
            name : name.value,
            author : author.value,
            type : type.value
        },
        error: function(e) {
            console.log("error", e.statusText);
            alert("查询失败")
        },
        success: function(res) {
            $("#tbody").html("");  //  如果第一行保留的话，添加tr:not(:first)
            var j=1;
            var htmlText = "";
            if(res.data == ""){
                alert("暂无所查书籍");
                return;
            }
            jQuery.each(res.data, function(i,item){   //遍历list
                if(item.remain==0){
                    cls = 'danger';
                }else{
                    cls = 'active';
                }
                if(item.flag){
                    var temp1 = '取消预约';
                    var temp2 = 'cancelbook('+item.id+')';
                }else{
                    var temp1 = '预约';
                    var temp2 = 'borrowbook('+item.id+')';
                }
                htmlText += '<tr class="'+cls+'">';
                htmlText += '<td>'+item.name+'</td>';
                htmlText += '<td>'+item.type+'</td>';
                htmlText += '<td>'+item.author+'</td>';
                htmlText += '<td>'+item.company+'</td>';
                htmlText += '<td>'+item.publishtime+'</td>';
                htmlText += '<td id="'+item.id+'">'+item.remain+'</td>';
                htmlText += '<td>'+item.total+'</td>';
                htmlText += '<td><input id="'+item.id+'" name="'+item.id+'" type="button" class="btn btn-success" onclick="'+temp2+'" value="'+temp1+'"></td>';
            });
            $("#tbody").append(htmlText);
        },
        complete: function(d) {
            console.log("done");
        }
    });
}