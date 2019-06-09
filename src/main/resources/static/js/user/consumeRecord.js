var consumeRecord = [];
$(document).ready(function () {
    var userID = sessionStorage.getItem(ID());
    getRequest(
        '/ticket/purchaseRecord?userId=' + userID,
        function (res) {
            if (res.success){
                consumeRecord = res.content;
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    )

    function renderTicketList(ticket) {
        var ticketDomStr = '';
        var start = ticket.schedule.startTime.slice(0,19).replace('T',' ');
        var end  = ticket.schedule.endTime.slice(0,19).replace('T',' ');
        ticketDomStr +=
            "<tr>"+
            "<td style=\"width: 115px\">"+ticket.schedule.movieName+"</td>" +
            "<td style=\"width: 70px\">"+ticket.schedule.hallName+"</td>" +
            "<td style=\"width: 80px\">"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>" +
            "<td style=\"width: 195px\">"+start+"</td>" +
            "<td style=\"width: 195px\">"+end+"</td>" +
            "<td style=\"width: 80px\">"+ticket.state+"</td>"+
            "</tr>";
        $(".table tbody").append(ticketDomStr);
    }
    
    }
);