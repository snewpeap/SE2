$("#cancel").click(function () {
    window.location.href='/manage/movieAll';
});

$("#submit").click(function (){
    var form = formGet();
    addMovie(form);
});

function formGet() {
    var formData = {
        name:$('#name').val(),
        startDate:$('#startDate').val(),
        poster:$('#poster').val(),
        description:$('#description').val(),
        type:$('#type').val(),
        duration:$('#duration').val(),
        country:$('#country').val(),
        language:$('#language').val(),
        director:$('#director').val(),
        starring:$('#starring').val(),
        screenWriter:$('#screenWriter').val()
    };
    return formData;
}

function validateForm(form) {
    if(form.name===''){
        alert('电影名称不能为空！');
        return false;
    }else if(form.startDate===''){
        alert('上映日期不能为空！');
        return false;
    }else if(form.poster===''){
        alert('电影海报不能为空！');
        return false;
    }else if(form.duration < 0){
        alert('电影时长必须大于零！');
        return false;
    }else{
        return true;
    }
}

function addMovie(form) {
    if(validateForm(form)){
        postRequest(
            '/manage/movie/add',
            form,
            function (res) {
                if(res.success){
                    alert("添加电影成功！");
                    window.location.href = '/manage/movieDetail?movieId='+res.content.id;
                }else{
                    alert(res.message);
                }
            },
            function (res) {
                alert(res.message);
            }
        )
    }
}