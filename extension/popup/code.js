/**
 * Created by linuslagerhjelm on 2017-05-10.
 */
chrome.storage.sync.get("user", function (items) {
    var user = items.user || {};
    var code = document.getElementById("code");
    code.innerHTML = user.id;
});