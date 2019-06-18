var orderVOList = [];
var ticketId;
$(document).ready(function(){
    getTicketList();
});

function getTicketList() {
    $('tbody').empty();
    getRequest(
        '/user/purchaseRecord',
        function (res) {
            if (res.success){
                orderVOList = res.content;
                orderVOList.forEach( function (orderVO) {
                    var ticketList = orderVO.ticketVOs;
                    ticketList.forEach(function (ticketVO) {
                        var str = "<tr>"+ "<td style=\"width: 115px\">"+orderVO.movieName+"</td>" +
                            "<td style=\"width: 70px\">"+ orderVO.hallName+"</td>" +
                            "<td style=\"width: 80px\">"+(ticketVO.row)+"排"+(ticketVO.column)+"座</td>" +
                            "<td style=\"width: 195px\">"+ orderVO.startTime.replace('T',' ').substring(0,19) +"</td>" +
                            "<td style=\"width: 195px\">"+ orderVO.endTime.replace('T',' ').substring(0,19) +"</td>" +
                            "<td style=\"width: 80px\">"+ticketVO.status+"</td>";
                        if (ticketVO.status==="已完成"){
                            var now = new Date();
                            var d1 = new Date(Date.parse(orderVO.startTime.substring(0,10).replace('-\g','/')));
                            if (now<d1){
                                str += "<td style='width: 80px'><a class='ticket' id='"+ticketVO.id+"' style='text-decoration: underline;color: #4381ff;'>退票</a></td>";
                            } else {
                                str += "<td style='width: 80px'><a id='@ticketId' style='color: #ed5565'>不可退票</a></td>"
                            }
                        } else {
                            str += "<td style='width: 80px'><a id='@ticketId' style='color: #ed5565'>不可退票</a></td>";
                        }
                        $("tbody").append(str);
                    })
                });
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error)
        }
    )
}


$(document).on('click','.ticket',function(e){
    ticketId = e.target.id;
    postRequest(
        '/user/ticket/refund',
        ticketId,
        function (res) {
            if (res.success){
                alert("退票成功！");
                getTicketList();
            } else {
                alert(res.message)
            }
        },
        function (error) {
            alert(error.message)
        }
    )
});
