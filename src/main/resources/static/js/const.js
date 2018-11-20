// const uu = "http://localhost:8080";
const uu = "http://www.zhxshark.com";
const loginUrl =  uu+"/index/login";   //登录
const RegisterUrl = uu+"/register";    //注册
const SendCodeUrl = uu+"/sendCode";    //发送验证码
const addArticleUrl= uu+"/article/addArticle";   //添加文章
const updateArticleUrl= uu+"/article/updateArticle";   //更新文章
const deleteBookUrl= uu+"/book/deletebook";    //删除书籍(管理员操作)
const borrowBookUrl= uu+"/book/borrowbook";     //借书
const cancelBookUrl= uu+"/book/cancelbook";     //还书
const selectNeedBookUrl= uu+"/book/selectNeedbook";  //按需求查询书籍
const UserMessageUrl= uu+"/reader";   //查询读者信息
const loadArticleUrl =  uu+"/blog/open/post/";
const loadArticleConmment =  uu+"/comment/list/";  //获取文章评论
const savaComment =  uu+"/comment/saveComment";   //保存评论信息
const loadAllArticle =  uu+"/blog/open/list?type=1&limit=10&offset="; //获取文章信息
