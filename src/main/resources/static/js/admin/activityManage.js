$(document).ready(function() {
    getAllMovies();

    getActivities();

    function getActivities() {
        getRequest(
            '/admin/promotion/get',
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            if(new Date(activity.endTime).getTime() > new Date().getTime()){
                var movieDomStr = "";
                if(activity.movieList.length){
                    activity.movieList.forEach(function (movie) {
                        movieDomStr += "<li class='activity-movie primary-text'>"+movie+"</li>";
                    });
                }else{
                    movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
                }

                activitiesDomStr+=
                    "<div class='activity-container'>" +
                        "<div class='activity-card card'>" +
                            "<div class='activity-line'>" +
                                "<span class='title'>"+activity.name+"</span>" +
                            "</div>" +
                            "<div class='activity-line'>" +
                                "<span>活动描述："+activity.description+"</span>" +
                            "</div>" +
                            "<div class='activity-line'>" +
                                "<span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                            "</div>" +
                            "<div class='activity-line'>" +
                                "<span>参与电影：</span>" +
                                "<ul>"+movieDomStr+"</ul>" +
                            "</div>" +
                        "</div>" +
                        "<div class='activity-coupon primary-bg'>" +
                            "<span class='title'>优惠券："+activity.name+"</span>" +
                            "<span class='title'>满"+activity.targetAmount+"减<span class='error-text title'>"+activity.couponAmount+"</span></span>" +
                            "<span class='gray-text'>"+activity.description+"</span>" +
                        "</div>" +
                    "</div>";
            }
        });
        $(".content-activity").append(activitiesDomStr);

    }

    function getAllMovies() {
        getRequest(
            '/manage/movie',
            function (res) {
                if(res.success){
                    var movieList = res.content;
                    $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                    movieList.forEach(function (movie) {
                        if(movie.status < 2){
                            $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                        }
                    });
                }else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error.message);
            }
        );
    }

    $("#activity-form-btn").click(function () {
        var form = {
            name: $("#activity-name-input").val(),
            description: $("#activity-description-input").val(),
            startTime: $("#activity-start-date-input").val(),
            endTime: $("#activity-end-date-input").val(),
            specifyMovies: $('#activity-movie-input').val()!=-1,
            movieIDs: Array.from(selectedMovieIds),
            targetAmount: $("#coupon-target-input").val(),
            couponAmount: $("#coupon-discount-input").val(),
            couponExpiration: $("#coupon-expiration-input").val()
        };

        postRequest(
            '/admin/promotion/add',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error.message);
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(parseInt(movieId));
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }
});