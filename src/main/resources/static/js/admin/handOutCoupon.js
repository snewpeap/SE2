var vips;

$(document).ready(function () {
    getVips();
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
    // $('tbody').empty();
    var tableDom = "";
    vips.forEach(function (vip) {
        tableDom += "<tr class='even pointer'>" +
            "<td class='a-center'><input type='checkbox' class='flat' name='table_records'></td>" +
            "<td>"+vip.vipcardID+"</td>" +
            "<td>"+vip.user.name+"</td>" +
            "<td>"+vip.consumption+"</td>" +
            "</tr>";
    });
    $('tbody').append(tableDom);
}