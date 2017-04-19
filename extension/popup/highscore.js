
document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighscores());
    }
});

/*console.log(response);*/

var data = { "startTime": 1490208166633 };
var userInformation;

/*$("#knapp2").click(function() {*/

function getHighscores(){

    $.ajax({
        type: "GET",
        url: "/api/getHighScores",
        data: "startTime=1490208166633",
        success: function (data) { console.log(data); },
        error: function (msg) { console.log(msg); }
     });

    /*$.ajax({
        type:"GET",
        url: "/api/getHighScores",
        data: JSON.stringify(data);
        success: function (userInfo)
        {
            userInfomation = userInfo;
            console.log(userInformation);
        }
         error: function (response) {

          }
});*/
};