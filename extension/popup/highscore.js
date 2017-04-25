
document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighscores);
    }
});

getHighscores();

var data = { "startTime": 1490208166633 };
var userInformation;

function getHighscores(){

    $.ajax({
        type: "GET",
        url: "http://localhost:4567/api/getHighScores",
        data: "startTime=1490208166633",
        success: insertScores,
        error: function (msg) { console.log(msg); }
     });

};

function insertScores(data) {
        var scores = JSON.parse(data);
        var list = document.getElementById("high-scores");
        list.innerHTML = "";

        scores.sort(function (s1, s2) {
            return s2.value - s1.value;
        });
        scores.forEach(function (score) {
            tr = document.createElement("tr");

            td1 = document.createElement("td");
            td1.innerText = score.value;

            td2 = document.createElement("td");
            td2.innerText = score.user.firstName;
            td2.innerHTML += "&nbsp;"
            td2.innerText += score.user.lastName;

            td3 = document.createElement("td");
            td3.innerText = score.user.office;

            tr.appendChild(td1)
            tr.appendChild(td2)
            tr.appendChild(td3)
            list.appendChild(tr);

        });

}