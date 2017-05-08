/**
 * Created by linuslagerhjelm on 2017-04-20.
 */

var urls = [];

chrome.storage.sync.get("urls", function (items) {
    urls = items.urls || [];

    var urlList = document.getElementById("forbiddenUrls");
    urls.forEach(function (url) {
        addListItem(urlList, url);
    });

    listenOnAdd();
});

chrome.runtime.onMessage.addListener(
    function(request, sender, sendResponse) {
        if (request.msg === "TAB_CHANGE") {
            console.log(request.msg)
        }
    }
);

function listenOnAdd() {
    document.getElementById("addUrl").addEventListener("click", function (e) {
        var input = document.getElementById("inputField");
        var urlList = document.getElementById("forbiddenUrls");

        if (!input.value) return;
        urls.push(input.value);
        chrome.storage.sync.set({"urls": urls}, function() {
            addListItem(urlList, input.value);
        });
    });
}

function addListItem(parent, text) {
    var li = document.createElement("li");
    var span = document.createElement("span");
    span.innerText = "x";

    li.addEventListener("click", removeUrl);
    span.classList.add("delete");

    li.appendChild(span);
    li.innerHTML += " " + text;
    parent.appendChild(li);
}

function removeUrl(e) {
    if (!e.target.classList.contains("delete")) return;

}