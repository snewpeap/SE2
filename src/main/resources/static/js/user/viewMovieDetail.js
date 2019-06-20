var allArrangement;
var movieId;
var isLike;

$(document).ready(function () {
    movieId = parseInt(window.location.href.split('?')[1].split('=')[1]);

    getSchedule();

    renderMovie();

});

function getSchedule() {
    getRequest(
        '/user/arrangement/get?movieId=' + movieId,
        function (res) {
            if (res.success) {
                if(res.content!=null&&res.content.length > 0){
                    $('#schedule').css("display", "");
                    allArrangement = res.content;
                    getTabs(0);
                }else{
                    $('#schedule').css("display", "");
                    $('#date-none-hint').css("display", "");
                }
            } else {
                $('#schedule').css("display", "");
                $('#date-none-hint').css("display", "");
                alert(res.message);
            }
        },
        function (error) {
            alert(error.message);
        }
    );
}

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

                $("#movie-img").append(imgStr);
                $("#movie-name").append(movie.name);
                if(movie.status===0){
                    $('.firstLine').append("<span class='label label-default'>未上映</span>");
                }else if(movie.status===1){
                    $('.firstLine').append("<span class='label label-success'>热映中</span>");
                }else if(movie.status===2){
                    $('.firstLine').append("<span class='label label-warning'>已下映</span>");
                }else{
                    $('.firstLine').append("<span class='label label-danger'>已下架</span>");
                }

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

function getTabs(i) {
    var dateContent = "";
    var index = 0;
    allArrangement.forEach(function (arrangements) {
        var dateStr = formatDate(new Date(arrangements[0].startTime)).substring(5,7)+ "月" + formatDate(new Date(arrangements[0].startTime)).substring(8,10) + "日";
        if(formatDate(new Date(arrangements[0].startTime)).substring(0,10)===formatDate(new Date())){
            dateStr += '（今天）';
        }else if(formatDate(new Date(arrangements[0].startTime)).substring(0,10)===plusDateByDay(new Date(),1)){
            dateStr += '（明天）';
        }else if(formatDate(new Date(arrangements[0].startTime)).substring(0,10)===plusDateByDay(new Date(),2)){
            dateStr += '（后天）';
        }
        dateContent += '<li role="presentation" class="arrangement-date'+index+'"><a onclick="getTabs('+index+')">' + dateStr + '</a></li>';
        if(index===i){
            renderArrangements(index);
        }
        index += 1;
    });
    $('#schedule-date').html(dateContent);
    $('.arrangement-date'+i).addClass('active');
}

function renderArrangements(index) {
    var scheduleItems = allArrangement[index];
    if (scheduleItems.length === 0) {
        $('#date-none-hint').css("display", "");
    } else {
        $('#date-none-hint').css("display", "none");
    }
    var bodyContent = "";
    for (var i = 0; i < scheduleItems.length; i++) {
        bodyContent += "<tr><td>" + scheduleItems[i].startTime.substring(11, 16) + "</td>" +
            "<td>预计" + scheduleItems[i].endTime.substring(11, 16) + "散场</td>" +
            "<td>" + scheduleItems[i].hallName + "</td>" +
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