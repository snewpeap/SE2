var vips;
var money;
var coupons;

$(document).ready(function () {
    getVips();
    getCoupons();
});

function getVips() {
    getRequest(
        '/admin/vip/get',
        function (res) {
            if(res.success){
                vips = res.content;
                renderVips();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);}
    )
}

function renderVips() {
    money = $('#moneyInput').val();
    if(money >= 0){
        $('.vipTbody').empty();
        var tableDom = "";
        vips.forEach(function (vip) {
            if(vip.consumption >= money){
                tableDom += "<tr class='even pointer'>" +
                    "<td class='a-center'><input type='checkbox' class='checkVip' id='"+vip.vipcardID+"'></td>" +
                    "<td><div class='vip-content'>"+vip.vipcardID+"</div></td>" +
                    "<td><div class='vip-content'>"+vip.user.name+"</div></td>" +
                    "<td><div class='vip-content'>"+vip.consumption+"</div></td>" +
                    "</tr>";
            }
        });
        $('.vipTbody').append(tableDom);
    }else {
        alert('筛选金额应大于等于零！')
    }
}

function getCoupons(){
    getRequest(
        '/admin/promotion/get',
        function (res) {
            if(res.success){
                coupons = res.content;
                renderCoupons();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderCoupons(){
    $('.couponTbody').empty();
    var tableDom = "";
    coupons.forEach(function (activity) {
        if(new Date(activity.endTime).getTime() >= new Date().getTime()){
            tableDom += "<tr class='even pointer'>" +
                "<td class='a-center'><input type='checkbox' class='checkCoupon' id='"+activity.id+"'></td>" +
                "<td><div class='vip-content'>"+activity.name+"</div></td>" +
                "<td><div class='vip-content'>"+activity.targetAmount+"</div></td>" +
                "<td><div class='vip-content'>"+activity.couponAmount+"</div></td>" +
                "<td><div class='vip-content'>"+activity.couponExpiration+"</div></td>" +
                "</tr>";
        }
    });
    $('.couponTbody').append(tableDom);
}

$(document).on('click','#check-all',function () {
    if($('#check-all').prop('checked')){
        $('input[class=checkVip]').each(function () {
            $(this).prop('checked',true);
        })
    }else{
        $('input[class=checkVip]').each(function () {
            $(this).prop('checked',false);
        })
    }
    handOutAble();
});

$(document).on('click','.checkVip',function () {
    var checkAll=true;
    $('input[class=checkVip]').each(function () {
        if(!$(this).prop('checked')){
            checkAll = false;
        }
    });
    if(!checkAll){
        $('#check-all').prop('checked',false);
    }
    handOutAble();
});

$(document).on('click','.checkCoupon',function () {
    if($(this).prop('checked')){
        $('input[class=checkCoupon]').each(function () {
            $(this).prop('checked',false);
        });
        $(this).prop('checked',true);
    }
    handOutAble();
});

function handOutAble(){
    var checkVip=false,checkCoupon=false;
    $('input[class=checkVip]').each(function () {
        if($(this).prop('checked')){
            checkVip = true;
        }
    });
    $('input[class=checkCoupon]').each(function () {
        if($(this).prop('checked')){
            checkCoupon = true;
        }
    });

    if(checkVip&&checkCoupon){
        $('.handOutButton').attr('disabled',false);
    }else{
        $('.handOutButton').attr('disabled','disabled');
    }
}

$('.handOutButton').click(function () {
    var vipsSelected=[],promotionIDsSelected=[];
    $('input[class=checkVip]:checked').each(function () {
        vipsSelected.push($(this).prop('id'));
    });
    $('input[class=checkCoupon]:checked').each(function () {
        promotionIDsSelected.push($(this).prop('id'));
    });
    postRequest(
        '/admin/vip/presentCoupon',
        {
            vips:vipsSelected,
            promotionIDs:promotionIDsSelected
        },
        function (res) {
            if(res.success){
                alert('赠送成功！');
                getVips();
                getCoupons();
                handOutAble();
                $('#check-all').prop('checked',false);
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
});
