var halls = [];

$(document).ready(function() {
    getCinemaHalls();
});

function getCinemaHalls() {
    getRequest(
        '/manage/hall/get',
        function (res) {
            halls = res.content;
            renderHall();
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function renderHall(){
    $('#hall-card').empty();
    var hallDomStr = "";
    halls.forEach(function (hall) {
        var seat = "";
        for(var i =0;i<hall.row;i++){
            var temp = "";
            for(var j =0;j<hall.column;j++){
                temp+="<div class='cinema-hall-seat'></div>";
            }
            seat+= "<div>"+temp+"</div>";
        }
        var hallDom =
            "<div class='cinema-hall'>" +
                "<div class='cinema-hall-card'>" +
                    "<div>" +
                        "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                        "<span class='cinema-hall-size'>"+ hall.row +'*'+ hall.column +"</span>";
        if(hall.imax){
            hallDom += "<span class='hall-label label-success'>IMAX</span>";
        }
        if(hall.is3d){
            hallDom += "<span class='hall-label label-info'>3 D</span>";
        }
        hallDom += "</div>" +
                    "<div><a class='cinema-hall-modify' data-hall='" + JSON.stringify(hall) + "' id='" + hall.id + "'>修改</a></div>" +
                "</div>" +
                "<div class=\"clearfix\"></div>" +
                "<div class='cinema-seat'>" + seat + "</div>" +
            "</div>";
        hallDomStr+=hallDom;
    });
    $('#hall-card').append(hallDomStr);
}

$('#hall-form-btn').click(function () {
    var form = getHallForm();
    if(formCheck(form)){
        postRequest(
            '/admin/hall/add',
            form,
            function (res) {
                if(res.success){
                    $("#hallModal").modal('hide');
                    getCinemaHalls();
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

function getHallForm() {
    return {
        name: $('#hall-name-input').val(),
        row: $('#hall-row-input').val(),
        column: $('#hall-column-input').val(),
        size: $('#hall-size-input').children('option:selected').val(),
        isImax: $('#hall-imax-input').children('option:selected').val(),
        is3d: $('#hall-3D-input').children('option:selected').val()
    }
}

function formCheck(form) {
    if(form.name===''){
        alert('影厅名称不能为空！');
        return false;
    }else if(form.row <= 0){
        alert('影厅行数应大于零！');
        return false;
    }else if(form.column <= 0){
        alert('影厅列数应大于零！');
        return false;
    }else{
        return true;
    }
}

$(document).on('click','.cinema-hall-modify',function (e) {
    var hall = JSON.parse(e.target.dataset.hall);
    var hallSize = hall.size==='大'?0:hall.size==='中'?1:2;
    console.log(hallSize);
    $('#hall-edit-name-input').val(hall.name);
    $('#hall-edit-row-input').val(hall.row);
    $('#hall-edit-column-input').val(hall.column);
    $("#hall-edit-size-input").children('option[value='+hallSize+']').attr('selected',true);
    $("#hall-edit-imax-input").children('option[value='+hall.imax+']').attr('selected',true);
    $("#hall-edit-3D-input").children('option[value='+hall.is3d+']').attr('selected',true);
    $('#editHallModal').modal('show');
    $('#editHallModal')[0].dataset.hallId = hall.id;
});

$('#hall-edit-form-btn').click(function () {
    var form = getHallEditForm();
    if(formCheck(form)){
        postRequest(
            '/admin/hall/modify?hallId=' + $('#editHallModal')[0].dataset.hallId,
            form,
            function (res) {
                if(res.success){
                    $("#editHallModal").modal('hide');
                    getCinemaHalls();
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

function getHallEditForm() {
    return {
        name: $('#hall-edit-name-input').val(),
        row: $('#hall-edit-row-input').val(),
        column: $('#hall-edit-column-input').val(),
        size: $('#hall-edit-size-input').children('option:selected').val()==='0'?'大':$('#hall-edit-size-input').children('option:selected').val()==='1'?'中':'小',
        isImax: $('#hall-edit-imax-input').children('option:selected').val(),
        is3d: $('#hall-edit-3D-input').children('option:selected').val()
    }
}