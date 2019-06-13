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
                //$("#nonmember-card").css("display", "none");

                vipCardId = res.content.getId();
                $("#member-id").text(vipCardId);
                $("#member-balance").text("¥" + res.content.getBalance().toFixed(2));
                //$("#member-joinDate").text(res.content.joinDate.substring(0, 10));
            } else {
                // 非会员
                //$("#member-card").css("display", "none");
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
                let str = "";
                res.content.forEach(function (one) {
                    str += "<div class=\"price\"><b>" + one.getPrice() + "</b>" +
                        "<div class='description'>充值优惠：满"+ one.getTargetAmount() + "减"+ one.getDiscountAmount +"</div>" +
                        "<button onclick=\"buyClick()\">立即购买</button>"
                });
                $("#toBuy").append(str);
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
                postRequest(
                    '/user/vip/card/add?userId=' + getCookie('id'),
                    null,
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("购买会员卡成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            } else {
                postRequest(
                    '/user/vip/card/deposit?=userId' + getCookie('id'),
                    {amount: parseInt($('#userMember-amount').val())},
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("充值成功");
                        getVIP();
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
    let isValidate = true;
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
        '/user/coupon/get',
        function (res) {
            if (res.success) {
                let couponList = res.content;
                let couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
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