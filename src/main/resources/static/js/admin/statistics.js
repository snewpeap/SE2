var placingRateDate = formatDate(new Date());
var favoriteMovieDays = 30;
var favoriteMovieNumber = 3;

$(document).ready(function() {
    $("#place-rate-date-input").val(placingRateDate);
    $("#favorite-movie-days-input").val(favoriteMovieDays);
    $("#favorite-movie-number-input").val(favoriteMovieNumber);

    getArrangementRate();

    getBoxOffice();

    getAudiencePrice();

    getPlacingRate();

    getPopularMovie();
});

function getArrangementRate() {
    getRequest(
        '/manage/statistics/arrangementRate?date='+formatDate(new Date()).replace(/-/g,'/'),
        function (res) {
            var data = res.content||[];
            var tableData = data.map(function (item) {
                return {
                    value: item.times,
                    name: item.name
                };
            });
            var nameList = data.map(function (item) {
                return item.name;
            });
            var option = {
                title : {
                    text: '今日排片率',
                    subtext: new Date().toLocaleDateString(),
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x : 'center',
                    y : 'bottom',
                    data:nameList
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'影片',
                        type:'pie',
                        radius : [50, 110],
                        // center : ['50%', '50%'],
                        // roseType : 'area',
                        data:tableData,
                        minShowLabelAngle:5
                    }
                ]
            };
            var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
            scheduleRateChart.setOption(option);
        },
        function (error) {
            alert(error.message);
        }
    );
}

function getBoxOffice() {
    getRequest(
        '/manage/statistics/totalBoxOffice',
        function (res) {
            var data = res.content || [];
            var tableData = data.map(function (item) {
                if(item.boxOffice > 0){
                    return {
                        value: item.boxOffice,
                        name: item.name,
                        label : {
                            show : true
                        },
                        labelLine : {
                            show : true
                        }
                    }
                }
            });
            var nameList = data.map(function (item) {
                if(item.boxOffice > 0){
                    return item.name;
                }
            });
            var option = {
                title : {
                    text: '所有电影票房',
                    subtext: '截至'+new Date().toLocaleDateString(),
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x : 'center',
                    y : 'bottom',
                    data:nameList
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'影片',
                        type:'pie',
                        radius : [50, 110],
                        // center : ['50%', '50%'],
                        // roseType : 'area',
                        data:tableData,
                        minShowLabelAngle:5
                    }
                ]
            };
            var scheduleRateChart = echarts.init($("#box-office-container")[0]);
            scheduleRateChart.setOption(option);
        },
        function (error) {
            alert(error.message);
        });
}

function getAudiencePrice() {
    getRequest(
        '/manage/statistics/audiencePrice?days=7',
        function (res) {
            var data = res.content || [];
            var tableData = data.map(function (item) {
                return item.price;
            });
            var dateList = data.map(function (item) {
                return formatDate(new Date(item.date));
            });
            var option = {
                title : {
                    text: '每日客单价',
                    x:'center'
                },
                xAxis: {
                    type: 'category',
                    data: dateList,
                    axisLabel:{
                        interval: 0
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: tableData,
                    type: 'line'
                }]
            };
            var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
            scheduleRateChart.setOption(option);
        },
        function (error) {
            alert(error.message);
        });
}

function getPlacingRate() {
    placingRateDate = $("#place-rate-date-input").val() || formatDate(new Date());
    getRequest(
        '/manage/statistics/placingRate?date='+placingRateDate.replace(/-/g,'/'),
        function (res) {
            var datas = res.content || [];
            var data = [];
            datas.forEach(function (item) {
                if(item.rate>0){
                    data.push(item);
                }
            });
            var tableData = data.map(function (item) {
                return item.rate;

            });
            var nameList = data.map(function (item) {
                return item.movieName;
            });
            var option = {
                title : {
                    subtext: placingRateDate,
                    x:'center'
                },
                xAxis: {
                    type: 'category',
                    data: nameList,
                    axisLabel:{
                        interval: 0
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: tableData,
                    type: 'bar',
                    barWidth:50
                }]
            };
            var scheduleRateChart = echarts.init($("#place-rate-container")[0]);
            scheduleRateChart.setOption(option);
        },
        function (error) {
            alert(error.message);
        });
}

function getPopularMovie() {
    favoriteMovieDays = $("#favorite-movie-days-input").val() > 0 ? $("#favorite-movie-days-input").val(): 30;
    favoriteMovieNumber = $("#favorite-movie-number-input").val() > 0 ? $("#favorite-movie-number-input").val(): 3;
    getRequest(
        '/manage/statistics/popularMovies?days='+favoriteMovieDays+'&movieNum='+favoriteMovieNumber,
        function (res) {
            var data = res.content||[];
            var totalBox = 0;
            for (var i = 0;i < data.length;i++){
                totalBox += data[i].boxOffice;
            }
            var tableData = data.map(function (item) {
                return {
                    value: item.boxOffice,
                    name: item.movie.name
                };
            });
            var nameList = data.map(function (item) {
                return item.movie.name;
            });
            var option = {
                title : {
                    subtext: favoriteMovieDays+'天内最受欢迎的'+favoriteMovieNumber+'个电影，截至昨日',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x : 'center',
                    y : 'bottom',
                    data:nameList
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'影片',
                        type:'pie',
                        radius : [50, 110],
                        // center : ['50%', '50%'],
                        // roseType : 'area',
                        data:tableData,
                        minShowLabelAngle:5
                    }
                ]
            };
            var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
            scheduleRateChart.setOption(option);
        },
        function (error) {
            alert(error.message);
        }
    );
}