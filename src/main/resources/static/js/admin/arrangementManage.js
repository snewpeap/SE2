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
    arrangementDate = formatDate(new Date()),
    halls = [],
    arrangements = [],
    movieList = [];

$(document).ready(function () {
    initData();
});

function initData() {
    $('#arrangement-date-input').val(arrangementDate);
    getHalls();

    // 过滤条件变化后重新查询
    $('#hall-select').change (function () {
        hallId=$(this).children('option:selected').val();
        getArrangements();
    });
    $('#arrangement-date-input').change(function () {
        arrangementDate = $('#arrangement-date-input').val();
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
                    $('#arrangement-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#arrangement-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getPreAndOnMovies();
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
        '/manage/movie',
        function (res) {
            if(res.success){
                res.content.forEach(function (movie) {
                    if(movie.status<2){
                        $('#arrangement-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                        $("#arrangement-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                    }
                    movieList.push(movie);
                });
                getArrangements();
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
        '/manage/arrangement/get?hallId='+hallId+'&startDate='+arrangementDate.replace(/-/g,'/'),
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
    $('.arrangement-date-container').empty();
    $(".arrangement-time-line").siblings().remove();
    var i = 0;
    arrangements.forEach(function (arrangementOfDate) {
        var beginDate = new Date(arrangementDate);
        var date = plusDateByDay(beginDate,i);
        $('.arrangement-date-container').append("<div class='arrangement-date'>"+date+"</div>");
        var arrangementDateDom = $(" <ul class='arrangement-item-line'></ul>");
        $(".arrangement-container").append(arrangementDateDom);
        arrangementOfDate.forEach(function (arrangement) {
            var movieName = '默认电影名';
            movieList.forEach(function (movie) {
                if(movie.id===arrangement.movieId){
                    movieName = movie.name;
                }
            });
            var arrangementStyle = mapArrangementStyle(arrangement);
            var arrangementItemDom =
                "<li id='arrangement-"+ arrangement.id +"' class='arrangement-item' data-arrangement='"+JSON.stringify(arrangement)+"' style='background:"+arrangementStyle.color+";top:"+arrangementStyle.top+";height:"+arrangementStyle.height+"'>"+
                "<span>"+movieName+"</span>"+
                "<span class='error-text'>¥"+arrangement.fare+"</span>"+
                "<span>"+formatTime(new Date(arrangement.startTime))+"-"+formatTime(new Date(arrangement.endTime))+"</span>"+
                "</li>";

            arrangementDateDom.append(arrangementItemDom);
        });
        i += 1;
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

$('#arrangement-form-btn').click(function () {
    var movieLength;
    for (var i = 0;i<movieList.length;i++){
        if (movieList[i].id === parseInt($("#arrangement-movie-input").children('option:selected').val())){
            movieLength = movieList[i].duration;
        }
    }
    var start = new Date($("#arrangement-start-date-input").val()).getTime();
    var end = new Date($("#arrangement-end-date-input").val()).getTime();
    var startDate = new Date($("#arrangement-start-date-input").val()).getDate();
    var endDate = new Date($("#arrangement-end-date-input").val()).getDate();
    var price = $("#arrangement-price-input");
    if ($("#arrangement-start-date-input").val() === ''){
        alert("开始时间不完整")
    }
    else if ($("#arrangement-end-date-input").val() === ''){
        alert("结束时间不完整")
    }
    else if(start>end){
        alert("开始时间不得晚于结束时间")
    }
    else if(movieLength > (end - start)/60000){
        alert("排片时间差应大于等于片长")
    }else if(startDate!==endDate){
        alert("不可跨天排片")
    }
    else if (parseInt(price.val()) <= 0){
        alert("票价应该大于零")
    }
    else{
        var form = {
            hallId: $("#arrangement-hall-input").children('option:selected').val(),
            movieId : $("#arrangement-movie-input").children('option:selected').val(),
            startTime: $("#arrangement-start-date-input").val(),
            endTime: $("#arrangement-end-date-input").val(),
            fare: $("#arrangement-price-input").val(),
            visibleDate:$("#arrangement-visible-date-input").val()
        };

        postRequest(
            '/manage/arrangement/add',
            form,
            function (res) {
                if(res.success){
                    $("#arrangementModal").modal('hide');
                    getArrangements();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error.message);
            }
        );
    }
});

$(document).on('click','.arrangement-item',function (e) {
    var arrangement = JSON.parse(e.target.dataset.arrangement);
    $("#arrangement-edit-hall-input").children('option[value='+arrangement.hallId+']').attr('selected',true);
    $("#arrangement-edit-movie-input").children('option[value='+arrangement.movieId+']').attr('selected',true);
    $("#arrangement-edit-start-date-input").val(arrangement.startTime.slice(0,16));
    $("#arrangement-edit-end-date-input").val(arrangement.endTime.slice(0,16));
    $("#arrangement-edit-price-input").val(arrangement.fare);
    $("#arrangement-edit-visible-date-input").val(arrangement.visibleDate.slice(0,10));
    $('#arrangementEditModal').modal('show');
    $('#arrangementEditModal')[0].dataset.arrangementId = arrangement.id;
});

$('#arrangement-edit-form-btn').click(function () {
    var movieLength;
    for (var i = 0;i<movieList.length;i++){
        if (movieList[i].id === parseInt($("#arrangement-edit-movie-input").children('option:selected').val())){
            movieLength = movieList[i].duration;
        }
    }
    var start = new Date($("#arrangement-edit-start-date-input").val()).getTime();
    var end = new Date($("#arrangement-edit-end-date-input").val()).getTime();
    var startDate = new Date($("#arrangement-edit-start-date-input").val()).getDate();
    var endDate = new Date($("#arrangement-edit-end-date-input").val()).getDate();
    var price = $("#arrangement-edit-price-input");
    if ($("#arrangement-edit-start-date-input").val() === ''){
        alert("开始时间不完整")
    }
    else if ($("#arrangement-edit-end-date-input").val() === ''){
        alert("结束时间不完整")
    }
    else if(start>end){
        alert("开始时间不得晚于结束时间")
    }
    else if(movieLength > (end - start)/60000){
        alert("排片时间差应大于等于片长")
    }else if(startDate!==endDate){
        alert("不可跨天排片")
    }
    else if (parseInt(price.val()) <= 0){
        alert("票价应该大于零")
    }
    else{
        var form = {
            hallId: $("#arrangement-edit-hall-input").children('option:selected').val(),
            movieId : $("#arrangement-edit-movie-input").children('option:selected').val(),
            startTime: $("#arrangement-edit-start-date-input").val(),
            endTime: $("#arrangement-edit-end-date-input").val(),
            fare: $("#arrangement-edit-price-input").val(),
            visibleDate:$("#arrangement-edit-visible-date-input").val()
        };

        postRequest(
            '/manage/arrangement/modify?arrangementId='+Number($('#arrangementEditModal')[0].dataset.arrangementId),
            form,
            function (res) {
                if(res.success){
                    $("#arrangementEditModal").modal('hide');
                    getArrangements();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error.message);
            }
        );
    }
});

$("#arrangement-edit-remove-btn").click(function () {
    var r=confirm("确认要删除该排片信息吗");
    if (r) {
        postRequest(
            '/manage/arrangement/remove?arrangementId='+Number($('#arrangementEditModal')[0].dataset.arrangementId),
            {},
            function (res) {
                if(res.success){
                    $("#arrangementEditModal").modal('hide');
                    getArrangements();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error.message);
            }
        );
    }
});

$(".toggle").click(function () {
    if( $(".arrangement-date-container").css('padding-left')==='95px'){
        $(".arrangement-date-container").css('padding-left','100px');
        $('.arrangement-date').css('width','150px')
    }else {
        $(".arrangement-date-container").css('padding-left','95px');
        $('.arrangement-date').css('width','135px')
    }
});