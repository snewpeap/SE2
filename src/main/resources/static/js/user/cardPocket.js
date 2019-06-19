$(document).ready(function () {
    // $(".gray-text")[0].innerText = sessionStorage.getItem("username");

    getVIP();
    getCoupon();
});

var isBuyState = true;
var vipCardId;

function getVIP() {
    getRequest(
        '/user/vip/card/get?userId=' + getCookie('id'),
        function (res) {
            if (res.success) {
                // 是会员
                //$("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");

                vipCardId = res.content.userId;
                $("#member-id").text(vipCardId);
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));
                //$("#member-joinDate").text(res.content.joinDate.substring(0, 10));
            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
                //$("#nonmember-card").css("visibility", "visible");
            }
        },
        function (error) {
            alert(error);
        });


    getRequest(
        '/user/vip/rechargeReduction',
        function (res) {
            if (res.success) {
                if(res.content.length > 0){
                    var str = "<div>";
                    res.content.forEach(function (one) {
                        str += "<div>满"+ one.targetAmount + "减"+ one.discountAmount +"</div>";
                    });
                    str += "</div>";
                    $("#member-buy-description").html("<div class='description'>充值优惠：</div>"+str);
                    $("#member-description").html(str);
                }else {
                    var str = "<div> 无 </div>";
                    $("#member-buy-description").html("<div class='description'>充值优惠：</div>"+str);
                    $("#member-description").html(str);
                }
            } else {
                alert(res.content);
            }
        },
        function (error) {
            alert(error);
        });
}

function buyClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
}

function chargeClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
            if (isBuyState) {
                getRequest(
                    '/user/vip/card/add?userId=' + getCookie('id'),
                    function (res) {
                        if (res.success){
                            $('#buyModal').modal('hide');
                            getVIP();
                        } else {
                            if(res.statusCode===777){
                                postRequest(
                                    '/user/vip/card/add/'+res.content.id,
                                    {},
                                    function (res) {
                                        if(res.success){
                                            $('#buyModal').modal('hide');
                                            alert("购买会员卡成功");
                                            getVIP();
                                        }else{
                                            alert(res.message);
                                        }
                                    },
                                    function (res) {
                                        alert(res.message);
                                    }
                                );
                            }
                        }
                    },
                    function (error) {
                        alert(error.message);
                    });
            } else {
                var orderId;
                getRequest(
                    '/user/vip/deposit?amount=' + parseInt($('#userMember-amount').val()),
                    function (res) {
                        orderId = res.content.id;
                        postRequest(
                            '/user/vip/deposit/'+ orderId,
                            {},
                            function (res) {
                                if (res.success){
                                    $('#buyModal').modal('hide');
                                    alert("充值成功！");
                                    getVIP();
                                } else {
                                    alert(res.message)
                                }
                            },
                            function (error) {
                                alert(error)
                            }
                        )
                    },
                    function (error) {
                        alert(error);
                    });
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/user/coupon/get?userId=' + getCookie('id'),
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.promotionName +
                        '</div>' +
                        '<div class="description">' +
                        coupon.promotionDescription +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startDay) + ' ~ ' + formatDate(coupon.endDay) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}