
document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("knapp2");
    if (elem) {
        elem.addEventListener("click", getHighscores(data));
    }
});

data = {
        "timestamp": JSON.stringify(1490208166633),

    }


function getHighscores() {

     $.ajax({
     type:"POST",
     url: "/api/getHighscores",
     data:data,
     success: function (response){
         var arr = $.parseJSON(response);

                     }
                 });
     console.log('response');

 }