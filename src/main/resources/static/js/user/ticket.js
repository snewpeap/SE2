var ticketList = [];

$(document).ready(function(){
    getTicketList();

    function getTicketList() {
        getRequest(
            '/user/ticket/get/existing',
            function (res) {
                if (res.success){
                    ticketList = res.content;
                    ticketList.forEach(function (ticket) {
                        renderTicketList(ticket);
                    })
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error)
            }
        )
    }

    function renderTicketList(ticket) {
        var ticketDomStr = '';
        ticketDomStr +=
            "<tr>"+
            "<td style=\"width: 115px\">"+ticket.schedule.movieName+"</td>" +
            "<td style=\"width: 70px\">"+ticket.schedule.hallName+"</td>" +
            "<td style=\"width: 80px\">"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>" +
            "<td style=\"width: 195px\">"+start+"</td>" +
            "<td style=\"width: 195px\">"+end+"</td>" +
            "<td style=\"width: 80px\">"+ticket.state+"</td>" +
            "<td style='width: 80px'><a id='ticketId' @click='refund()'>退票</a></td>" +
            "</tr>";
        $(".table tbody").append(ticketDomStr);
    }

    $("#ticketId").click(function () {
        refund(ticketId);
    });

    //todo: refund
    function refund(e) {
        postRequest(
            '/user/ticket/refund',
            ticketId,
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

    }

});