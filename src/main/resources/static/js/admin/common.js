$(document).ready(function () {
    var role = getCookie("role");
    if(role==='staff'){
        $('#hall').css("display", "none");
        $('#staff').css("display", "none");
        $('#strategy').css("display", "none");
        $('#coupon').css("display", "none");
    }else if(role==='manager'){
        $('#staff').css("display", "none");
    }
});