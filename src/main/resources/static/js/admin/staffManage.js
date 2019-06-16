var role;
var roles = [];
var staffAll;

$(document).ready(function () {
    role = $('#roleSelect').children('option:selected').val();
    if(role==='all'){
        roles.push('manager');
        roles.push('staff')
    }else{
        roles.push(role);
    }

    getStaff();
});

function getStaff(){
    getRequest(
        '/root/getStaff?query='+roles,
        function (res) {
            if(res.success){
                staffAll = res.content;
                renderStaff()
            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}

function renderStaff() {
    $('tbody').clean();
    var tableDom = "";
    staffAll.forEach(function (staff) {
        tableDom += "<tr>" +
            "<td class='td-id-"+staff.id+"'>"+staff.id+"</td>" +
            "<td class='td-name-"+staff.id+"'>"+staff.name+"</td>" +
            "<td class='td-name-"+staff.id+"-edit' style='display: none;'><input class='staffName' type='text' id='name_"+staff.id+"' placeholder='"+staff.name+"'></td> " +
            "<td class=''>******</td>" +
            "<td class='td-role-"+staff.id+"'>"+staff.role+"</td>" +
            "<td class='td-role-"+staff.id+"-edit' style='display: none;'><select class='staffRole' id='role_"+staff.id+"'>" +
            "<option value='"+(staff.role==='manager'?'manager':'staff')+"'>"+(staff.role==='manager'?'经理':'员工')+"</option> " +
            "<option value='"+(staff.role==='manager'?'staff':'manager')+"'>"+(staff.role==='manager'?'员工':'经理')+"</option> " +
            "</select></td> " +
            "<td class='td-action-"+staff.id+"'><a class='modifyStaff' id='"+staff.id+"'>修改</a>|<a class='deleteStaff' id='"+staff.id+"'>删除</a></td>" +
            "<td class='td-action-"+staff.id+"-edit' style='display: none;'><a class='confirm-modify id='"+staff.id+"'>确认</a>|<a class='cancel-modify id='"+staff.id+"'>取消</a></td>" +
            "</tr>";
    });
    $('tbody').append(tableDom);
}