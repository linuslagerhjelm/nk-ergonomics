
/*document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighScores());
    }
});*/

/*console.log(response);*/

var data = { startTime: 1490208166633 };
var userInfomation;

$("#knapp2").click(function() {
    $.ajax({
        type:"GET",
        url: "/api/getHighScores",
        data: JSON.stringify(data),
        success: function (userInfo)
        {
            userInfomation = userInfo;
        }
    });
});