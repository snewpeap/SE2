var movie;

$(document).ready(function () {
    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    getMovie(movieId);
});

function getMovie(movieId) {
    getRequest(
        '/manage/movie/get/'+movieId,
        function (res) {
            if (res.success===true){
                movie = res.content;
                renderMovieDetail();
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
}