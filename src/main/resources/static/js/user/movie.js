let movieList = [];
let characterChar = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];


$(document).ready(function () {
    getAllMovieList();
    function getAllMovieList(){
        getRequest(
            '/user/movie/all',
            function (res) {
                if (res.success){
                    movieList = res.content;
                    movieList.forEach(function (movie) {

                    })
                }else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        );
    }

    function addMovie(movie) {
        movieStr = '';
        movieStr += "<tr><td>" + movie.name + "</td>" +
            "<td>" + movie.status + "</td>" +
            "<td>" + movie.type + "</td>" +
            ""

    }




});

$("#all-tab").click(function () {
    let movieStr = '';
    movieStr += "";
    $(".table tbody").append();
})