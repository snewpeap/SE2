var consumeRecord = [];
var order;
$(document).ready(function () {
    getRequest(
        '/user/purchaseRecord?userId=' + getCookie('id'),
        function (res) {
            if (res.success){
                consumeRecord = res.content;
                consumeRecord.forEach(function (record) {
                    order = record;
                    addRecord(record)
                })
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    );

    function addRecord(record) {
        var recordStr = '';
        recordStr +=
            "<tr id='"+record.orderID+"' title='";
        record.ticketVOs.forEach(function (ticket) {
            recordStr += '电影名称：'+record.movieName + '          座位：' + ticket.row + '排' + ticket.column + '座' + '          电影票状态：' + ticket.status + '&#10;'
        });
        recordStr += "'>"+
            "<td>"+ record.movieName+"</td>" +
            "<td>"+ record.startTime.replace('T',' ').substring(0,19)+"</td>" +
            "<td>"+ record.ticketVOs.length +"张</td>" +
            "<td>" + record.originalSpend + "元</td>" +
            "<td>" + (record.realSpend).toFixed(2) + "元</td>" +
            "<td>"+record.completeTime.replace('T',' ').substring(0,19)+"</td>" +
            "</tr>" ;
        $(".table tbody").append(recordStr);
    }
    }
);