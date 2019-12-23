
$(function () {
    //背景
    var c=document.getElementById("c");
    var	ctx=c.getContext("2d");
    c.width=window.innerWidth;
    c.height=window.innerHeight;
    var string1 = "1a2b3c4d5f6g7h8j9k0l";
    string1.split("");
    var fontsize=20;
    columns=c.width/fontsize;
    var drop = [];
    for(var x=0;x<columns;x++)
    {
        drop[x]=0;
    }
    function drap(){
        ctx.fillStyle="rgba(0,0,0,0.07)";
        ctx.fillRect(0,0,c.width,c.height);
        ctx.fillStyle="#5cbdaa";
        ctx.font=fontsize+"px arial";
        for(var i=0;i<drop.length;i++){
            var text1=string1[Math.floor(Math.random()*string1.length)];
            ctx.fillText(text1,i*fontsize,drop[i]*fontsize);
            drop[i]++;
            if(drop[i]*fontsize>c.height&&Math.random()>.9){
                drop[i]=0;
            }
        }
    }
    setInterval(drap,20);


    var inputWidth = $("[name='password']").innerWidth();
    var SlideVerifyPlug = window.slideVerifyPlug;
    var slideVerify = new SlideVerifyPlug('#verify-wrap',{
        wrapWidth:inputWidth,//设置 容器的宽度 ，默认为 350 ，也可不用设，你自己css 定义好也可以，插件里面会取一次这个 容器的宽度
        initText:'滑动滑块进行验证',  //设置  初始的 显示文字
        sucessText:'验证通过，快登陆吧',//设置 验证通过 显示的文字
        getSucessState:function(res){
            //当验证完成的时候 会 返回 res 值 true，只留了这个应该够用了
            verify=res;
        }
    });
    $("#resetBtn").on('click',function(){
        slideVerify.resetVerify();//可以重置 插件 回到初始状态
    });
    $("#getState").on('click',function(){
        alert(slideVerify.slideFinishState);   //这个可以拿到 当前验证状态  是否完成
    });
    var slideVerify2 = new SlideVerifyPlug('#verify-wrap2',{
        wrapWidth:'300',
        initText:'请按住滑块',
        sucessText:'验证通过',

    });
    $("#resetBtn2").on('click',function(){
        slideVerify2.resetVerify();
    });
    $("#getState2").on('click',function(){
        alert(slideVerify2.slideFinishState);
    })
});
