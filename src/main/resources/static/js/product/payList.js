$(function () {
   payInquiry(); 
});


function payInquiry() {
    var username = localStorage.getItem("username");
    var token = localStorage.getItem("jwt");

    $.ajax({
        url : "order/payList",
        method : "POST",
        dataType : "json",
        data : {
            "username" : username
        },
        headers: { "Authorization": 'Bearer ' + token },
        success:function (data) {
            var list = data.list;
            var count = data.totalCount;
            $("#totalCost").text(count);
            if(data.msg.status==200){
                var li = $("#payList");
                li.empty();
                var html = "";
                for(var i = 0;i<list.length;i++){
                    html += "<tr>";
                    html += "<td>"+list[i].username+"</td>";
                    html += "<td>"+list[i].cost+"</td>";
                    html += "<td>"+list[i].paygubun+"</td>";
                    html += "<td>"+list[i].createDate+"</td>";
                    html += "</tr>";
                }
                li.append(html);
            }
        },
        error : function () {

        }
    });

}

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