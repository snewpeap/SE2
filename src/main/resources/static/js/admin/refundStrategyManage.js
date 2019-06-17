$(document).ready(function () {
    getRefundStrategy()
});

function getRefundStrategy() {
    getRequest(
        '/admin/refund/get',
        function (res) {
            if(res.success){

            }else{
                alert(res.message);
            }
        },
        function (res) {
            alert(res.message);
        }
    )
}