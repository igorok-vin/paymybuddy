$(document).ready(function () {
    var correct = 'Add contact before proceed !!!!!';
    $('span').each(function () {
        /*Warning for emty list modal dialog on contact.html*/
        if ($(this).text() == correct) {
            $(this).css({
                'color': 'red'
            });

            $("#myModal_transaction_not_enough_funds").hide();
            $("#globalError").hide();
            $("#description").hide();
            $("#transactionForm").on("submit",function (event) {
                event.preventDefault();
                $("#emty_contact_list").fadeIn("slow");
                return false;
            });
            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });
            $(".button_warning").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });

        } else if ($(this).text() != correct) {
            $(".btn-primary").click(function () {
                $(".overlay_deletion_confirmation").fadeIn("slow");
            });
            $(".modal_window_deletion_confirmation__close").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });
        }
    });

    $(".home__item").mouseenter(function () {
        $(".home__item").removeClass('animate__animated');
        $(this).toggleClass('scale-up').siblings().toggleClass('scale-down');
    })
});