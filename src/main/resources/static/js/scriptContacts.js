$(document).ready(function () {
    var correct = 'Add contact before proceed !!!!!';
    var button = document.getElementById("delete_button_contact_form");

    if($("#delete_selection span").text() !=correct){
        button.onclick=function (){

            selectedEmail = $('.chosen-single').find('span').last().text();

            $(".overlay_deletion_confirmation").fadeIn("slow");
            $(".modal_window_deletion_confirmation__subtitle").text("Are you sure you want to delete this contact: " + selectedEmail +"?");
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
});