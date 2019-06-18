var strategyAll;
var strategyTarget;
var tdTarget,tdDiscount,tdDiscountEdit,tdAction,tdActionEdit;
var discountId;

$(document).ready(function () {
    getVipStrategy();
});

function getVipStrategy() {
    getRequest(
        '/admin/vip/reduction/get',
        function (res) {
            if(res.success){
                strategyAll = res.content;
                renderStrategy();
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderStrategy() {
    $('tbody').empty();
    var tableDom = "";
    strategyAll.forEach(function (strategy) {
        tableDom += "<tr>" +
            "<td class='td-target-"+strategy.targetAmount+"'>"+strategy.targetAmount+"</td>" +
            "<td class='td-type-"+strategy.targetAmount+"'>满减</td>" +
            "<td class='td-discount-"+strategy.targetAmount+"'>"+strategy.discountAmount+"</td>" +
            "<td class='td-discount-"+strategy.targetAmount+"-edit' style='display: none;'><input class='form-control' type='number' min='1' id='discount_"+strategy.targetAmount+"' value='"+strategy.discountAmount+"' style='display: inline-block;width: 100px;'></td> " +
            "<td class='td-action-"+strategy.targetAmount+"'><a class='modifyStrategy' id='"+strategy.targetAmount+"'>修改</a>|<a class='deleteStrategy' id='"+strategy.targetAmount+"'>删除</a></td>" +
            "<td class='td-action-"+strategy.targetAmount+"-edit' style='display: none;'><a class='confirm-modify' id='"+strategy.targetAmount+"'>确认</a>|<a class='cancel-modify' id='"+strategy.targetAmount+"'>取消</a></td>" +
            "</tr>";
    });
    $('tbody').append(tableDom);
}

$(document).on('click','.modifyStrategy',function (e) {
    strategyTarget = e.target.id;
    set(strategyTarget);
    $(tdDiscount).css('display','none');
    $(tdDiscountEdit).css('display','');
    $(tdAction).css('display','none');
    $(tdActionEdit).css('display','');
});

$(document).on('click','.deleteStrategy',function (e) {
    strategyTarget = e.target.id;
    set(strategyTarget);
    var r = confirm('你确定要删除该充值优惠吗？');
    if(r){
        postRequest(
            '/admin/vip/reduction/delete?targetAmount='+ strategyTarget,
            {},
            function (res) {
                if(res.success){
                    getVipStrategy();
                }else{
                    alert(res.message);
                }
            },
            function (res) {
                alert(res.message);
            }
        )
    }
});

$(document).on('click','.confirm-modify',function (e) {
    strategyTarget = e.target.id;
    set(strategyTarget);
    var form = {
        targetAmount: $(tdTarget).text(),
        discountAmount: $(discountId).val()
    };
    var r = confirm('你确定要修改该充值优惠吗？');
    if(r){
        if(form.discountAmount <= 0){
            alert('优惠金额应大于零！')
        }else{
            postRequest(
                '/admin/vip/reduction/modify',
                form,
                function (res) {
                    if(res.success){
                        getVipStrategy();
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
});

$(document).on('click','.cancel-modify',function (e) {
    strategyTarget = e.target.id;
    set(strategyTarget);
    $(tdDiscount).css('display','');
    $(tdDiscountEdit).css('display','none');
    $(tdAction).css('display','');
    $(tdActionEdit).css('display','none');
});

function set(strategyTarget) {
    discountId = '#discount_' + strategyTarget;
    tdTarget = '.td-target-' + strategyTarget;
    tdDiscount = '.td-discount-' + strategyTarget;
    tdDiscountEdit = '.td-discount-' + strategyTarget + '-edit';
    tdAction = '.td-action-' + strategyTarget;
    tdActionEdit = '.td-action-' + strategyTarget + '-edit';
}

function addStrategy() {
    var tableDom = "";
    tableDom += "<tr>" +
        "<td class='td-target-add''><input class='form-control' type='number' min='1' id='addTarget' style='display: inline-block;width: 100px;'></td>" +
        "<td>满送</td>" +
        "<td class='td-discount-add'><input class='form-control' type='number' min='1' id='addDiscount' style='display: inline-block;width: 100px;'></td>" +
        "<td class='td-action-add'><a class='confirm-add'>提交</a>|<a class='cancel-add'>取消</a></td>" +
        "</tr>";
    $('tbody').append(tableDom);
}

$(document).on('click','.confirm-add',function () {
    var form = {
        targetAmount: $('#addTarget').val(),
        discountAmount: $('#addDiscount').val()
    };
    var r = confirm('你确定要增加该充值策略吗？');
    if(r){
        if(form.targetAmount <= 0){
            alert('目标金额应大于0！')
        }else if(form.discountAmount <= 0){
            alert('优惠金额应大于0！')
        }else{
            postRequest(
                '/admin/vip/reduction/add',
                form,
                function (res) {
                    if(res.success){
                        getVipStrategy();
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
});


$(document).on('click','.cancel-add',function () {
    getVipStrategy();
});