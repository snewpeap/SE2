$(document).ready(function () {

    $("#login-btn").click(function () {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        login(
            '/login',
            formData,
            function (date,status,xhr) {
                // if (res.success) {
                // var redirect = xhr.getResponseHeader("Location");
                // if (redirect){
                //     window.location.href = redirect;
                // }
                var role = getCookie('role');
                if (role === "audience") {
                    sessionStorage.setItem('role', 'audience');
                } else {
                    sessionStorage.setItem('role', 'staff');
                }
                window.location.href = "/index"
                // } else {
                //     alert(res.message);
                // }
            },
            function (error) {
                alert(error);
            });
    });

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }
});