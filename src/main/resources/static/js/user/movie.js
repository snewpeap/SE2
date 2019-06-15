let movieList = [];
let wholeMovieStr = '';
let characterChar = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

$(document).ready(function () {
    getAllMovieList();
    function getAllMovieList(){
        getRequest(
            '/user/movie/all',
            function (res) {
                if (res.success){
                    console.log(res.content);
                    movieList = res.content;
                    wholeMovieStr += '<tbody id="all-tbody">';
                    movieList.forEach(function (movie) {
                        wholeMovieStr += addMovie(movie);
                    });
                    wholeMovieStr +="</tbody>";
                    console.log(movieList);
                    console.log(wholeMovieStr);
                    console.log($("#table-breakpoint"));
                    $("#table-breakpoint").append(wholeMovieStr);
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
        let movieStr = '';
        movieStr += "<tr onclick='toDetail()'><td>" + movie.name + "</td>" +
            "<td>" + movie.status + "</td>" +
            "<td>" + movie.type + "</td>" +
            "<td>" + movie.startDate +"</td>" +
            "<td>" + movie.likeNum + "</td></tr>";
        return movieStr;
    }
});
// todo abcdefg
function changeTable(e) {
    let id = e.target.id;
    let tbody = $(id+"-tbody");
    if (tbody!==null){
        let movieStr = "";
        movieList.forEach(function (movie) {
            if(judgeNameStartWith(movie.name,id)){
                let easyName = pinyin.getCamelChars(movie.name);
            }

        });
        $("#table-breakpoint").append();
    }
}


function judgeNameStartWith(str,c) {


}

function toDetail(e) {
    window.location.herf = "/user/movie/detail?id=" + e.target.id + "&name=" + e.target.name;
}


