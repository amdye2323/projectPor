$(function () {
    let username = localStorage.getItem("username");
    $("#userId").text(username);
});

function doPageHome(url) {
    var token = localStorage.getItem("jwt");
    const headers =  {
        "Authorization": "Bearer " + token,
    };
    const init = {
        "method" : "GET",
        "headers" : headers
    }
    fetch(url,init);
}
