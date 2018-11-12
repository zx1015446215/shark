function addArticleform() {
    $('.login-form-mask').fadeIn(100);
    $('.login-form').slideDown(200);
}
function closeArticleform() {
    $('.login-form-mask').fadeOut(100);
    $('.login-form').slideUp(200);
}

/**
 * 添加文章
 */
function addArticle() {
    var title = document.getElementById("title");
    var author = document.getElementById("author");
    var type = document.getElementById("articlecategories");
    var content = document.getElementById("content").value;
    content = content.replace(/\r\n/g, '<br/>'); //IE9、FF、chrome
    content = content.replace(/\n/g, '<br/>'); //IE7-8
    content = content.replace(/\s/g, ' '); //空格处理
    $.ajax({
        url: addArticleUrl,
        type: "POST",
        datatype: "json",
        data: {
            title : title.value,
            author : author.value,
            categories : type.value,
            content : content
        },
        error: function(e) {
            console.log("error", e.statusText);
            alert("插入失败");
        },
        success: function(res) {
            location.reload();
        },
        complete: function(d) {
            console.log("done");
        }
    });
}
/**
 * 确认修改文章
 */
function makeSureUpdateAriticle(cid,title,autho) {
    var btn = document.getElementById(cid);
    var m = document.getElementsByName(cid).item(0).getElementsByTagName("input");
    var n = document.getElementsByName(cid).item(0).getElementsByTagName("textarea");
    var title = m[0].value,
        author = m[1].value,
        categories = m[2].value,
        content = n[0].value;
    content = content.replace(/\r\n/g, '<br/>'); //IE9、FF、chrome
    content = content.replace(/\n/g, '<br/>'); //IE7-8
    content = content.replace(/\s/g, ' '); //空格处理
    $.ajax({
        url : updateArticleUrl,
        type : 'post',
        datatype: "json",
        data : {
            cid : cid ,
            title: title ,
            categories: categories ,
            author: author,
            content: content
        },
        error: function (e) {
            alert("修改失败");
        },
        success: function (res) {
            btn.value="修改";   //改变值
            btn.onclick = function (ev) {   //改变动作
                updateArticle(cid);
            };
            alert("修改成功");
            location.reload();
        },
        complete: function(d) {
            console.log("done");
        }
    });
}
/**
 * 修改文章
 */
function updateArticle(cid) {
    var btn = document.getElementById(cid);
    var b = $("input[type='button'][id='"+cid+"']").parent();  //td
    var a = b.siblings();  //获取td的兄弟节点
    a[0].innerHTML="<input type='text' value='"+a[0].innerText+"'/>";
    a[1].innerHTML="<input type='text' value='"+a[1].innerText+"'/>";
    var s = a[2].getElementsByTagName("textarea");
    s[0].disabled=false;
    s[0].value=s[0].placeholder;
    a[3].innerHTML="<input type='text' value='"+a[3].innerText+"'/>";
    btn.value="确认";   //改变值
    btn.onclick = function (ev) {   //改变动作
        makeSureUpdateAriticle(cid);
    };
}