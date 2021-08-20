$(function () {
    pageInquiry();
});

function pageInquiry() {
    var username = localStorage.getItem("username");
    var token = localStorage.getItem("jwt");

    $.ajax({
        url : "order/orderList",
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
                var li = $("#orderList");
                li.empty();
                var html = "";
                for(var i = 0;i<list.length;i++){
                    html += "<tr>";
                    html += "<td>"+list[i].username+"</td>";
                    html += "<td>"+list[i].productName+"</td>";
                    html += "<td>"+list[i].productStandard+"</td>";
                    html += "<td>"+list[i].productCount+"</td>";
                    html += "<td>"+list[i].productCost+"</td>";
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

function orderRemove(orderId) {
    var id = parseInt(orderId);
    var username = localStorage.getItem("username");
    var token = localStorage.getItem("jwt");

    $.ajax({
       url : "order/removeOrder",
       data : {
           "username" : username,
           "orderId" : id
       },
       headers: { "Authorization": 'Bearer ' + token },
       dataType : "json",
       method : "POST",
       success:function (msg) {
           if(msg.status===200){
               alert(msg.responseMessage+" 이 완료되었습니다.");
               pageInquiry();
           }else{
               alert("오류입니다 관리자에게 문의주세요.");
           }
       },
       error : function () {

       }
    });
}

function payToOrder() {
    var payGubun = $("#payGubun option:selected").val();
    if(payGubun==""){
        alert("결제방법을 선택해주세요.");
        return ;
    }
    var username = localStorage.getItem("username");
    var token = localStorage.getItem("jwt");

    $.ajax({
        url : "order/payToOrder",
        data : {
            "username" : username,
            "payGubun" : payGubun
        },
        headers: { "Authorization": 'Bearer ' + token },
        dataType : "json",
        method : "POST",
        success:function (msg) {
            console.log(msg);
            if(msg.status===200){
                alert(msg.responseMessage+" 이 완료되었습니다.");
                pageInquiry();
            }else{
                alert("오류입니다 관리자에게 문의주세요.");
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