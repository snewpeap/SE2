var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

var hallId,
    scheduleDate = formatDate(new Date()),
    halls = [],
    arrangements = [],
    movieList = [];
var movieSelect;

$(document).ready(function () {
    initData();
});

function initData() {
    $('#schedule-date-input').val(scheduleDate);
    getHalls();
    getPreAndOnMovies();

    // 过滤条件变化后重新查询
    $('#hall-select').change (function () {
        hallId=$(this).children('option:selected').val();
        getArrangements();
    });
    $('#schedule-date-input').change(function () {
        scheduleDate = $('#schedule-date-input').val();
        getArrangements();
    });
}

function getHalls() {
    getRequest(
        '/manage/hall/get',
        function (res) {
            if(res.success){
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getArrangements();
            }else{
                alert(res.message);
            }
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function getPreAndOnMovies() {
    getRequest(
        '/manage/movie/all',
        function (res) {
            if(res.success){
                res.content.forEach(function (movie) {
                    if(movie.state<2){
                        movieList.push(movie);
                        $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                        $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                    }
                });
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res);
        }
    );
}

function getArrangements() {
    getRequest(
        '/manage/arrangement/get?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
        function (res) {
            if(res.success){
                arrangements = res.content;
                renderArrangements();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res);
        }
    );
}

function renderArrangements() {
    $('.schedule-date-container').empty();
    $(".schedule-time-line").siblings().remove();
    arrangements.forEach(function (arrangement) {
        $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(arrangement.startTime))+"</div>");
        var arrangementDateDom = $(" <ul class='schedule-item-line'></ul>");
        $(".schedule-container").append(arrangementDateDom);
        var arrangementStyle = mapArrangementStyle(arrangement);
        var arrangementItemDom =$(
            "<li id='schedule-"+ arrangement.id +"' class='schedule-item' data-schedule='"+JSON.stringify(arrangement)+"' style='background:"+arrangementStyle.color+";top:"+arrangementStyle.top+";height:"+arrangementStyle.height+"'>"+
            "<span>"+arrangement.movieId+"</span>"+
            "<span class='error-text'>¥"+arrangement.fare+"</span>"+
            "<span>"+formatTime(new Date(arrangement.startTime))+"-"+formatTime(new Date(arrangement.endTime))+"</span>"+
            "</li>"
        );
        arrangementDateDom.append(arrangementItemDom);
    })
}

function mapArrangementStyle(arrangement) {
    var start = new Date(arrangement.startTime).getHours()+new Date(arrangement.startTime).getMinutes()/60,
        end = new Date(arrangement.endTime).getHours()+new Date(arrangement.endTime).getMinutes()/60 ;
    return {
        color: colors[arrangement.movieId%colors.length],
        top: 40*start+'px',
        height: 40*(end-start)+'px'
    }
}