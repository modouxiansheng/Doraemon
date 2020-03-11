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

function CheckStatus(value) {
    if (value == "onlinePay") {
        document.getElementById("onlinePay").checked = true;
        document.getElementById("underlinePay").checked = false;
    }
    else if (value == "underlinePay") {
        document.getElementById("onlinePay").checked = false;
        document.getElementById("underlinePay").checked = true;
    }
}


//根据后台生成的checkout_session_id去支付
var CHECKOUT_SESSION_ID = "cs_test_nkCz23gLtfqL0SFcmOa7SUukhsZcJTeKG4boi5NJJBUPuVo6gubkbgN1";

var stripe = Stripe('pk_test_IvnAwbTFQPE2MZlwzc1r2dD100MJmWD2Xi');


function stripePay(){
    alert(CHECKOUT_SESSION_ID)
    stripe.redirectToCheckout({
        sessionId: CHECKOUT_SESSION_ID
    }).then(function (result) {
        console.log(result);
    });
}