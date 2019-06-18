$(document).ready(function () {
    getVips();
});

function getVips() {
    getRequest(
        '/admin/vip/get',
        function (res) {
            if(res.success){

            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);}
    )
}