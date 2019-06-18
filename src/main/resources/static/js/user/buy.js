var selectedSeats = [];
var scheduleId;
var order = {ticketId: [], couponId: 0};
var isVIP = false;
var useVIP = true;
var price = 0.0;
var totalVO;
var coupons = [];
var activities = [];
var ticketId = [];
var orderId;
var movieId;
let fare;
let hall;
let movie;
let chosenSeatId = [];

$(document).ready(function () {
    // $(".gray-text")[0].innerText = sessionStorage.getItem("username");
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);
    movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    getInfo();
    getMovie();


});
function getMovie() {
    getRequest(
        '/user/movie/' + movieId,
        function (res) {
            if (res.success){
                $('#movie-img').append('<img style="width:280px" src=" ' + res.content.poster + '"/>');
                $('#movie-language').append(res.content.language);
                $('#movie-type').append(res.content.type);
                $('#movie-length').append(res.content.duration);
            } else {
                alert(res.message);
            }
        }
    )
}
// function getSchedule() {
//     getRequest(
//         '/user/'
//     )
// }
function getInfo() {
    getRequest(
        '/user/seat/get?arrangementId=' + scheduleId,
        function (res) {
            if (res.success) {
                let seats = res.content.seatMap;
                let startTime = res.content.startTime;
                fare = res.content.fare;
                movie = res.content.movie;
                hall = res.content.hall;
                $('#schedule-hall-name').text(hall);
                $('#order-schedule-hall-name').text(hall);
                $('#schedule-fare').text(fare.toFixed(2));
                $('#order-schedule-fare').text(fare.toFixed(2));
                $('#schedule-time').text(startTime.substring(5, 7) + "月" + startTime.substring(8, 10) + "日 " + startTime.substring(11, 16) + "场");
                $('#order-schedule-time').text(startTime.substring(5, 7) + "月" + startTime.substring(8, 10) + "日 " + startTime.substring(11, 16) + "场");
                let hallDomStr = "";
                let seat = "";
                for (let i = 0;i<seats.length;i++){
                    let tempStr = "";
                    for (let j = 0;j<seats.length;j++){
                        if (seats[i][j].isLocked){
                            tempStr += "<button class='cinema-hall-seat-lock'></button>";
                        } else {
                            tempStr += "<button class='cinema-hall-seat-choose' id='" + seats[i][j].seatId + "' onclick='seatClick(\"" + seats[i][j].seatId  + "\"," + i + "," + j + ")'></button>";
                        }
                    }
                    seat += "<div>" + tempStr + "</div>";
                }
                let hallDom =
                    "<div class='cinema-hall'>" +
                    "<div>" +
                    "<span class='cinema-hall-name'>" + hall + "</span>" +
                    "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
                    "</div>" +
                    "<div class='cinema-seat'>" + seat +
                    "</div>" +
                    "</div>";
                hallDomStr += hallDom;
                $('#hall-card').html(hallDomStr);

                price = fare;

                setTimeout(getExistingTicket,100);
            }},
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function getExistingTicket() {
    getRequest(
        "/user/ticket/get/existing?scheduleId=" + scheduleId,
        function (res) {
            if (res.success){
                let tickets = res.content;
                if(tickets != null){
                    if (tickets.length !==0 ){
                        let r = confirm("您之前选的座位还未付款，点击确认继续购买");
                        if(r){
                            getOrder();
                        }else{
                            cancelTickets(tickets);
                        }
                    }
                }
            }
        })
}

//todo orderId?
function cancelTickets() {
    postRequest(
        "/user/ticket/cancel?userId=" + getCookie('id'),
        {
            orderId
        },
        function (res) {
            if(res.success){
                getInfo();
            }
            else{
                alert(res.message);
            }
        },
        function (err) {
            alert(err);
        }
    )
}


function seatClick(id, i, j) {
    chosenSeatId.push(id);
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length === 0) {
        seatDetailStr += "还未选择座位";
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}
// todo 获得活动列表？
// function getActivity(){
//     getRequest(
//         '/activity/get',
//         function (res) {
//             if (res.success){
//                 activities = res.content;
//             }
//         },
//         function (error) {
//             alert(error);
//         }
//     )
// }

function getCoupon() {
    getRequest(
        '/user/coupon/get?userId=' + getCookie('id'),
        function (res) {
            if (res.success){
                var couponTicketStr = "";
                if (res.content.length === 0) {
                    $('#order-discount').text("优惠金额：无");
                    $('#order-actual-total').text(" ¥" + totalVO);
                    $('#pay-amount').html("<div><b>金额：</b>" + totalVO + "元</div>");
                } else {
                    for (let coupon of res.content) {
                        if(price * ticketId.length > coupon.targetAmount){
                            coupons.push(coupon);
                            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
                        }
                    }
                    $('#order-coupons').html(couponTicketStr);

                    if(coupons.length > 0){
                        changeCoupon(0);
                    }else{
                        $('#order-discount').text("优惠金额：无");
                        $('#order-actual-total').text(" ¥" + totalVO);
                        $('#pay-amount').html("<div><b>金额：</b>" + totalVO + "元</div>");
                    }
                }
            }
        },
        function (error) {
            alert(error);
        }
    )
}

function getTicket() {
    getRequest(
        '/user/ticket/get/existing?scheduleId=' + scheduleId + "&userId=" + getCookie('id'),
        function (res) {
            if (res.success){
                res.content.tickets.forEach(function (ticket) {
                        if (ticket.status === "未完成" && ticket.arrangementId === scheduleId){
                            ticketId.push(ticket.id);
                            let ticketStr="";
                            ticketStr += "<div>" + (ticket.rowIndex + 1) + "排" + (ticket.columnIndex + 1) + "座</div>";
                            order.ticketId.push(ticket.id);
                            $('#order-tickets').append(ticketStr);
                        }
                    }
                );

                let num = "<div>" + ticketId.length + "张</div>";
                $('#order-ticket-num').html(num);

                let total = price * ticketId.length;
                totalVO = total.toFixed(2);
                $('#order-total').text(totalVO);
                $('#order-footer-total').text("总金额： ¥" + totalVO);

                getCoupon();

            }else {
                alert(res.message);
            }
        }
    )
}

function orderConfirmClick() {
    let seats = [];
    selectedSeats.forEach(function (seat) {
        let seatData = {
            columnIndex: seat[1],
            rowIndex: seat[0]
        };
        seats.push(seatData);
    });

    postRequest(
        '/user/ticket/lockSeats/' + scheduleId + "?userId=" + getCookie('id'),
        chosenSeatId
        ,function (res) {
            if (res.success){
                getOrder();
                orderId = (res.content).ID;
            }
            else{
                alert(res.message);
            }
        },function (err) {
            alert(err);
        }
    )
}

function getOrder() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");

    // getActivity();
    getTicket();

    getRequest(
        '/user/vip/card/get?userId=' + getCookie('id'),
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (res) {
        })
}

function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}



function changeCoupon(couponIndex) {
    order.couponId = coupons[couponIndex].id;
    $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
    let actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

//todo 主要在支付
function payConfirmClick() {
    let payMethod = useVIP ? 'vip' : 'alipay';
    //postPayRequest();
    postRequest('/user/' + payMethod + '/pay/' + orderId,
        function (res) {
            if (res.success) {
                if (useVIP){
                    $('#order-state').css("display", "none");
                    $('#success-state').css("display", "");
                    $('#buyModal').modal('hide');
                } else {
                    document.write(res.content);
                }
            } else {
                alert(res.message);
            }
        },
        function (err) {
            alert(err);
        });
}

function postPayRequest() {
    console.log(ticketId);
    console.log(order.couponId);
    postRequest('/user/ticket/pay?userId=' + getCookie('id') + "&couponId=" + order.couponId,
        {ticketId:ticketId},
        function(res){
            if(res.success === true){
                $('#order-state').css("display", "none");
                $('#success-state').css("display", "");
                $('#buyModal').modal('hide');
            }else{
                alert(res.message);
            }
        },
        function (err) {
            alert(err);
        });
}

function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}