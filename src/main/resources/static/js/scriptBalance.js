$(document).ready(function () {
    var button = document.getElementById("balance_button");

    button.onclick = function (){
        $('.balance__form .send_money__input_number').each(function (){
            if($(this).val()==0.0) {
                $(".overlay_warning_empty_list").fadeIn("slow");
                $(".modal_window_warning_empty_list__close").click(function () {
                    $(".overlay_warning_empty_list").fadeOut("slow");
                });
                $(".button_warning").click(function () {
                    $(".overlay_warning_empty_list").fadeOut("slow");
                });

            }else {
                const b = document.querySelector('#balance_button');
                b.setAttribute('type','submit');
            }
        });
    };
});