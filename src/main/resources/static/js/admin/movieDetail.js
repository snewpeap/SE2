var movie;
var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
$(document).ready(function () {
    getMovie(movieId);
});

function getMovie(movieId) {
    getRequest(
        '/manage/movie/'+movieId,
        function (res) {
            if (res.success===true){
                movie = res.content;
                renderMovieDetail();
                getMovieLikeChart(movie.likeData);
            } else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderMovieDetail(){
    $('#movie-img').attr('src',movie.poster || "/images/defaultPoster.jpg");
    $('#movie-name').text(movie.name);
    $('#movie-description').text(movie.description);
    $('#movie-startDate').text(formatDate(new Date(movie.startDate)));
    $('#movie-type').text(movie.type);
    $('#movie-country').text(movie.country);
    $('#movie-language').text(movie.language);
    $('#movie-director').text(movie.director);
    $('#movie-starring').text(movie.starring);
    $('#movie-writer').text(movie.screenWriter);
    $('#movie-duration').text(movie.duration);
    if(movie.status===3){
        $('#delete-btn > span').text('已下架');
        $('#delete-btn').attr('disabled','disabled');
    }
}

function getMovieLikeChart(data) {
    var dateArray = [],
        numberArray = [];
    data.forEach(function (item) {
        dateArray.push(item.date);
        numberArray.push(item.likeAmount);
    });

    var myChart = echarts.init($("#like-date-chart")[0]);

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '想看人数变化表'
        },
        xAxis: {
            type: 'category',
            data: dateArray
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: numberArray,
            type: 'line'
        }]
    };

    myChart.setOption(option);
}

$('#modify-btn').click(function () {
    $("#movie-edit-name-input").val(movie.name);
    $("#movie-edit-date-input").val(movie.startDate.slice(0,10));
    $("#movie-edit-img-input").val(movie.poster);
    $("#movie-edit-description-input").val(movie.description);
    $("#movie-edit-type-input").val(movie.type);
    $("#movie-edit-duration-input").val(movie.duration);
    $("#movie-edit-country-input").val(movie.country);
    $("#movie-edit-language-input").val(movie.language);
    $("#movie-edit-director-input").val(movie.director);
    $("#movie-edit-starring-input").val(movie.starring);
    $("#movie-edit-writer-input").val(movie.screenWriter);
});

$('#movie-edit-form-btn').click(function () {
    if ($('#movie-edit-name-input').val() === ''){
        alert("电影名称不能为空！")
    }else if($('#movie-edit-date-input').val() === ''){
        alert("上映时间不能为空！")
    } else if($('#movie-edit-img-input').val() === ''){
        alert("电影海报不能为空！")
    }else if($('#movie-edit-duration-input').val() <= 0){
        alert("片长不能小于等于零！")
    }
    else{
        var formData = getMovieEditForm();
        postRequest(
            '/manage/movie/modify',
            formData,
            function (res) {
                if(res.success){
                    getMovie(movieId);
                    $("#movieEditModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

function getMovieEditForm() {
    return {
        id: movieId,
        name: $('#movie-edit-name-input').val(),
        startDate: $('#movie-edit-date-input').val(),
        poster: $('#movie-edit-img-input').val(),
        description: $('#movie-edit-description-input').val(),
        type: $('#movie-edit-type-input').val(),
        duration: $('#movie-edit-duration-input').val(),
        country: $('#movie-edit-country-input').val(),
        starring: $('#movie-edit-starring-input').val(),
        director: $('#movie-edit-director-input').val(),
        screenWriter: $('#movie-edit-writer-input').val(),
        language: $('#movie-edit-language-input').val()
    };
}

$('#delete-btn').click(function () {
    var r=confirm("确认要下架该电影吗？");
    if (r) {
        postRequest(
            '/manage/movie/remove?movieId='+movieId,
            {},
            function (res) {
                if(res.success){
                    alert("下架成功！");
                    $('#delete-btn > span').text('已下架');
                    $('#delete-btn').attr('disabled','disabled');
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