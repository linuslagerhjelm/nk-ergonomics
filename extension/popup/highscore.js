
document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighscores);
    }
});

function getHighscores() {
    alert("window is loaded ");
 }

/*$(document).ready(function(){
    $(window).load(function() {

     alert("window is loaded");
    });
)}*/