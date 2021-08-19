$(function () {
   console.log("동작시작");
});

function login(){
   var accountId = $("#accountId").val();
   var accountPass = $("#accountPass").val();

   $.ajax({
      url : "/api/authenticate",
      method : "POST",
      contentType: "application/json",
      data : JSON.stringify({
         "username" : accountId,
         "password" : accountPass
      }),
      dataType : "json",
      success : function (data) {
         var dbo = data;
         localStorage.setItem("jwt",dbo.token);
         localStorage.setItem("username",dbo.user);
         location.href = "/order";
      },
      error : function (e) {
         alert(e+"에러입니다");
      }
   });
};
