/**
 * Инициализация страницы с новостями.
 */
function init () {
    console.log("initialisation");
    var button = document.getElementById("send_button");
    button.onclick = function (ev) {
        console.log("send request");
        var url = document.contextPath + "/news?act=add&" +
            "title="+document.getElementById("title_new_note").value +
            "&text="+document.getElementById("text_new").value;

        var http = new XMLHttpRequest();
        http.open("POST", url, true);
        http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        http.onload = function () {
            if (this.readyState != 4) return;
            if (this.status != 200) {
                console.log( 'ошибка: ' + (this.status ? this.statusText : 'запрос не удался') );
                return;
            }

            console.log(this.responseText);
            var object = JSON.parse(this.responseText);
            console.log(object);
            alert(object.err_text + "\n\nError code: " + object.err_code);
            if (object.err_code = "0") {
                var newsContainer = document.getElementById("news_container");
                newsContainer.innerHTML = object.html + newsContainer.innerHTML
                document.getElementById("title_new_note").value = "";
                document.getElementById("text_new").value = "";
            }
        };
        http.send();
    };
};