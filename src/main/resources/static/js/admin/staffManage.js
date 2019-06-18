var role;
var roles = [];
var staffAll;
var staffId;
var tdName,tdNameEdit,tdRole,tdRoleEdit,tdAction,tdActionEdit;
var nameId,roleId;


$(document).ready(function () {
    getStaff();
});

function getStaff(){
    role = $('#roleSelect').children('option:selected').val();
    if(role==='all'){
        roles = [];
        roles.push('manager');
        roles.push('staff')
    }else{
        roles = [];
        roles.push(role);
    }

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
    $('tbody').empty();
    var tableDom = "";
    staffAll.forEach(function (staff) {
        var roleChinese = staff.role==='root'?'总经理':(staff.role==='manager'?'经理':'员工');
        tableDom += "<tr>" +
            "<td class='td-id-"+staff.id+"'>"+staff.id+"</td>" +
            "<td class='td-name-"+staff.id+"'>"+staff.name+"</td>" +
            "<td class='td-name-"+staff.id+"-edit' style='display: none;'><input class='form-control' type='text' id='name_"+staff.id+"' value='"+staff.name+"' style='display: inline-block;width: 100px;'></td> " +
            "<td class=''>******</td>" +
            "<td class='td-role-"+staff.id+"'>"+roleChinese+"</td>" +
            "<td class='td-role-"+staff.id+"-edit' style='display: none;'><select class='form-control' id='role_"+staff.id+"' style='display: inline-block;width: 100px;'>" +
            "<option value='"+(staff.role==='root'?'root':(staff.role==='manager'?'manager':'staff'))+"'>"+(staff.role==='root'?'总经理':(staff.role==='manager'?'经理':'员工'))+"</option> " +
            "<option value='"+(staff.role==='root'?'manager':'root')+"'>"+(staff.role==='root'?'经理':'总经理')+"</option> " +
            "<option value='"+(staff.role==='root'?'staff':(staff.role==='manager'?'staff':'manager'))+"'>"+(staff.role==='root'?'员工':(staff.role==='manager'?'员工':'经理'))+"</option> " +
            "</select></td> " +
            "<td class='td-action-"+staff.id+"'><a class='modifyStaff' id='"+staff.id+"'>修改</a>|<a class='deleteStaff' id='"+staff.id+"'>删除</a></td>" +
            "<td class='td-action-"+staff.id+"-edit' style='display: none;'><a class='confirm-modify' id='"+staff.id+"'>确认</a>|<a class='cancel-modify' id='"+staff.id+"'>取消</a></td>" +
            "</tr>";
    });
    $('tbody').append(tableDom);
}

$('#roleSelect').change(function () {
    getStaff();
});

$(document).on('click','.modifyStaff',function (e) {
    staffId = e.target.id;
    set(staffId);
    $(tdName).css('display','none');
    $(tdNameEdit).css('display','');
    $(tdRole).css('display','none');
    $(tdRoleEdit).css('display','');
    $(tdAction).css('display','none');
    $(tdActionEdit).css('display','');
});

$(document).on('click','.deleteStaff',function (e) {
    staffId = e.target.id;
    set(staffId);
    var r = confirm('你确定要删除该员工吗？');
    if(r){
        postRequest(
            '/root/removeStaff?staffId='+ staffId,
            {},
            function (res) {
                if(res.success){
                    getStaff();
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
    staffId = e.target.id;
    set(staffId);
    var form = {
        id: staffId,
        name: $(nameId).val(),
        password: '',
        role: $(roleId).children('option:selected').val()
    };
    var r = confirm('你确定要修改该员工信息吗？');
    if(r){
        postRequest(
            '/root/changeRole',
            form,
            function (res) {
                if(res.success){
                    getStaff();
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

$(document).on('click','.cancel-modify',function (e) {
    staffId = e.target.id;
    set(staffId);
    $(tdName).css('display','');
    $(tdNameEdit).css('display','none');
    $(tdRole).css('display','');
    $(tdRoleEdit).css('display','none');
    $(tdAction).css('display','');
    $(tdActionEdit).css('display','none');
});

function set(staffId) {
    nameId = '#name_' + staffId;
    roleId = '#role_' + staffId;
    tdName = '.td-name-' + staffId;
    tdNameEdit = '.td-name-' + staffId + '-edit';
    tdRole = '.td-role-' + staffId;
    tdRoleEdit = '.td-role-' + staffId + '-edit';
    tdAction = '.td-action-' + staffId;
    tdActionEdit = '.td-action-' + staffId + '-edit';
}

$('#staff-form-btn').click(function () {
    var form = getAddStaffForm();
    if(form.name===''){
        alert('名称不能为空！');
    }else if(form.password===''){
        alert('密码不能为空');
    }else if(form.password !== $('#staff-password-twice').val()){
        alert('两次密码输入不相同！')
    }else{
        if(form.role==='manager'){
            postRequest(
                '/root/addManager',
                form,
                function (res) {
                    if(res.success){
                        $('#staffModal').modal('hide');
                        getStaff();
                    }else{
                        alert(res.message);
                    }
                },
                function (res) {
                    alert(res.message);
                }
            );
        }else{
            postRequest(
                '/root/addStaff',
                form,
                function (res) {
                    if(res.success){
                        $('#staffModal').modal('hide');
                        getStaff();
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

function getAddStaffForm(){
    return {
        name: $('#staff-name-input').val(),
        password: $('#staff-password-input').val(),
        role: $('#staff-role-input').children('option:selected').val()
    }
}