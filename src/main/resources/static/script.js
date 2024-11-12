var filterName = document.getElementById("filterName")
var filterDesc = document.getElementById("filterDesc")

var query = window.location.search
var params = new URLSearchParams(query)

var nameParam = params.get("name")
var descParam = params.get("desc")

if (nameParam == null || nameParam == "null") nameParam = ""
if (descParam == null || descParam == "null") descParam = ""

filterName.value = nameParam
filterDesc.value = descParam

function filter() {
    window.location.href = "/?name=" + filterName.value + "&desc=" + filterDesc.value
}

// refresh every minute...
setTimeout(function() {
    window.location.reload(1);
}, 60000);