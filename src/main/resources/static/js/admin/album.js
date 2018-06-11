$(function () {

    $.ajax({
        type: 'GET',
        dataType: 'json',
        data: {},
        url: 'album/getAlbums.shtml',
        success: function (resp) {

            var albums = resp.data;
            var html = '';
            if(albums && albums.length > 0) {
                $.each(albums,function (index, value) {
                    html += '<div id="'+value.id+'" onclick="list(\''+value.id+'\')" class="line-wrapper"> <div class="line-scroll-wrapper"> <div class="line-normal-wrapper"> <div class="line-normal-left-wrapper"> <div class="line-normal-avatar-wrapper"><img src="'+value.url+'"/></div> <div class="line-normal-info-wrapper"> <div class="line-normal-user-name">'+value.name+'</div> <div class="line-normal-msg">'+value.desc+'</div> <div class="line-normal-time">'+value.time+'</div> </div> </div> <!--<div class="line-normal-icon-wrapper"><img src=""/></div>--> </div> <div class="line-btn-delete"><button onclick="del(\''+value.id+'\')">删除</button></div> </div> </div>';
                })
            }
            $('body').append(html);

            Slide();//绑定滑动事件
        },
        error: function () {

        }
    });

    //album-add.html
    $('#album-add').on('click',function () {
        window.location.href = 'album-add.html';
    })
    $('#album-cloudup').on('click',function () {
        window.location.href = 'album-cloudup.html';//
    })







});
function del(id) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        data: {id:id},
        url: 'album/delAlbums.shtml',
        success: function (resp) {

            var albums = resp.data;
            if(resp.message == 'SUCCESS') {
                $('#'+id).remove();
            }
        },
        error: function () {

        }
    });
}
function list(id) {
    window.location.href = 'nanoGALLERY-master/index.html?id='+id;
}
function Slide() {
    // 设定每一行的宽度=屏幕宽度+按钮宽度
    $(".line-scroll-wrapper").width($(".line-wrapper").width() + $(".line-btn-delete").width());
    // 设定常规信息区域宽度=屏幕宽度
    $(".line-normal-wrapper").width($(".line-wrapper").width());
    // 设定文字部分宽度（为了实现文字过长时在末尾显示...）
    $(".line-normal-msg").width($(".line-normal-wrapper").width() - 280);

    // 获取所有行，对每一行设置监听
    var lines = $(".line-normal-wrapper");
    var len = lines.length;
    var lastX, lastXForMobile;

    // 用于记录被按下的对象
    var pressedObj;  // 当前左滑的对象
    var lastLeftObj; // 上一个左滑的对象

    // 用于记录按下的点
    var start;

    // 网页在移动端运行时的监听
    for (var i = 0; i < len; ++i) {
        lines[i].addEventListener('touchstart', function(e){
            lastXForMobile = e.changedTouches[0].pageX;
            pressedObj = this; // 记录被按下的对象

            // 记录开始按下时的点
            var touches = event.touches[0];
            start = {
                x: touches.pageX, // 横坐标
                y: touches.pageY  // 纵坐标
            };
        });

        lines[i].addEventListener('touchmove',function(e){
            // 计算划动过程中x和y的变化量
            var touches = event.touches[0];
            delta = {
                x: touches.pageX - start.x,
                y: touches.pageY - start.y
            };

            // 横向位移大于纵向位移，阻止纵向滚动
            if (Math.abs(delta.x) > Math.abs(delta.y)) {
                event.preventDefault();
            }
        });

        lines[i].addEventListener('touchend', function(e){
            if (lastLeftObj && pressedObj != lastLeftObj) { // 点击除当前左滑对象之外的任意其他位置
                $(lastLeftObj).animate({marginLeft:"0"}, 500); // 右滑
                lastLeftObj = null; // 清空上一个左滑的对象
            }
            var diffX = e.changedTouches[0].pageX - lastXForMobile;
            if (diffX < -150) {
                $(pressedObj).animate({marginLeft:"-132px"}, 500); // 左滑
                lastLeftObj && lastLeftObj != pressedObj &&
                $(lastLeftObj).animate({marginLeft:"0"}, 500); // 已经左滑状态的按钮右滑
                lastLeftObj = pressedObj; // 记录上一个左滑的对象
            } else if (diffX > 150) {
                if (pressedObj == lastLeftObj) {
                    $(pressedObj).animate({marginLeft:"0"}, 500); // 右滑
                    lastLeftObj = null; // 清空上一个左滑的对象
                }
            }
        });
    }

    // 网页在PC浏览器中运行时的监听
    for (var i = 0; i < len; ++i) {
        $(lines[i]).bind('mousedown', function(e){
            lastX = e.clientX;
            pressedObj = this; // 记录被按下的对象
        });

        $(lines[i]).bind('mouseup', function(e){
            if (lastLeftObj && pressedObj != lastLeftObj) { // 点击除当前左滑对象之外的任意其他位置
                $(lastLeftObj).animate({marginLeft:"0"}, 500); // 右滑
                lastLeftObj = null; // 清空上一个左滑的对象
            }
            var diffX = e.clientX - lastX;
            if (diffX < -150) {
                $(pressedObj).animate({marginLeft:"-132px"}, 500); // 左滑
                lastLeftObj && lastLeftObj != pressedObj &&
                $(lastLeftObj).animate({marginLeft:"0"}, 500); // 已经左滑状态的按钮右滑
                lastLeftObj = pressedObj; // 记录上一个左滑的对象
            } else if (diffX > 150) {
                if (pressedObj == lastLeftObj) {
                    $(pressedObj).animate({marginLeft:"0"}, 500); // 右滑
                    lastLeftObj = null; // 清空上一个左滑的对象
                }
            }
        });
    }
}