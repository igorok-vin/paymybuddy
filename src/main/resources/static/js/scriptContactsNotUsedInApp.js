$(document).ready(function () {
    var correct = 'Add contact before proceed !!!!!';
    $('span').each(function () {
        /*Warning for emty list modal dialog on contact.html*/
        if ($(this).text() == correct) {
            $(this).css({
                'color': 'red'
            });

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

        } else {
            $(".btn-primary").click(function () {
                $(".overlay_deletion_confirmation").fadeIn("slow");
            });
            $(".modal_window_deletion_confirmation__close").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });
        }
    });
});