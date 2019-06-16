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
        movieStr += "<tr id='@movie.id' onclick='toDetail(e)'><td>" + movie.name + "</td>";
        if (movie.status === 0) {
            movieStr += "<td>未上映</td>"
        }else if (movie.status === 1){
            movieStr += "<td>热映中</td>"
        } else if (movie.status === 2){
            movieStr += "<td>已下映</td>"
        }else if (movie.status===3){
            movieStr += "<td>已下架</td>"
        }
        movieStr += "<td>" + movie.type + "</td>" +
            "<td>" + (movie.startDate).substring(0,10) +"</td>" +
            "<td>" + movie.likeNum + "</td></tr>";
        return movieStr;
    }
});

// todo abcdefg
function changeTable(e) {
    let id = e.target.id;
    let tbody = $(id+"-tbody");
    if (tbody===null){
        let bodyId = id + "-tbody";
        let movieStr = "<tbody id= bodyId>";
        movieList.forEach(function (movie) {
                let easyName = pinyin.getCamelChars(movie.name);
                if (easyName.charAt(0)==id){
                    movieStr += addMovie(movie);
                }
        });
        $("#table-breakpoint").append(movieStr);
    }
}


function toDetail(e) {
    window.location.herf = "/user/movie/detail?id=" + e.target.id;
}


