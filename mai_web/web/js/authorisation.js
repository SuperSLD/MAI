function init() {
    if (localStorage.getItem("key") != null) {
        document.getElementById("key").value = localStorage.getItem("key")
    }

    document.getElementById("form_key").onsubmit = function (ev) {
        localStorage.setItem("key", document.getElementById("key").value)
    }
}
