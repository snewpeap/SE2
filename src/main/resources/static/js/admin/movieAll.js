var movieList=[];
var preAndOnShowMovieList=[];
var batchOffMovieList=[];


$(document).ready(function () {
    getAllMovie();
});

function getAllMovie() {
    getRequest(
        '/manage/movie',
        function (res) {
            if(res.success===true){
                movieList = res.content;
                movieList.forEach(function (movie) {
                    if(movie.status < 2){
                        preAndOnShowMovieList.push(movie);
                    }else{
                        batchOffMovieList.push(movie);
                    }
                });
                renderMovie();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderMovie() {
    var movieDom = '';
    preAndOnShowMovieList.forEach(function (movie) {
        movie.description = movie.description || '';
        movieDom +=
            "<li class='movie-item card'>" +
            "<img class='movie-img' src='" + (movie.poster || "/images/defaultPoster.jpg") + "' alt=''/>" +
            "<div class='movie-info'>" +
            "<div class='movie-title'>" +
            "<span class='primary-text'>" + movie.name + "</span>" +
            "<span class='label "+ (movie.status===0?'label-default':'label-success')+"'>"+(movie.status===0?'未上映':'热映中') +"</span>"+
            "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeNum || 0) + "人想看</span>" +
            "</div>" +
            "<div class='movie-description dark-text'><span>简介：" + movie.description + "</span></div>" +
            "<div>类型：" + movie.type + "</div>" +
            "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
            "<div class='movie-operation'><a href='/manage/movieDetail?movieId="+ movie.id +"'>详情</a></div></div>" +
            "</div>"+
            "</li>";
    });
    batchOffMovieList.forEach(function (movie) {
        movie.description = movie.description || '';
        movieDom +=
            "<li class='movie-item card'>" +
            "<img class='movie-img' src='" + (movie.poster || "/images/defaultPoster.jpg") + "' alt=''/>" +
            "<div class='movie-info'>" +
            "<div class='movie-title'>" +
            "<span class='primary-text'>" + movie.name + "</span>" +
            "<span class='label "+ (movie.status===2?'label-warning':'label-danger')+"'>"+(movie.status===2?'已下映':'已下架') +"</span>"+
            "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeNum || 0) + "人想看</span>" +
            "</div>" +
            "<div class='movie-description dark-text'><span>简介：" + movie.description + "</span></div>" +
            "<div>类型：" + movie.type + "</div>" +
            "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
            "<div class='movie-operation'><a href='/manage/movieDetail?movieId="+ movie.id +"'>详情</a></div></div>" +
            "</div>"+
            "</li>";
    });
    $('.x_panel').append(movieDom);
}