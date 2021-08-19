function signUp() {
    var username = $("#username").val();
    var password = $("#password").val();
    var nickname = $("#nickname").val();

    $.ajax({
        url : "/api/signup",
        data : JSON.stringify({
            "username" : username,
            "password" : password,
            "nickname" : nickname
        }),
        contentType: 'application/json',
        dataType : "json",
        method : "POST",
        success : function (data) {
            alert("가입되었습니다.");
            location.href = "/";
        },
        error : function (e) {
            alert("이미 가입되어 있는 유저입니다.");
        }
    })
}