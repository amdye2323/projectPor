$(function () {
    basketSet();
});

function pushBasket(product) {
    var productId = $(product).find("input[name='productId']").val();
    var productName = $(product).find("input[name='productName']").val();
    var productImage = $(product).find("input[name='productImage']").val();
    var productCost = $(product).find("input[name='productCost']").val();
    var pro = new Object();

    pro.productId = productId;
    pro.productName = productName;
    pro.productImage = productImage;
    pro.productCost = productCost;
    pro.productCount = 1;

    var productList = JSON.parse(localStorage.getItem("ProductArray"));

    if(productList==null){
        var array = new Array();
        array.push(pro);
        localStorage.setItem("ProductArray",JSON.stringify(array));
        basketSet();
    }else{
        var count = 0;
        productList.map(
            function (o) {
                if(o.productId==productId){
                    o.productCount = parseInt(o.productCount) + 1;
                    count++;
                }
            }
        );
        if(!count>=1){//중복이 아닌 경우
            productList.push(pro);
            localStorage.setItem("ProductArray",JSON.stringify(productList));
            basketSet();
        }else{
            localStorage.setItem("ProductArray",JSON.stringify(productList));
            basketSet();
        }
    }
};

function basketClear() {
    var jwt = localStorage.getItem("jwt");
    var username = localStorage.getItem("username");
    localStorage.clear();
    localStorage.setItem("jwt",jwt);
    localStorage.setItem("username",username);
    basketSet();
}

function basketSet() {
    var productList = JSON.parse(localStorage.getItem("ProductArray"));

    var basketList = $("#basketList");
    basketList.empty();

    if(productList==null){

    }else{
        productList.map(
            function (o) {
                var html = "";

                html += '<li style="width: 350px;" onclick="basketDel(this);">';
                html += '<div className="card" style="width: 100%;">';
                html += '<img style="width: 286px;height: 180px;" className="card-img-top" src="'+o.productImage+'" alt="Card image cap">';
                html += '<div className="card-body">';
                html += '<h5 className="card-title" >'+o.productName+'</h5>';
                html += "<div class='form-group row'>";
                html += "<label class='col-sm-2 col-form-label'>단가</label>";
                html += "<div class='col-sm-10'>";
                html += '<p class="form-control">'+o.productCost+'</p>';
                html += "</div></div>";
                html += "<div class='form-group row'>";
                html += "<label class='col-sm-2 col-form-label'>갯수</label>";
                html += "<div class='col-sm-10'>";
                html += '<p class="form-control">'+o.productCount+'</p>';
                html += "</div></div>";
                html += "<div class='form-group row'>";
                html += "<label class='col-sm-2 col-form-label'>갯수</label>";
                html += "<div class='col-sm-10'>";
                html += '<p class="form-control">'+(parseInt(o.productCount) * parseInt(o.productCost))+'</p>';
                html += "</div></div>";
                html += '<input type="hidden" value="'+o.productId+'"  name="productId"/>';
                html += '<input type="hidden" value="'+o.productName+'" name="productName"/>';
                html += '<input type="hidden" value="'+o.productCost+'"  name="productCost"/>';
                html += '<input type="hidden" value="'+o.productImage+'"  name="productImage"/>';
                html += '<input type="hidden" value="'+o.productCount+'"  name="productCount"/>';
                html += '<input type="hidden" value="'+(parseInt(o.productCount) * parseInt(o.productCost))+'"  name="productTotalCost"/>';
                html += '</div></div></li>';
                if(o.productCount>=1){
                    basketList.append(html);
                }

            }
        );
    }
}

function basketDel(product) {
    var productId = $(product).find("input[name='productId']").val();
    var productList = JSON.parse(localStorage.getItem("ProductArray"));

    productList.map(
        function (o) {
            if(o.productId==productId){
                o.productCount = parseInt(o.productCount) -1;
            }
        }
    );
    localStorage.setItem("ProductArray",JSON.stringify(productList));
    basketSet();
}

function basketOrder() {
    var productList = JSON.parse(localStorage.getItem("ProductArray"));
    var array = new Array();
    productList.map(
        function (o) {
            if(parseInt(o.productCount)>=1){
                var obj = new Object();
                obj.username = localStorage.getItem("username");
                obj.productId = o.productId;
                obj.productName = o.productName;
                obj.productImage = o.productImage;
                obj.productCost = o.productCost;
                obj.productCount = o.productCount;
                obj.productTotalCount = parseInt(o.productCost.toString()) * parseInt(o.productCount.toString());
                array.push(obj);
            }

        }
    );
    var token = localStorage.getItem("jwt");

    $.ajax({
        url : "/order/addOrder",
        contentType: 'application/json',
        data : JSON.stringify(array),
        headers: { "Authorization": 'Bearer ' + token },
        dataType : "json",
        method : "POST",
        success:function (msg) {
            if(msg.status===200){
                alert(msg.responseMessage+"이 완료되었습니다.");
                localStorage.removeItem("ProductArray");
                basketSet();
            }else{
                alert("서버 오류입니다.관리자에게 문의해주세요.");
            }
        },
        error:function (e) {
            alert(e+" 관련 에러입니다. 관리자에게 문의해주세요.");
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