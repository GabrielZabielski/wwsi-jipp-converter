let dropdown = $('select#converters');
dropdown.empty();
const url ='http://localhost:5050/converter';

$.getJSON(url, function (data) {
    $.each(data, function (i, item) {
        let option = document.createElement('Option');
        option.text = item;
        option.value = item;
        dropdown.add(option);
    })
});