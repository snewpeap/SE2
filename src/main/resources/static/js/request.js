//全局请求方法 包括 一些公共方法
function getRequest(url, onSuccess, onError) {
    $.ajax({
        type: 'GET',
        url: url,
        async: true,
        success: onSuccess,
        error: onError
    });
}

function postRequest(url, data, onSuccess, onError) {
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        data: JSON.stringify(data),
        contentType: 'application/json',
        processData: false,
        success: onSuccess,
        error: onError
    });
}

function login(url,data,onSuccess,onError) {
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        processData: true,
        success: onSuccess,
        error: onError
    })
}

function getCookie(cname)
{
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)===0) return c.substring(name.length,c.length);
    }
    return "";
}

function formatDate(date){
    var year = date.getFullYear();
    var month = date.getMonth() + 1 + '';
    var day = date.getDate() + '';
    month.length===1 && (month = '0'+month);
    day.length===1 && (day = '0'+day);
    return year+'-'+month+'-'+day;
}

function plusDateByDay(date,days) {
    date.setDate(date.getDate() + days );
    var year = date.getFullYear();
    var month = date.getMonth() + 1 + '';
    var day = date.getDate() + '';
    month.length===1 && (month = '0'+month);
    day.length===1 && (day = '0'+day);
    return year+'-'+month+'-'+day;
}

function formatTime(date){
    var hour = date.getHours()+'';
    var minutes = date.getMinutes()+'';
    hour.length===1 && (hour = '0'+hour);
    minutes.length===1 && (minutes = '0'+minutes);
    return hour+":"+minutes;
}

$(document).ready(function () {

    //全局事件
    document.addEventListener("error", function (e) {
        var elem = e.target;
        if (elem.tagName.toLowerCase() == 'img') {
            elem.src = "/images/defaultPoster.jpg";
        }
    }, true);

    $('.avatar-lg').attr('title','退出登录');
    $('#logout').attr('title','退出登录');

    $('.avatar-lg').click(function () {
        confirm('确认要退出登录吗？') && postRequest('/logout',null,function (res) {
            window.location.href='/index';
        });
    });
    $('#logout').click(function () {
        confirm('确认要退出登录吗？') && postRequest('/logout',null,function (res) {
             window.location.href='/index';
        });
    });
});
