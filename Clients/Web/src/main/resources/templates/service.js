let dropdown = $("Select#converters");
dropdown.empty();
const url ='http://localhost:5050/converter';

// dropdown.append('<option selected="true" disabled>Choose State/Province</option>');
// dropdown.prop('selectedIndex', 0);

$.getJSON(url, function (data) {
    $.each(data, function (i, item) {
        let option = document.createElement('Option');
        option.text = item;
        option.value = item;
        dropdown.append($('<option></option>').attr('value', item).text(item));
    });
    dropdown.prop('selectedIndex', 0);
    selectedOption();
});


function selectedOption() {
    let drop = document.getElementById('converters');
    var type = drop.value;
    let from = $("Select#from");
    let to = $("Select#in-to");
    from.empty();
    to.empty();
    const url ='http://localhost:5050/converter/'+type;

    $.getJSON(url, function (data) {
        $.each(data, function (i, item) {
            let option = document.createElement('Option');
            option.text = item;
            option.value = item;
            from.append($('<option></option>').attr('value', item).text(item));
            to.append($('<option></option>').attr('value', item).text(item));
        });
        from.prop('selectedIndex', -1);
        to.prop('selectedIndex', -1);
    });
}

function convert() {
    let info = document.getElementById("info");
    info.innerHTML = "";
    let drop = document.getElementById('converters');
    var type = drop.value;

    var from = document.getElementById("from").value;
    var into = document.getElementById("in-to").value;
    var input = parseFloat(document.getElementById("input").value);

    if (from.toString() === into.toString() || isNaN(input) ){
        info.innerHTML = "Validation error";
    }
    else {
        const url = "http://localhost:5050/converter/"+type+"/value?"+"from="+from+"&to="+into+"&input="+input.toString();
        $.getJSON(url, function (data) {
            $.each(data, function (key, value) {
                console.log(key, value);
                document.getElementById("output").innerHTML = value.toString();
            })

        })
    }
}