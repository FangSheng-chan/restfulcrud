$(function () {
    $(window).on('resize', function () {
        //1.获取窗口的宽度
        let clientW = $(window).width();
        //2.设置临界点
        let isShowBigImage = clientW >= 900;
        //1.3 获取所有item
        let $allItems = $('#lk_carousel .carousel-item');
        // console.log($allItems);
        $allItems.each((index, item) => {
            //4.1取出图片路径
            let src = isShowBigImage ? $(item).data('lg-img'):$(item).data('sm-img');
            // console.log(src);
            let imgUrl = 'url("' + src +'")';
            //4.2 设置背景
            $(item).css({
                backgroundImage: imgUrl
            })

            //1.4.3
            if(!isShowBigImage){
                let $img = "<img src='"+src+"'>";
                $(item).empty().append($img);
            }else{
                $(item).empty();
            }
        });
    });

    $(window).trigger('resize')
});