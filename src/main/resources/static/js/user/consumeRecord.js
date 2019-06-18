var consumeRecord = [];
$(document).ready(function () {
    let userID = getCookie('id');
    getRequest(
        '/user/purchaseRecord?userId=' + getCookie('id'),
        function (res) {
            if (res.success){
                consumeRecord = res.content;
                consumeRecord.forEach(function (record) {
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
        let recordStr = '';
        recordStr +=
            "<tr>"+
            "<td style=\"width: 115px\">"+ record.movieName+"</td>" +
            "<td style=\"width: 195px\">"+ (record.startTime).substring(0,10)+"</td>" +
            "<td style=\"width: 80px\">"+ record.ticketVOs.length +"张</td>" +
            "<td style='width: 120px'>" + record.originalSpend + "元</td>" +
            "<td style='width: 120px'>" + record.realSpend + "元</td>" +
            "</tr>" ;
        $(".table tbody").append(recordStr);
    }
    
    function getTicketInfo(record) {
        let ticketStr = '';
        record.ticketVOs.forEach(function (ticketVO) {
            ticketStr +=
                "电影名称："+ ticketVO.name +
                "放映影厅："+ ticketVO.hallname +
                "座位：" + (ticketVO.row) + "排" + (ticketVO.column) + "座" +
                "开始时间：" + ticketVO.startDate +
                "结束时间：" + ticketVO.endDate;
            }
        )
    }
    }
    
);