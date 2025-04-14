$(document).ready(function () {
    var warning = 'Add contact before proceed !!!!!';
        if ($(".send_money__form span").text() != warning) {

            $(".button_pay").on("submit",function () {
                return true;
            });

        } else {

            $(".send_money__form span").css({
                'color': 'red'
            });

            $("#myModal_transaction_not_enough_funds").hide();
            $("#globalErrorTransaction").hide();
            $("#description").hide();

            $("#transactionForm").on("submit",function (event) {
                event.preventDefault();
                $("#emty_contact_list_transactionPage").fadeIn("slow");
                return false;
            });
            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });
            $(".button_warning").click(function () {
                $(".overlay_warning_empty_list").fadeOut("slow");
            });
        };
});