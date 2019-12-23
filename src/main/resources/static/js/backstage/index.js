var $,tab,skyconsWeather;
layui.config({
    base : "/static/js/backstage/"
}).use(['bodyTab','form','element','layer','jquery'],function(){
    var form = layui.form,
        layer = layui.layer,
        element = layui.element;
    $ = layui.jquery;
    tab = layui.bodyTab({
        openTabNum : "50",  //最大可打开窗口数量
        //	url : "json/navs.json" //获取菜单json地址
    });




    //退出
    $(".signOut").click(function(){
        window.sessionStorage.removeItem("menu");
        menu = [];
        window.sessionStorage.removeItem("curmenu");
    })

    //隐藏左侧导航
    $(".hideMenu").click(function(){
        $(".layui-layout-admin").toggleClass("showMenu");
        //渲染顶部窗口
        tab.tabMove();
    })

    //渲染左侧菜单
//	tab.render();



    $(document).on('keydown', function() {
        if(event.keyCode == 13) {
            $("#unlock").click();
        }
    });

    //手机设备的简单适配
    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade')

    treeMobile.on('click', function(){
        $('body').addClass('site-mobile');
    });

    shadeMobile.on('click', function(){
        $('body').removeClass('site-mobile');
    });

    // 添加新窗口
    $("body").on("click",".layui-nav .layui-nav-item a",function(){
        //如果不存在子级
        if($(this).siblings().length == 0){
            addTab($(this));
            $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
        }
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    })

    //公告层
    /*	function showNotice(){
            layer.open({
                type: 1,
                title: "系统公告",
                closeBtn: false,
                area: '310px',
                shade: 0.8,
                id: 'LAY_layuipro',
                btn: ['火速围观'],
                moveType: 1,
                content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>最近偶然发现贤心大神的layui框架，瞬间被他的完美样式所吸引，虽然功能不算强大，但毕竟是一个刚刚出现的框架，后面会慢慢完善的。很早之前就想做一套后台模版，但是感觉bootstrop代码的冗余太大，不是非常喜欢，自己写又太累，所以一直闲置了下来。直到遇到了layui我才又燃起了制作一套后台模版的斗志。由于本人只是纯前端，所以页面只是单纯的实现了效果，没有做服务器端的一些处理，可能后期技术跟上了会更新的，如果有什么问题欢迎大家指导。谢谢大家。</p><p>在此特别感谢Beginner和Paco，他们写的框架给了我很好的启发和借鉴。希望有时间可以多多请教。</p></div>',
                success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    btn.css('text-align', 'center');
                    btn.on("click",function(){
                        window.sessionStorage.setItem("showNotice","true");
                    })
                    if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
                        btn.on("click",function(){
                            layer.tips('系统公告躲在了这里', '#showNotice', {
                                tips: 3
                            });
                        })
                    }
                }
            });
        }*/

    $(".showNotice").on("click",function(){
        showNotice();
    })

    //刷新后还原打开的窗口
    if(window.sessionStorage.getItem("menu") != null){
        menu = JSON.parse(window.sessionStorage.getItem("menu"));
        curmenu = window.sessionStorage.getItem("curmenu");
        var openTitle = '';
        for(var i=0;i<menu.length;i++){
            openTitle = '';
            if(menu[i].icon){
                if(menu[i].icon.split("-")[0] == 'icon'){
                    openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
                }else{
                    openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
                }
            }
            openTitle += '<cite>'+menu[i].title+'</cite>';
            openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
            element.tabAdd("bodyTab",{
                title : openTitle,
                content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                id : menu[i].layId
            })
            //定位到刷新前的窗口
            if(curmenu != "undefined"){
                if(curmenu == '' || curmenu == "null"){  //定位到后台首页
                    element.tabChange("bodyTab",'');
                }else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                    element.tabChange("bodyTab",menu[i].layId);
                }
            }else{
                element.tabChange("bodyTab",menu[menu.length-1].layId);
            }
        }
        //渲染顶部窗口
        tab.tabMove();
    }

    //刷新当前
    $(".refresh").on("click",function(){  //此处添加禁止连续点击刷新一是为了降低服务器压力，另外一个就是为了防止超快点击造成chrome本身的一些js文件的报错(不过貌似这个问题还是存在，不过概率小了很多)
        if($(this).hasClass("refreshThis")){
            $(this).removeClass("refreshThis");
            $(".clildFrame .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload(true);
            setTimeout(function(){
                $(".refresh").addClass("refreshThis");
            },2000)
        }else{
            layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
        }
    })

    //关闭其他
    $(".closePageOther").on("click",function(){
        if($("#top_tabs li").length>2 && $("#top_tabs li.layui-this cite").text()!="后台首页"){
            var menu = JSON.parse(window.sessionStorage.getItem("menu"));
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
                    for(var i=0;i<menu.length;i++){
                        if($("#top_tabs li.layui-this cite").text() == menu[i].title){
                            menu.splice(0,menu.length,menu[i]);
                            window.sessionStorage.setItem("menu",JSON.stringify(menu));
                        }
                    }
                }
            })
        }else if($("#top_tabs li.layui-this cite").text()=="后台首页" && $("#top_tabs li").length>1){
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    window.sessionStorage.removeItem("menu");
                    menu = [];
                    window.sessionStorage.removeItem("curmenu");
                }
            })
        }else{
            layer.msg("没有可以关闭的窗口了@_@");
        }
        //渲染顶部窗口
        tab.tabMove();
    })
    //关闭全部
    $(".closePageAll").on("click",function(){
        if($("#top_tabs li").length > 1){
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != ''){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    window.sessionStorage.removeItem("menu");
                    menu = [];
                    window.sessionStorage.removeItem("curmenu");
                }
            })
        }else{
            layer.msg("没有可以关闭的窗口了@_@");
        }
        //渲染顶部窗口
        tab.tabMove();
    })

    //修改密码
    $("#updPassword").click(function () {
        layer.prompt({title: '输入原密码', formType: 1}, function(pass, index){
            layer.close(index);
            $.ajax({
                url:"/check/password",
                data:{"password":pass},
                success:function (res) {
                    if (res.code == 0){
                        layer.prompt({title: '输入新密码', formType: 2}, function(text, index){
                            layer.close(index);
                            layer.msg(res.data);
                            $.ajax({
                                url:"user/password",
                                type:"put",
                                data:{"token":res.data,"password":text},
                                success:function (res) {
                                    if (res.code == 0){
                                        layer.msg('修改成功,请重新登陆', function () {
                                            window.location = '/loginPage';
                                        });
                                    } else{
                                        layer.msg("修改失败")
                                    }


                                }
                            })

                        });
                    } else{
                        layer.msg("密码错误")
                    }
                }
            })

        });
    })
})




//打开新窗口
function addTab(_this){
    tab.tabAdd(_this);
}

function addMyTable(title,url) {
    tab.addMyTable(title,url);
}


