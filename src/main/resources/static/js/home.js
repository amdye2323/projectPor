$(function () {
    let username = localStorage.getItem("username");
    $("#userId").text(username);
    if(username!=null){
        $("#loginBox").hide();
        $("#signBtn").hide();
        $("#loginBtn").hide();
        $("#logoutBtn").show();
    }else{
        $("#loginBox").show();
        $("#signBtn").show();
        $("#loginBtn").show();
        $("#logoutBtn").hide();
    }
});

function doPage(url) {
    var token = localStorage.getItem("jwt");
    const headers =  {
        "Authorization": "Bearer " + token,
    };

    var username = localStorage.getItem("username");

    $.ajax({
        url : "/api/auth",
        data : {

        },
        headers : {
            "Authorization": "Bearer " + token,
        },
        contentType: 'application/json',
        method:"POST",
        success:function (msg) {
            if(msg.status==200){
                location.href = url;
            }else{
                alert("서버 오류입니다.관리자에게 문의해주세요.");
            }
        },
        error:function (e) {
            alert("jwt 토큰 오류입니다.관리자에게 문의해주세요.");
        }
    });
};

function logOut() {
    var token = localStorage.getItem("jwt");
    $.ajax({
        url : "/api/auth",
        data : {
            "username" : localStorage.getItem("username")
        },
        headers : {
            "Authorization": "Bearer " + token,
        },
        contentType: 'application/json',
        method : "POST",
        dataType : "json",
        success:function () {
            localStorage.removeItem("jwt");
            localStorage.removeItem("username");
            location.href = "/";
        },
        error:function (e) {
            alert(e);
        }
    });
};