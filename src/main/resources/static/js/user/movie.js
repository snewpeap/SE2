let movieList = [];
$(document).ready(function () {
    getAllMovieList();

    function getAllMovieList(){
        getRequest(
            '/user/movie/all',
            function (res) {
                if (res.success){
                    movieList = res.content;
                }else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        );
    }

    $("#all-tab").click(function () {
        let movieStr = '';
        movieStr += "";

        $(".table tbody").append();
    })




});