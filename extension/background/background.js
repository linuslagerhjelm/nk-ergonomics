/**
 * Created by linuslagerhjelm on 2017-02-28.
 * Recreated by Cilinksi
 */

chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
    showNotif(changeInfo, tab);
});

function showNotif(changeInfo, tab) {
    chrome.storage.sync.get("urls", function (items) {
        items.urls.forEach(function (url) {
            var rx = new RegExp(url, 'g');
            if (rx.test(tab.url)) {
                console.log(tab.url);
                chrome.notifications.create('', {
                    type: 'basic',
                    iconUrl: '../resources/img/NKlogoNoText.png',
                    title: 'Time to work out',
                    message: 'Game is now available'
                }, function(notificationId) {});
            }
        });
    });
}
