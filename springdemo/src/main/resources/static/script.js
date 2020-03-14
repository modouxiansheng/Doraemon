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
function stripeRefund() {


    $.ajax({
        type: 'GET',
        url: "/refund?returnId="+$("#refundId").val(),
        success: function(response){
            alert(response);
        },
        error:function(response){
            alert("失败");
            console.log(response);
        }
    });
}

var stripe = Stripe('pk_test_IvnAwbTFQPE2MZlwzc1r2dD100MJmWD2Xi');

function stripePay(){
    $.ajax({
        url: '/pay',
        type: 'GET',
        dataType:"json",
        processData: false,
        contentType: false,
        success:(function(data) {
            stripe.redirectToCheckout({
                sessionId: data.sessionId
            }).then(function (result) {
                console.log(result);
            });
        }),
        error:(function(res) {
            alert("失败");
        })
    });

}

