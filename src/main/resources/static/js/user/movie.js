var movieList = [];
var wholeMovieStr = '';
var characterChar = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

$(document).ready(function () {
    getAllMovieList();
});

function getAllMovieList(){
    getRequest(
        '/user/movie/all',
        function (res) {
            if (res.success){
                movieList = res.content;
                wholeMovieStr = '';
                movieList.forEach(function (movie) {
                    wholeMovieStr += addMovie(movie);
                });
                $("tbody").html(wholeMovieStr);
                $('.noResult').css('display','none');
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
    var movieStr = '';
    movieStr += "<tr id='"+movie.id+"'><td id='"+movie.id+"'>" + movie.name + "</td>";
    if (movie.status === 0) {
        movieStr += "<td id='"+movie.id+"'>未上映</td>"
    }else if (movie.status === 1){
        movieStr += "<td id='"+movie.id+"'>热映中</td>"
    } else if (movie.status === 2){
        movieStr += "<td id='"+movie.id+"'>已下映</td>"
    }else if (movie.status===3){
        movieStr += "<td id='"+movie.id+"'>已下架</td>"
    }
    movieStr += "<td id='"+movie.id+"'>" + movie.type + "</td>" +
        "<td id='"+movie.id+"'>" + (movie.startDate).substring(0,10) +"</td>" +
        "<td id='"+movie.id+"'>" + movie.likeNum + "</td></tr>";
    return movieStr;
}

function changeTable(letter) {
    if(letter==='all'){
        getAllMovieList();
    }else {
        wholeMovieStr = '';
        movieList.forEach(function (movie) {
            var easyName = pinyin.getCamelChars(movie.name);
            if (easyName.substring(0,1)===letter){
                wholeMovieStr += addMovie(movie);
            }
        });
        if(wholeMovieStr !== ''){
            $("tbody").html(wholeMovieStr);
            $('.noResult').css('display','none');
        }else{
            $("tbody").html(wholeMovieStr);
            $('.noResult').css('display','');
        }
    }
}


$(document).on('click','td',function (e) {
    console.log(e.target.id);
    $(window).attr('location',"/user/movie/detail?id=" + e.target.id);
    return false;
});


