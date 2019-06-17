var allMovieList = [];
var firstImageStr = "";
var newestList = [];
var highestList = [];
var mostList = [];

$(document).ready(function () {
    function getMovieList() {
        getRequest(
            '/user/movie/all',
            function (res) {
                if (res.success) {
                    allMovieList = res.content;
                    renderMovie();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        )
    }

    getMovieList();
});

function renderMovie() {
    getNewest();
    console.log(newestList);
    getHighestBox();
    console.log(highestList);
    getMostLikes();
    console.log(mostList);

    firstImageStr = "<a href='/user/movie/detail?movieId="+newestList[0].id+"' class='hvr-sweep-to-bottom'><img src='"+ (newestList[0].poster||"/images/defaultPoster.jpg") + "' title=\"Movies Pro\"  alt='' class='img-responsive' />" +
        "<div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div></a>";
    $('#video').append(firstImageStr);
    var newestMovieInfo = getFirstInfoStr(newestList);
    $('#newest-movie-info').append(newestMovieInfo);
    $('#newest-movie-other').append(getNextInfoStr(newestList));

    firstImageStr = "<a href='/user/movie/detail?movieId="+highestList[0].id+"' class='hvr-sweep-to-bottom'><img src='"+ (highestList[0].poster||"/images/defaultPoster.jpg") + "' alt='' class='img-responsive' /></a>";
    $('#video1').append(firstImageStr);
    var highestMovieInfo = getFirstInfoStr(highestList);
    $('#highest-movie-info').append(highestMovieInfo);
    $('#highest-movie-other').append(getNextInfoStr(highestList));

    firstImageStr = "<a href='/user/movie/detail?movieId="+mostList[0].id+"' class='hvr-sweep-to-bottom'><img src='"+ (mostList[0].poster||"/images/defaultPoster.jpg") + "' alt='' class='img-responsive' /></a>";
    $('#video2').append(firstImageStr);
    var mostMovieInfo = getFirstInfoStr(mostList);
    $('#most-movie-info').append(mostMovieInfo);
    $('#most-movie-other').append(getNextInfoStr(mostList));

    $('#owl-demo').append(getNewestForSlide());
    $("#owl-demo").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        autoPlay : true,
        navigation :true,

        items : 5,
        itemsDesktop : [640,4],
        itemsDesktopSmall : [414,3]
    });
    $('#slide-most-box').append(getMostBoxForSlide());
    $('#second-top-movies').append(getSecondTopMovies());
    $('#top-movie-image').append(getTopMovieImage());
    $('#top-movie-info').append(getTopMovieInfo());


    //判断是否参加了活动 如果参加了添加小标签
    function joinedPromotion(List,n) {
        if (List[n].joinedPromotions!==null && List[n].joinedPromotions.length!==0){
            if(List[n].joinedPromotions.length >1){
                return "<div class=\"ribben one\"><p>活动:"+List[n].joinedPromotions[0]+",...</p></div>";
            }else{
                return "<div class=\"ribben one\"><p>活动:"+List[n].joinedPromotions[0]+"</p></div>";
            }
        }else {
            return "";
        }
    }

    //按电影的id排序
    function getNewest() {
        for (var i = 0;i<allMovieList.size-1;i++){
            for (var j = 0; j<allMovieList.size-i-1;j++){
                if (allMovieList[j].id<allMovieList[j+1].id){
                    var temp = allMovieList[j];
                    allMovieList[j] = allMovieList[j+1];
                    allMovieList[j+1] = temp;
                }
            }
        }
        for (var x = 0;x<allMovieList.length;x++){
            newestList.push(allMovieList[x]);
        }
        console.log(newestList);
    }

    //按想看人数排序
    getMostLikes();
    function getMostLikes() {
        for (var i = 0;i<allMovieList.size-1;i++){
            for (var j = 0; j<allMovieList.size-i-1;j++){
                if (allMovieList[j].likeNum<allMovieList[j+1].likeNum){
                    var temp = allMovieList[j];
                    allMovieList[j] = allMovieList[j+1];
                    allMovieList[j+1] = temp;
                }
            }
        }
        for (var x = 0;x<allMovieList.length;x++){
            mostList.push(allMovieList[x]);
        }
    }

    //按票房排序
    getHighestBox();
    function getHighestBox() {
        for (var i = 0;i<allMovieList.size-1;i++){
            for (var j = 0; j<allMovieList.size-i-1;j++){
                if (allMovieList[j].box<allMovieList[j+1].box){
                    var temp = allMovieList[j];
                    allMovieList[j] = allMovieList[j+1];
                    allMovieList[j+1] = temp;
                }
            }
        }
        for (var x = 0;x<allMovieList.length;x++){
            highestList.push(allMovieList[x]);
        }
    }

    //拼接最。。电影的信息字符串（不包括图片）
    function getFirstInfoStr(List) {
        var firstMovieInfo = "";
        firstMovieInfo += "<p class=\"fexi_header\">"+ List[0].name+"</p>" +
            "<p class=\"fexi_header_para\"><span>内容简介<label>:</label></span>"+ List[0].description+"</p>"+
            "<p class=\"fexi_header_para\"><span>上映日期<label>:</label></span>"+ List[0].startDate.slice(0,10)+"</p>" +
            "<p class=\"fexi_header_para\"><span>类型<label>:</label></span>"+ List[0].type+"</p>" +
            "<p class=\"fexi_header_para\"><span>想看人数<label>:</label></span>"+ List[0].likeNum+"</p>" +
            joinedPromotion(List,0);
        return firstMovieInfo;
    }

    //拼接下面一堆电影的字符串 图片 名称 想看人数
    function getNextInfoStr(List) {
        var nextInfoStr = "";
        for (var i = 1;i<=8 && i<allMovieList.length;i++){
            nextInfoStr +=
                "<div class=\"w3l-movie-gride-agile\">" + "<a href=\"/user/movie/detail?movieId="+List[i].id+"\" class=\"hvr-sweep-to-bottom\"><img src=\"" + (List[i].poster||"/images/defaultPoster.jpg") +
                "\" title=\"Movies Pro\" class=\"img-responsive\" alt=\" \" /><div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div></a>" +
                "<div class=\"mid-1 agileits_w3layouts_mid_1_home\">" +
                "<div class=\"w3l-movie-text\"><h6><a href=\"/user/movie/detail\">" + List[i].name + "</a></h6></div>" +
                "<div class=\"mid-2 agile_mid_2_home\"><p>想看人数："+ List[i].likeNum +"</p><div class=\"clearfix\"></div></div></div>" +
                joinedPromotion(List,i) +
                "</div>";
        }
        return nextInfoStr;
    }

    //拼接小标签下面的滑动窗口之一 最新电影 的字符串
    function getNewestForSlide() {
        var newMovie = "";
        for (var i = 0;i<newestList.length;i++){
            newMovie +=
                "<div class=\"item\">" +
                "<div class=\"w3l-movie-gride-agile w3l-movie-gride-slider \">" +
                "<a href=\"/user/movie/detail?movieId="+newestList[i].id+"\" class=\"hvr-sweep-to-bottom\"><img src=\" "+ (newestList[i].poster||"/images/defaultPoster.jpg") +"\" title=\"Movies Pro\" class=\"img-responsive\" alt=\" \" />" +
                "<div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div>" +
                "</a>" +
                "<div class=\"mid-1 agileits_w3layouts_mid_1_home\">" +
                "<div class=\"w3l-movie-text\">" +
                "<h6><a href=\"/user/movie/detail\">" + newestList[i].name + "</a></h6>" +
                "</div>" +
                "<div class=\"mid-2 agile_mid_2_home\">" +
                "<p>" + newestList[i].startDate.slice(0,10) + "</p>"+
                "<div>想看人数：" + newestList[i].likeNum + "</div>" +
                "<div class=\"clearfix\"></div>" +
                "</div>" +
                "</div>" +
                joinedPromotion(newestList,i) +
                "</div>" +
                "</div>";
        }
        return newMovie;
    }

    function getMostBoxForSlide() {
        var res = "";
        for (var i = 0;i<=10 && i<highestList.length;i++){
            res += "<div class=\"col-md-2 w3l-movie-gride-agile requested-movies\">" +
                        "<a href=\"/user/movie/detail?movieId="+highestList[i].id+"\" class=\"hvr-sweep-to-bottom\">" +
                            "<img src=\"" + (highestList[i].poster||"/images/defaultPoster.jpg") + "\" title=\"Movies Pro\" class=\"img-responsive\" alt=\" \">" +
                            "<div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div>" +
                        "</a>" +
                        "<div class=\"mid-1 agileits_w3layouts_mid_1_home\">" +
                            "<div class=\"w3l-movie-text\">" +
                                "<h6><a href=\"/user/movie/detail\">" + highestList[i].name + "</a></h6>" +
                            "</div>" +
                            "<div class=\"mid-2 agile_mid_2_home\"><p>" + highestList[i].startDate.slice(0,10) + "</p>" +
                                "<div>想看人数：" + highestList[i].likeNum + "</div>" +
                                "<div class=\"clearfix\"></div>" +
                            "</div>" +
                        "</div>" +
                        joinedPromotion(highestList,i) +
                    "</div>";
        }
        return res;
    }

    function getSecondTopMovies() {
        var res = '';
        for (var i = 1;i<=8 && i<mostList.length;i++){
            res += "<div class=\"w3l-movie-gride-agile\">" +
                "<a href=\"/user/movie/detail?movieId="+mostList[i].id+"\" class=\"hvr-sweep-to-bottom\"><img src=\"" + (mostList[i].poster||"/images/defaultPoster.jpg") + "\" title=\"Movies Pro\" class=\"img-responsive\" alt=\" \">" +
                "<div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div></a>" +
                "<div class=\"mid-1 agileits_w3layouts_mid_1_home\">" +
                "<div class=\"w3l-movie-text\">" +
                "<h6><a href=\"/user/movie/detail\">" + mostList[i].name + "</a></h6></div>" +
                "<div class=\"mid-2 agile_mid_2_home\"><p>" + mostList[i].startDate.slice(0,10) + "</p><div>想看人数:" + mostList[i].likeNum +
                "</div><div class=\"clearfix\"></div></div></div>" +
                joinedPromotion(mostList,i) +
                "</div>";
        }
        return res;
    }

    function getTopMovieImage() {
        var res = '';
        res += "<a href='/user/movie/detail?movieId="+mostList[0].id+"'><img src=\"" + (mostList[0].poster||"/images/defaultPoster.jpg") + "\" alt=\"\" class=\"img-responsive\" />" +
            "<div class=\"w3l-action-icon\"><i class=\"fa fa-play-circle-o\" aria-hidden=\"true\"></i></div></a>";
        return res;
    }

    function getTopMovieInfo() {
        var res = '';
        res += "<p class=\"fexi_header\">"+mostList[0].name+"</p>" +
            "<p class=\"fexi_header_para\"><span>内容简介<label>:</label></span>"+mostList[0].description+"</p>" +
            "<p class=\"fexi_header_para\"><span>上映日期<label>:</label></span>" + mostList[0].startDate.slice(0,10) + "</p>" +
            "<p class=\"fexi_header_para\"><span>类型<label>:</label> </span>" + mostList[0].type +
            "<p class=\"fexi_header_para fexi_header_para1\"><span>想看人数<label>:</label></span>" + mostList[0].likeNum + "" +
            joinedPromotion(mostList,0);
        return res;
    }
}