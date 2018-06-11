$(function () {

    $('#Login').unbind('click').on('click',function () {

        $('.dpForm').ajaxSubmit(function(result) {

            var result = JSON.parse(result);
            if (result.result == "SUCCESS") {
                window.location.href = 'admin/album.html'
            } else {
                alert(result.message);
            }
        });
    })





});