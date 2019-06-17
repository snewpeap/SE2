var allArrangement;
var movieId;
var isLike;

$(document).ready(function () {
    movieId = parseInt(window.location.href.split('?')[1].split('=')[1]);

    getSchedule();
    // getIfLike();
    renderMovie();

    function getSchedule() {
        getRequest(
            '/user/arrangement/get?movieId=' + movieId,
            function (res) {
                if (res.success) {
                    $('#schedule').css("display", "");
                    allArrangement = res.content;
                    getTabs(0);
                } else {
                    $('#none-hint').css("display", "");
                }
            },
            function (error) {
                alert(error);
            }
        );
    }

    // function getIfLike() {
    //     getRequest(
    //         '/user/movie/'+ movieId,
    //         function (res) {
    //             if (res.success){
    //                 var m = res.content;
    //                 isLike = m.liked;
    //                 if (isLike){
    //                     $('#like-btn span').text("已想看");
    //                 }
    //             } else {
    //                 alert(res.message);
    //             }
    //         },
    //         function (error) {
    //             alert(error);
    //         }
    //     )
    //
    // }

    function renderMovie() {
        getRequest(
            '/user/movie/' + movieId,
            function (res) {
                if (res.success){
                    var movie = res.content;
                    isLike = movie.liked;
                    if (isLike){
                        $('#like-btn').css('display','none');
                        $('#unlike-btn').css('display','');
                    }else{
                        $('#like-btn').css('display','');
                        $('#unlike-btn').css('display','none');
                    }
                    var imgStr = "<img style='width:280px' src='";
                    if (movie.poster!=null&&movie.poster!==''){
                        imgStr += movie.poster;
                    } else {
                        imgStr += '/images/defaultPoster.jpg';
                    }
                    imgStr += "'/>";
                    // imgStr = "<img src=" + (movie.poster || 'images/defaultPoster.jpg')+ "/>";
                    // console.log(imgStr);
                    // console.log($("#movie-img"));
                    $("#movie-img").append(imgStr);
                    // console.log($("#movie-img"));
                    $("#movie-name").append(movie.name);
                    $("#movie-description").append(movie.description);
                    $("#movie-startDate").append((movie.startDate).substring(0,10));
                    $("#movie-type").append(movie.type);
                    $("#movie-country").append(movie.country);
                    $("#movie-language").append(movie.language);
                    $("#movie-director").append(movie.director);
                    $("#movie-starring").append(movie.starring);
                    $("#movie-writer").append(movie.screenWriter);
                } else {
                    alert(res.message)
                }
            },
            function error() {
                alert(error)
            }
        )
    }

});

function getTabs(i) {
    var dateContent = "";
    var index = 0;
    allArrangement.forEach(function (arrangements) {
        for (var date in arrangements){
            var dateStr = date.substring(5,7)+ "月" + date.substring(8,10) + "日";
            if(date.substring(0,10)===formatDate(new Date())){
                dateStr += '（今天）';
            }else if(date.substring(0,10)===plusDateByDay(new Date(),1)){
                dateStr += '（明天）';
            }else if(date.substring(0,10)===plusDateByDay(new Date(),2)){
                dateStr += '（后天）';
            }
            dateContent += '<li role="presentation" class="arrangement-date'+index+'"><a onclick="getTabs('+index+')">' + dateStr + '</a></li>';
            if(index===i){
                renderArrangements(date);
            }
            index += 1;
        }

    });
    $('#schedule-date').html(dateContent);
    $('.arrangement-date'+i).addClass('active');
}

// function repaintScheduleDate(e) {
//     var dateContent = "";
//     var arrangementListOneDay = allArrangement.get(curDate);
//     for (var i = 0; i<arrangementListOneDay.length;i++){
//         var dateSt
//         dateContent += '<li role="presentation" id="schedule-date' + i + '"><a href="#"  onclick="repaintScheduleDate(\'' + i + '\')">' + date + '</a></li>';
//     }
//     for (date in allArrangement){
//         var dateStr = date.substring(5,7)+ "月" + date.substring(8,10) + "日";
//
//     }
//     // for (var i = 0; i < allArrangement.length; i++) {
//     //     var date = allArrangement[i].key.substring(5, 7) + "月" + allArrangement[i].key.substring(8, 10) + "日";
//     //     if (i === 0) date += "（今天）";
//     //     else if (i === 1) date += "（明天）";
//     //     else if (i === 2) date += "（后天）";
//     //     dateContent += '<li role="presentation" id="schedule-date' + i + '"><a href="#"  onclick="repaintScheduleDate(\'' + i + '\')">' + date + '</a></li>';
//     // }
//     $('#schedule-date').html(dateContent);
//
//     $('#schedule-date' + curDateLoc).addClass("active");
//     repaintScheduleBody(curDateLoc);
// }

function renderArrangements(date) {
    var scheduleItems = [];
    allArrangement.forEach(function (arrangements) {
       for(var key in arrangements){
           if(key===date){
               scheduleItems = arrangements[date];
           }
       }
    });
    if (scheduleItems.length === 0) {
        $('#date-none-hint').css("display", "");
    } else {
        $('#date-none-hint').css("display", "none");
    }
    var bodyContent = "";
    for (var i = 0; i < scheduleItems.length; i++) {
        bodyContent += "<tr><td>" + scheduleItems[i].startTime.substring(11, 16) + "</td>" +
            "<td>预计" + scheduleItems[i].endTime.substring(11, 16) + "散场</td>" +
            "<td>" + scheduleItems[i].hallId + "</td>" +
            "<td><b>" + scheduleItems[i].fare.toFixed(2) + "</b></td>" +
            "<td><a class='btn btn-primary' href='/user/buy?id=" + movieId + "&arrangementId=" + scheduleItems[i].id + "' role='button'>选座购票</a></td></tr>";
    }
    $('#schedule-body').html(bodyContent);
}

$('#like-btn').click(function () {
    postRequest(
        '/user/movie/like/' + movieId +"?userId=" + getCookie('id'),
        {},
        function (res) {
            if (res.success){
                $('#like-btn').css('display','none');
                $('#unlike-btn').css('display','');
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    );
});

$('#unlike-btn').click(function () {
    postRequest(
        '/user/movie/unlike/' + movieId +"?userId=" + getCookie('id'),
        {},
        function (res) {
            if (res.success){
                $('#like-btn').css('display','');
                $('#unlike-btn').css('display','none');
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    );
});