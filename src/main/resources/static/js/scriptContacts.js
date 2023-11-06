$(document).ready(function () {
    var correct = 'Add contact before proceed !!!!!';
    var button = document.getElementById("delete_button_contact_form");

    /*тут if не хоче працювати через клас а через ID*/
    if($("#delete_selection span").text() !=correct){
        button.onclick=function (){
            $(".overlay_deletion_confirmation").fadeIn("slow");
            $("#button_submit_modal_window_deletion_confirmation").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });
            $(".modal_window_deletion_confirmation__close").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });
        }
    }else {
        $("#delete_selection span").css({
            'color': 'red'
        });
        button.onclick=function (){
            $("#empty_contact_list_contactsPage").fadeIn("slow");
            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });
            $("#button_empty_contact_list_contactsPage").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });

        }
    };
   /* button.onclick=function() {
       $(".contact__form span").each(function (){
         if ($(this).text()==correct) {
             $(this).css({
                 'color': 'red'
             });
             $("#empty_contact_list_contactsPage").fadeIn("slow");
         }

       })};*/

    /*$('span').each(function () {
        /!*Warning for emty list modal dialog on contact.html*!/
        if ($(this).text() == correct) {
            $(this).css({
                'color': 'red'
            });

            /!*Warning for emty list modal dialog on transaction.html*!/
            $(".contact__form").on("submit",function (event) {
                event.preventDefault();
                $(".overlay_deletion_confirmation").hide();
                $("#empty_contact_list_contactsPage").fadeIn("slow");
                return false;
            });
            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });
            $(".button_warning").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });

        } else /!*if ($(this).text() != correct)*!/ {
            $(".btn-primary").click(function () {
                $(".overlay_deletion_confirmation").fadeIn("slow");
            });
            $(".modal_window_deletion_confirmation__close").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });
        }
    });*/

});