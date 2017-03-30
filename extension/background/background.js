/**
 * Created by linuslagerhjelm on 2017-02-28.
 * Recreated by Cilinksi
 */

document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighscores);
    }
});

var getHighscores = function() {
    alert("window is loaded ");
 }

/*$(document).ready(function(){
    $(window).load(function() {

     alert("window is loaded");
    });
)}*/