var orderVOList = [];

$(document).ready(function(){
    getTicketList();

    function getTicketList() {
        getRequest(
            '/user/purchaseRecord',
            function (res) {
                if (res.success){
                    orderVOList = res.content;
                    orderVOList.forEach( function (orderVO) {
                        let ticketList = orderVO.ticketVOs;
                        ticketList.forEach(function (ticketVO) {
                            let str = "<tr>"+ "<td style=\"width: 115px\">"+orderVO.movieName+"</td>" +
                                "<td style=\"width: 70px\">"+ orderVO.hallName+"</td>" +
                                "<td style=\"width: 80px\">"+(ticketVO.row)+"排"+(ticketVO.column)+"座</td>" +
                                "<td style=\"width: 195px\">"+ (orderVO.startTime).substring(0,10) +"</td>" +
                                "<td style=\"width: 195px\">"+ (orderVO.endTime).substring(0,10) +"</td>" +
                                "<td style=\"width: 80px\">"+ticketVO.status+"</td>";
                            if (ticketVO.status==="已完成"){
                                let now = new Date();
                                let d1 = new Date(Date.parse(orderVO.startTime.substring(0,10).replace('-','/')));
                                if (now<d1){
                                    str += "<td style='width: 80px'><button class='ticket' id='@ticketId' onclick='refund(e)'>退票</button></td>";
                                } else {
                                    str += "<td style='width: 80px'><button id='@ticketId'>不可退票</button></td>"
                                }
                            } else {
                                str += "<td style='width: 80px'><a id='@ticketId'>不可退票</a></td>";
                            }
                            $(".table tbody").append(str);
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

    // $("#ticketId").click(function () {
    //     refund(ticketId);
    // });



});

$(document).on('click','.ticket',function(e){
    let id = e.target.id;
    postRequest(
        '/user/ticket/refund?userId=' + getCookie('id'),
        {
            id
        },
        function (res) {
            if (res.success){
                alert("退票成功！")
            } else {
                alert(res.message)
            }
        },
        function (error) {
            alert(error)
        }
    )
});
