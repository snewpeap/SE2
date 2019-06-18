var rechargeRecord =[];

$(document).ready(function () {
    getRequest(
        '/user/vip/history?userId=' + getCookie('id'),
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
        str += "<tr>" + "<td style='width: 195px'>"+ record.getDate() + "</td>" +
            "<td style='width: 100px'>" + record.getPay() +"</td>" +
            "<td style='width: 100px'>" + record.getRealAmount() +"</td>" +
            "<td style='width: 100px'>" + record.getBalance() +"</td>" +
            "</tr>"
    });

    $('.table tbody').append(str)

});