var movieList;
$(document).ready(function () {
    getAllMovieList();

    function getAllMovieList(){
        getRequest(
            '/movie/all',
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
        var movieStr = '';

        $(".table tbody").append();
    })




});