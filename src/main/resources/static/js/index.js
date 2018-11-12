var limit = 10;
var currentPage = 0;
var total;
$(function(){
    bindList(0);

});

function nextPage() {
    bindList(currentPage * limit)
}
//获取文章信息并动态加载
function LoadSingleArticle(cid) {
    $.ajax({
        url: loadArticleUrl + cid,
        type: 'get',
        dataType: 'json',
        success: function (article) {
            var d = new Date( article.gtm_modified);
            var times=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
            var htmlText = "";
            htmlText += '<div class="bg">';
            htmlText += '<div class="article"><br/>';
            htmlText += '<h1>' + article.title + '</h1><br/>';
            htmlText += '<div><label>作者: </label>';
            htmlText += '<a>&nbsp;' + article.author + '</a>';
            htmlText += '</div>';
            htmlText += '<div><label>时间: </label>';
            htmlText += '<a>&nbsp;' + times + '</a>';
            htmlText += '</div>';
            htmlText += '<div><label>内容: </label>';
            htmlText += '<p class="content">' + article.content + '</p>';
            htmlText += '</div>';
            htmlText += '</div>';
            htmlText += '<br/>';
            htmlText += '<br/>';
            htmlText += '</div>';
            $("#inhere").html("");
            $("#inhere").append(htmlText);
            // document.getElementById("inhere").className = "con";
            getComment(cid);
        }
    });
}

//存储父节点为0的回复
$("#comment").on('click',function(){
    var content = $("#content").val();
    var parent_id = 0;
    $.ajax({
        url : savaComment,
        type : 'get',
        dataType : 'json',
        data : {
            parent_id :parent_id,
            content : content
        },
        success : function(){
            var htmlText = "";
            //获取时间
            var time = new Date();   // 程序计时的月从0开始取值后+1
            var m = time.getMonth() + 1;
            var data = time.getFullYear() + "-" + m + "-"
                + time.getDate() + " " + time.getHours() + ":"
                + time.getMinutes() + ":" + time.getSeconds();
            $("#content").html("");
            htmlText += '<div class="comment-info">';
            htmlText += '<header><img src="./images/niming.jpg"></header>';
            htmlText += '<div class="comment-right">';
            htmlText += '<h3>匿名</h3>';   //id
            htmlText += '<div class="comment-content-header">';
            htmlText += '<span><i class="glyphicon glyphicon-time"></i>'+data+'</span>';  //时间
            htmlText += '</div>';
            htmlText += '<p class="content">'+content+'</p>';       //评论内容
            htmlText += '<div class="comment-content-footer">';
            htmlText += '<div class="row">';
            htmlText += '<div class="col-md-10">';
            htmlText += '</div>';
            // htmlText += '<div class="col-md-2"><span class="reply-btn">回复</span>';
            // htmlText += '</div>';
            htmlText += '</div>';
            htmlText += '</div>';
            htmlText += '</div>';
            htmlText += '</div>';
            // $("#loadComment").append(htmlText);
            document.getElementById("content").value = "";
            $("#loadComment").prepend(htmlText);  //加入到最前面
        }
    });
});




// 获取文章评论信息并动态加载
function getComment(cid){
    document.getElementById("commentContainer").style.display = "block";
    $.ajax({
        url : loadArticleConmment+cid,
        type : 'get',
        dataType : 'json',
        success : function(rows) {
            //解析List<Tree<Comment>>
            var htmlText = "";
            for (i = 0; i < rows.length; i++) {
                //rows[i]  等价于Tree<Comment>
                var map = rows[i].attributes;
                htmlText += '<div class="comment-info">';
                htmlText += '<header><img src="./images/niming.jpg"></header>';
                htmlText += '<div class="comment-right">';
                htmlText += '<h3>匿名</h3>';   //id
                htmlText += '<div class="comment-content-header">';
                htmlText += '<span><i class="glyphicon glyphicon-time"></i>'+map["date"]+'</span>';  //时间
                htmlText += '</div>';
                htmlText += '<p class="content">'+rows[i].text+'</p>';       //评论内容
                htmlText += '<div class="comment-content-footer">';
                htmlText += '<div class="row">';
                htmlText += '<div class="col-md-10">';
                htmlText += '</div>';
                // htmlText += '<div class="col-md-2"><span class="reply-btn">回复</span>';
                // htmlText += '</div>';
                htmlText += '</div>';
                htmlText += '</div>';
                if(rows[i].hasChildren == true){
                    //开始子节点
                    htmlText += '<div class="reply-list">';
                    //此处的child等价于List<Tree<T>>
                    var child = rows[i].children;
                    for(j=0;j<child.length;j++){
                        var attr = child[j].attributes;
                        //一个小子节点
                        htmlText += '<div class="reply">';
                        htmlText += '<div><a href="javascript:void(0)">'+attr["user_id"]+'</a>:';
                        htmlText += '<span>'+child[j].text+'</span>';
                        htmlText += '</div>';
                        htmlText += '<p><span>'+attr["date"]+'</span>';
                        // htmlText += '<span class="reply-list-btn">回复</span></p>';
                        htmlText += '</p>';
                        //结束一个小子节点
                        htmlText += '</div>';
                    }

                    //结束子节点
                    htmlText += '</div>';
                }
                htmlText += '</div>';
                htmlText += '</div>';
            }
            $("#loadComment").html("");
            $("#loadComment").append(htmlText);
        }
    });
}

function bindList(offset) {
    $.ajax({
        url : loadAllArticle + offset,
        type : 'get',
        dataType : 'json',
        success : function(data) {
            var rows = data.rows;
            total = data.total;
            var htmlText = "";
            for (i = 0; i < rows.length; i++) {
                htmlText += '<div class="post-preview zx-articlemargin circle">';
                htmlText += '<a>';
                htmlText += '<h2 class="post-title">';
                htmlText += rows[i].title;
                htmlText += '</h2>';
                htmlText += '</a>';
                htmlText += '<p class="authorsize">作者：<a href="#">'
                    + rows[i].author
                    + '</a> &nbsp;&nbsp; '
                    + rows[i].gtm_modified + '</p>';
                htmlText += '<div class="authorsize" style="width:100%; height:90px; line-height:30px; overflow:hidden; position:relative;">'+rows[i].content+'</div>'
                htmlText += '<div><input type="button" onclick="'+'LoadSingleArticle('+rows[i].cid+')'+'" class="btn size-L btn-primary radius" value="更多内容"></div>';
                htmlText += '</div>';
                htmlText += '<hr>';
            }
            $("#incomeNum").append(htmlText);
            document.getElementById("flagLoad").style.display = "block";
            currentPage++;
            if (total <= currentPage * limit) {
                document.getElementById("flagLoaded").style.display = "block";
                document.getElementById("flagLoad").style.display = "none";
            }
        }
    });
}