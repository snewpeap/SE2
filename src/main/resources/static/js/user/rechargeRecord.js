var rechargeRecord =[];

$(document).ready(function () {
    getRequest(
        '/user/vip/rechargeHistory?userId=' + getCookie('id'),
        function (res) {
            if (res.success){
                rechargeRecord = res.content;
            } else {
                alert(res.message)
            }
        },
        function (error) {
            alert(error)
        }
    );

    let str = '';
    rechargeRecord.forEach(function (record) {

    });

    $('.table tbody').append()

});