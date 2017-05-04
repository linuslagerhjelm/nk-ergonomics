/**
 * Created by linuslagerhjelm on 2017-02-28.
 * Recreated by Cilinksi
 */

 console.log("hej2");

data = {
                   "firstName": "Linus",
                   "lastName": "Lagerhjelm",
                   "office": "LA"
               }

/*$.ajax({
    type: "POST",
    url: "http://localhost/api/createUser",
    data: JSON.stringify(data),
    success: function (data) { console.log(data) },
});*/

var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
       // Action to be performed when the document is read;
       console.log(this.responseText)
    }
};
xhttp.open("GET", "/api/getUsers?office=STOCKHOLM", true);
xhttp.send();


