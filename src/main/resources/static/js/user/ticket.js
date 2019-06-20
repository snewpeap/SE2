var allRefundStrategy = [];
var minRefundStrategy;
var refundable = false;
var orderVOList = [];
var ticketId;
$(document).ready(function(){
    getRefundStrategy();
});

function getRefundStrategy() {
    getRequest(
        '/user/refundStrategy',
        function (res) {
            if(res.success){
                allRefundStrategy = res.content;
                getTicketList();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function getMaxRefundable(dateStr) {
    var date1 = new Date(dateStr.substring(0,10));
    date1.setDate(date1.getDate()+1);
    var dateStamp = date1.getTime();
    var todayStamp = new Date().getTime();
    var dayInterval = (dateStamp-todayStamp)/86400000;
    for(var i = allRefundStrategy.length-1;i>=0;i--) {
        if (allRefundStrategy[i].refundable === 1 && allRefundStrategy[i].day < dayInterval) {
            return allRefundStrategy[i];
        }
    }
    return null;
}

function getTicketList() {
    $('tbody').empty();
    getRequest(
        '/user/purchaseRecord',
        function (res) {
            if (res.success){
                orderVOList = res.content;
                orderVOList.forEach( function (orderVO) {
                    var ticketList = orderVO.ticketVOs;
                    // ticketList.sort(function (a,b) {
                    //     return a.date - b.date;
                    // });
                    // console.log(ticketList);
                    ticketList.forEach(function (ticketVO) {
                        let str = "";
                        if (ticketVO.status === "已完成"){
                            str += "<tr style='background-color: #ffffff'>"
                        } else if (ticketVO.status === "未完成"){
                            str += "<tr style='background-color: #e1f7e7'>"
                        } else if (ticketVO.status === "已失效") {
                            str += "<tr style='background-color: #dcf4f5'>"
                        } else if (ticketVO.status === "已退票") {
                            str += "<tr style='background-color: #e8eaee'>"
                        }
                        str += "<td style=\"width: 115px\">"+orderVO.movieName+"</td>" +
                            "<td style=\"width: 70px\">"+ orderVO.hallName+"</td>" +
                            "<td style=\"width: 80px\">"+(ticketVO.row)+"排"+(ticketVO.column)+"座</td>" +
                            "<td style=\"width: 195px\">"+ orderVO.startTime.replace('T',' ').substring(0,19) +"</td>" +
                            "<td style=\"width: 195px\">"+ orderVO.endTime.replace('T',' ').substring(0,19) +"</td>" +
                            "<td style=\"width: 80px\">"+ticketVO.status+"</td>";
                        if (ticketVO.status==="已完成"){
                            if (getMaxRefundable(orderVO.startTime)!=null){
                                str += "<td style='width: 80px'><a class='ticket' id='"+ticketVO.id+"' data-order='"+JSON.stringify(orderVO)+"' style='text-decoration: underline;color: #4381ff;'>退票</a></td>";
                            } else {
                                str += "<td style='width: 80px'><a style='color: #ed5565'>不可退票</a></td>"
                            }
                        } else if(ticketVO.status === '未完成'){
                            str += "<td style='width: 80px'><a href='/user/buy?id="+orderVO.movieId+"&arrangementId="+ticketVO.arrangementId+"' style='text-decoration: underline;color: #4381ff;'>去完成</a></td>"
                        }else {
                            str += "<td style='width: 80px'><a style='color: #ed5565'>不可退票</a></td>";
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
    var order = JSON.parse(e.target.dataset.order);
    var refund = getMaxRefundable(order.startTime);
    var r = confirm('当前退票仅可退回'+refund.percentage+'%，你确定要退票吗？');
    if(r){
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
        );
    }
});
