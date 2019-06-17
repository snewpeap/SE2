var refundAll;
var refundDay;
var tdDay,tdPercentage,tdPercentageEdit,tdAction,tdActionEdit,tdActionBan;
var percentageId;

$(document).ready(function () {
    getRefundStrategy()
});

function getRefundStrategy() {
    getRequest(
        '/admin/refund/get',
        function (res) {
            if(res.success){
                refundAll = res.content;
                renderRefunds()
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderRefunds() {
    $('tbody').empty();
    var tableDom = "";
    refundAll.forEach(function (refund) {
        tableDom += "<tr>" +
            "<td class='td-day-"+refund.day+"'>"+refund.day+"</td>" +
            "<td class='td-percentage-"+refund.day+"'>"+refund.percentage+"</td>" +
            "<td class='td-percentage-"+refund.day+"-edit' style='display: none;'><input class='form-control' type='number' min='0' max='100' id='percentage_"+refund.day+"' value='"+refund.percentage+"' style='display: inline-block;width: 100px;'></td>";
        if(refund.refundable===1){
            tableDom += "<td>使用中</td>" +
                "<td class='td-action-"+refund.day+"'><a class='modifyRefund' id='"+refund.day+"'>修改</a>|<a class='banRefund' id='"+refund.day+"'>停用</a></td>" +
                "<td class='td-action-"+refund.day+"-ban' style='display: none;'><a class='unbanRefund' id='"+refund.day+"'>启用</a></td>";
        }else{
            tableDom += "<td>禁用</td>" +
                "<td class='td-action-"+refund.day+"'  style='display: none;'><a class='modifyRefund' id='"+refund.day+"'>修改</a>|<a class='banRefund' id='"+refund.day+"'>停用</a></td>" +
                "<td class='td-action-"+refund.day+"-ban'><a class='unbanRefund' id='"+refund.day+"'>启用</a></td>";
        }
        tableDom += "<td class='td-action-"+refund.day+"-edit' style='display: none;'><a class='confirm-modify' id='"+refund.day+"'>确认</a>|<a class='cancel-modify' id='"+refund.day+"'>取消</a></td>" +
            "</tr>";
    });
    $('tbody').append(tableDom);
}

$(document).on('click','.modifyRefund',function (e) {
    refundDay = e.target.id;
    set(refundDay);
    $(tdPercentage).css('display','none');
    $(tdPercentageEdit).css('display','');
    $(tdAction).css('display','none');
    $(tdActionEdit).css('display','');
});

$(document).on('click','.banRefund',function (e) {
    refundDay = e.target.id;
    set(refundDay);
    console.log(e.target.dataset);
    var refund = searchRefund(refundDay);
    var r = confirm('你确定要禁用该策略吗？');
    if(r){
        refund.refundable = 0;
        postRequest(
            '/admin/refund/modify',
            refund,
            function (res) {
                if(res.success){
                    getRefundStrategy();
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

$(document).on('click','.unbanRefund',function (e) {
    refundDay = e.target.id;
    set(refundDay);
    var refund = searchRefund(refundDay);
    var r = confirm('你确定要启用该策略吗？');
    if(r){
        refund.refundable = 1;
        postRequest(
            '/admin/refund/modify',
            refund,
            function (res) {
                if(res.success){
                    getRefundStrategy();
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

function searchRefund(day){
    var result = {};
    refundAll.forEach(function (refund) {
        if(refund.day==day){
            result = refund;
        }
    });
    return result;
}

$(document).on('click','.confirm-modify',function (e) {
    refundDay = e.target.id;
    set(refundDay);
    var form = {
        day: $(tdDay).text(),
        percentage: $(percentageId).val(),
        refundable: 1
    };
    var r = confirm('你确定要修改该策略吗？');
    if(r){
        if(form.percentage < 0||form.percentage > 100){
            alert('退票百分比应介于0至100之间！')
        }else{
            postRequest(
                '/admin/refund/modify',
                form,
                function (res) {
                    if(res.success){
                        getRefundStrategy();
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
    refundDay = e.target.id;
    set(refundDay);
    $(tdPercentage).css('display','');
    $(tdPercentageEdit).css('display','none');
    $(tdAction).css('display','');
    $(tdActionEdit).css('display','none');
});

function set(refundDay) {
    percentageId = '#percentage_' + refundDay;
    tdDay = '.td-day-' + refundDay;
    tdPercentage = '.td-percentage-' + refundDay;
    tdPercentageEdit = '.td-percentage-' + refundDay + '-edit';
    tdAction = '.td-action-' + refundDay;
    tdActionEdit = '.td-action-' + refundDay + '-edit';
    tdActionBan = '.td-action-' + refundDay + '-ban';
}

function addRefund() {
    var tableDom = "";
    tableDom += "<tr>" +
        "<td class='td-day-add''><input class='form-control' type='number' min='1' id='addDay' style='display: inline-block;width: 100px;'></td>" +
        "<td class='td-percentage-add'><input class='form-control' type='number' min='0' max='100' id='addPercentage' style='display: inline-block;width: 100px;'></td>" +
        "<td></td>" +
        "<td class='td-action-add'><a class='confirm-add'>提交</a>|<a class='cancel-add'>取消</a></td>" +
        "</tr>";
    $('tbody').append(tableDom);
}

$(document).on('click','.confirm-add',function () {
    var form = {
        day: $('#addDay').val(),
        percentage: $('#addPercentage').val(),
        refundable: 1
    };
    var r = confirm('你确定要增加该策略吗？');
    if(r){
        if(form.day <= 0){
            alert('距开场时间应大于0！')
        }else if(form.percentage < 0||form.percentage > 100){
            alert('退票百分比应介于0至100之间！')
        }else{
            postRequest(
                '/admin/refund/add',
                form,
                function (res) {
                    if(res.success){
                        getRefundStrategy();
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
    getRefundStrategy();
});