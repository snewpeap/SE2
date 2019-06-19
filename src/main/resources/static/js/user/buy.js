var selectedSeats = [];
var scheduleId;
var order = {ticketId: [], couponId: 0};
var orderWithCoupon;
var isVIP = false;
var useVIP = true;
var price = 0.0;
var totalVO;
var coupons = [];
// var activities = [];
var ticketId = [];
var orderId;
var movieId;
var fare;
var hall;
var movie;
var chosenSeatId = [];
var actualTotal = 0.0;
var VIPCardBalance = 0.0;
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
                $('#movie-img').append('<img style="width:280px" src=" ' + (res.content.poster||'/images/defaultPoster.jpg') + '"/>');
                // $('#movie-language').append(res.content.language);
                // $('#movie-type').append(res.content.type);
                // $('#movie-length').append(res.content.duration);
            } else {
                alert(res.message);
            }
        }
    )
}

function getInfo() {
    getRequest(
        '/user/seat/get?arrangementId=' + scheduleId,
        function (res) {
            if (res.success) {
                var seats = res.content.seatMap;
                var startTime = res.content.startTime;
                fare = res.content.fare;
                movie = res.content.movie;
                hall = res.content.hall;
                $('#schedule-hall-name').text(hall);
                $('#order-movie-name').text(movie);
                $('#order-schedule-hall-name').text(hall);
                $('#schedule-fare').text(fare.toFixed(2));
                $('#order-schedule-fare').text(fare.toFixed(2));
                $('#schedule-time').text(startTime.substring(5, 7) + "月" + startTime.substring(8, 10) + "日 " + startTime.substring(11, 16) + "场");
                $('#order-schedule-time').text(startTime.substring(5, 7) + "月" + startTime.substring(8, 10) + "日 " + startTime.substring(11, 16) + "场");
                var hallDomStr = "";
                var seat = "";
                for (var i = 0;i<seats.length;i++){
                    seat += "<div>";
                    for (var j = 0;j<seats[i].length;j++){
                        if (seats[i][j].locked){
                            seat += "<button class='cinema-hall-seat-lock'></button>";
                        } else {
                            seat += "<button class='cinema-hall-seat-choose' id='" + seats[i][j].seatId + "' onclick='seatClick(" + seats[i][j].seatId  + "," + (i+1) + "," + (j+1) + ")'></button>";
                        }
                    }
                    seat += "</div>";
                }
                var hallDom =
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
                orderWithCoupon = res.content;
                if(orderWithCoupon != null){
                    if (orderWithCoupon.tickets.length !==0 ){
                        var r = confirm("您之前选的座位还未付款，点击确认继续购买");
                        if(r){
                            orderId = orderWithCoupon.id;
                            getOrder();
                        }else{
                            cancelTickets(orderWithCoupon.id);
                        }
                    }
                }
            }
        })
}

function cancelTickets(orderId) {
    postRequest(
        "/user/ticket/cancel",
        orderId,
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


function seatClick(id,i,j) {
    // chosenSeatId.push(id);
    var seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");
        chosenSeatId.push(id);
        selectedSeats[selectedSeats.length] = [i,j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");
        chosenSeatId = chosenSeatId.filter(function (value) {
            return value!==id;
        });
        selectedSeats = selectedSeats.filter(function (value) {
            return value[0]!==i || value[1]!==j;
        })
    }
    // selectedSeats.sort(function (x, y) {
    //     var res = x[0] - y[0];
    //     return res === 0 ? x[1] - y[1] : res;
    // });

    var seatDetailStr = "";
    if (chosenSeatId.length === 0) {
        seatDetailStr += "还未选择座位";
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0]) + "排" + (seatLoc[1]) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}
// 获得活动列表？
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

function renderCoupon(couponsAll) {
    var couponTicketStr = "";
    if (couponsAll.length === 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + totalVO);
        $('#pay-amount').html("<div><b>金额：</b>" + totalVO + "元</div>");
    } else {
        for (let coupon of couponsAll) {
            if(price * ticketId.length >= coupon.targetAmount){
                couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
                coupons.push(coupon);
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

function getTicket() {
    getRequest(
        '/user/ticket/get/existing?scheduleId=' + scheduleId,
        function (res) {
            if (res.success){
                orderWithCoupon = res.content;
                orderWithCoupon.tickets.forEach(function (ticket) {
                        if (ticket.status === "未完成" && ticket.arrangementId === scheduleId){
                            ticketId.push(ticket.id);
                            var ticketStr="";
                            ticketStr += "<div>" + (ticket.row) + "排" + (ticket.column) + "座</div>";
                            order.ticketId.push(ticket.id);
                            $('#order-tickets').append(ticketStr);
                        }
                    }
                );

                var num = "<div>" + ticketId.length + "张</div>";
                $('#order-ticket-num').html(num);

                var total = price * ticketId.length;
                totalVO = total.toFixed(2);
                $('#order-total').text(totalVO);
                $('#order-footer-total').text("总金额： ¥" + totalVO);

                renderCoupon(orderWithCoupon.coupons);

            }else {
                alert(res.message);
            }
        }
    )
}

function orderConfirmClick() {
    // var seats2 = [];
    // selectedSeats.forEach(function (seat) {
    //     var seatData = {
    //         columnIndex: seat[1],
    //         rowIndex: seat[0]
    //     };
    //     seats2.push(seatData);
    // });

    postRequest(
        '/user/ticket/lockSeats/' + scheduleId + "?userId=" + getCookie('id'),
        chosenSeatId
        ,function (res) {
            if (res.success){
                orderId = res.content.id;
                getOrder();
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
                VIPCardBalance = res.content.balance.toFixed(2);
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
    useVIP = (type === 0);
    if (type === 0) {
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
    actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

function payConfirmClick() {
    var payMethod = useVIP ? 'vip' : 'alipay';
    //postPayRequest();
    if (payMethod === 'vip'){
        if (parseInt(actualTotal) >= parseInt(VIPCardBalance)){
            alert("会员卡余额不足！请使用支付宝支付或充值后重新支付！")
        } else {
            postRequest('/user/vip/pay/' + orderId,
                order.couponId
                ,
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
                        $('.success-notice').empty();
                        $('.success-notice').append(res.message);
                        $('.success-notice').append('<div class="hint">至<a class="hint" href="/user/ticket">“我的电影票”</a>页面查看</div>')
                    }
                },
                function (err) {
                    alert(err);
                });
        }
    }else {
        postRequest('/user/alipay/pay/' + orderId,
            order.couponId
            ,
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
                    $('.success-notice').empty();
                    $('.success-notice').append(res.message);
                    $('.success-notice').append('<div class="hint">至<a class="hint" href="/user/ticket">“我的电影票”</a>页面查看</div>')
                }
            },
            function (err) {
                alert(err);
            });
    }
}
