/**
 * Created by linuslagerhjelm on 2017-02-28.
 */

// Copyright (c) 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

/**
 * Get the current URL.
 *
 * @param {function(string)} callback - called when the URL of the current tab
 *   is found.
 */

document.addEventListener('DOMContentLoaded', function() {
    var elem = document.getElementById("submit");
    if (elem) {
        elem.addEventListener("click", create);
    }
});

function create() {
    var firstName = capitalizeFirst(document.getElementById("namn").value);
    var lastName = capitalizeFirst(document.getElementById("efternamn").value);
    var office = document.getElementById("office").value.toUpperCase();
    var data = {
        "firstName": firstName,
        "lastName": lastName,
        "office": office
    };
    $.ajax({
        type: "POST",
        url: "/api/createUser",
        data: JSON.stringify(data),
        success: function (data) { console.log(data); },
        error: function (msg) {console.log(msg); }
    });
}

function capitalizeFirst(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}


