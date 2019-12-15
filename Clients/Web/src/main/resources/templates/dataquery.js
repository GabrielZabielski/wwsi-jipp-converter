$(document).ready( function () {
    $('#history').DataTable({
        "sAjaxSource": "/statistic",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        columns: [
            { "mData": "dataId"},
            { "mData": "converter" },
            { "mData": "date" },
            { "mData": "unitFrom" },
            { "mData": "unitTo" },
            { "mData": "input" },
        ]
    })
});