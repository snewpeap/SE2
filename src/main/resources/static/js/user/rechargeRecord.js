var rechargeRecord =[];

$(document).ready(function () {
    getRequest(
        '/user/vip/history?userId=' + getCookie('id'),
        function (res) {
            if (res.success){
                rechargeRecord = res.content;
                renderRecord();
            } else {
                alert(res.message)
            }
        },
        function (error) {
            alert(error)
        }
    );
});

function renderRecord() {
    var str = '';
    rechargeRecord.forEach(function (record) {
        str += "<tr>" + "<td style='width: 195px'>"+ record.date.replace('T',' ').substring(0,19) + "</td>" +
            "<td style='width: 100px'>" + record.delta +"</td>" +
            "<td style='width: 100px'>" + (record.originalAmount + record.delta)+"</td>" +
            "</tr>"
    });

    $("#tbody0").append(str)
}