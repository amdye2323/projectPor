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
            console.log(data);
            var list = data.list;
            var count = data.totalCount;
            console.log(list);
            $("#totalCost").text(count);
            if(data.msg.status==200){
                var li = $("#orderList");
                li.empty();
                var html = "";
                for(var i = 0;i<list.length;i++){
                    html += "<div class='col-6' style='cursor:pointer;' onclick='orderRemove("+list.orderId+")'>";
                    html += "<div style='width: 100%;'>";
                    html += "<div><label>단가</label><p>"+list.productStandard+"</p></div>";
                    html += "<div><label>수량</label><p>"+list.productCount+"</p></div>";
                    html += "<div><label>비용</label><p>"+list.productCost+"</p></div>";
                    html += "</div></div>";
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

    console.log(id);

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
           console.log(msg);
           if(msg.status===200){
               alert(msg.responseMessage+" 이 완료되었습니다.");
               basketSet();
           }else{
               alert("오류입니다 관리자에게 문의주세요.");
           }
       },
       error : function () {

       }
    });
}