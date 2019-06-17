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
                    getTabs();
                } else {
                    $('#none-hint').css("display", "");
                }
            },
            function (error) {
                alert(error);
            }
        );
    }

    // //todo get Like
    // function getIfLike() {
    //     getRequest(
    //         '/user/movie/'+ movieId,
    //         function (res) {
    //             if (res.success){
    //                 let m = res.content;
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
                    let movie = res.content;
                    isLike = movie.liked;
                    if (isLike){
                        $('#like-btn span').text("已想看");
                    }
                    let imgStr = "<img style='width:280px' src=";
                    if (movie.poster!=null){
                        imgStr += movie.poster;
                    } else {
                        imgStr += '/images/defaultPoster.jpg';
                    }
                    imgStr += "/>";
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

function getTabs() {
    let dateContent = "";
    for (let date in allArrangement){
        let dateStr = date.substring(5,7)+ "月" + date.substring(8,10) + "日";
        dateContent += '<li role="presentation" id="@date"><a href="#"  onclick="repaintScheduleBody(e)">' + dateStr + '</a></li>';
    }
    $('#schedule-date').html(dateContent);
    // $('#' + date).addClass("active");
    //repaintScheduleBody(curDateLoc);
}

// function repaintScheduleDate(e) {
//     let dateContent = "";
//     let arrangementListOneDay = allArrangement.get(curDate);
//     for (let i = 0; i<arrangementListOneDay.length;i++){
//         let dateSt
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

function repaintScheduleBody(e) {
    let date = e.target.id;
    let scheduleItems = allArrangement.get(date);
    if (scheduleItems.length === 0) {
        $('#date-none-hint').css("display", "");
    } else {
        $('#date-none-hint').css("display", "none");
    }
    let bodyContent = "";
    for (let i = 0; i < scheduleItems.length; i++) {
        bodyContent += "<tr><td>" + scheduleItems[i].startTime.substring(11, 16) + "</td>" +
            "<td>预计" + scheduleItems[i].endTime.substring(11, 16) + "散场</td>" +
            "<td>" + scheduleItems[i].hallId + "</td>" +
            "<td><b>" + scheduleItems[i].fare.toFixed(2) + "</b></td>" +
            "<td><a class='btn btn-primary' href='/user/movieDetail/buy?id=" + movieId + "&scheduleId=" + scheduleItems[i].id + "' role='button'>选座购票</a></td></tr>";
    }
    $('#schedule-body').html(bodyContent);
}

//todo:like unlike
$('#like-btn').click(function () {
    if (!isLike){
        postRequest(
            '/user/movie/like/' + movieId +"?userId=" + getCookie('id'),
            null,
            function (res) {
                if (res.success){
                    // alert("操作成功!");
                    $('#like-btn span').text("已想看");
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        );
    } else {
        postRequest(
            '/user/movie/unlike/' + movieId +"?userId=" + getCookie('id'),
            null,
            function (res) {
                if (res.success){
                    // alert("操作成功!");
                    $('#like-btn span').text("想 看");
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        );
    }
});