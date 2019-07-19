function readURLOne(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imageOnePreview').css('background-image', 'url('+e.target.result +')');
            $('#imageOnePreview').hide();
            $('#imageOnePreview').fadeIn(650);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#imageOne").change(function() {
    readURLOne(this);
});

function readURLTwo(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imageTwoPreview').css('background-image', 'url('+e.target.result +')');
            $('#imageTwoPreview').hide();
            $('#imageTwoPreview').fadeIn(650);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#imageUploadTwo").change(function() {
    readURLTwo(this);
});


function getUpload(){
    //获取form表单中所有属性  key为name值
    var formData = new FormData($("#picForm")[0]);
    $.ajax({
        url: '/pic',
        type: 'POST',
        dataType:"json",
        data: formData,
        processData: false,
        contentType: false,
        success:(function(data) {
            window.confirm(data.message);
            window.location.reload();
        }),
        error:(function(res) {
            alert("失败");
        })
    });
}