$(document).ready(function () {
    /* Warning for emty list modal dialog or
    Deletion confirmation modal dialog  */

    /*Шукаємо всі span на сторінці якщо в якомусь із них(тілько в одному має зявитись) зявляєтьмся надпис з змфної correct то надпис стає червоним і при повторному нажиманні на delete в формі зявляється повідомлення про пустий список.
    * Якщо список з контактами і напису немає зявляється вікно тільки з підвердженням видалення.
    * При пустому списку разом з попередження вискакує вікно про видалення то приходиться його ховати через hide() */

    var correct = 'Add contact before proceed !!!!!';
    $('span').each(function () {
        /*Warning for emty list modal dialog on contact.html*/
        if ($(this).text() == correct) {
            $(this).css({
                'color': 'red'
            });
            $(".btn-primary").click(function () {
                $(".overlay_deletion_confirmation").hide();
                $(".overlay_warning_empty_list").fadeIn("slow");
            });

            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_deletion_confirmation").hide();
                $(".overlay_warning_empty_list").fadeOut("slow");
            });

            /*Warning for emty list modal dialog on transaction.html*/
            $("#transactionForm").on("submit",function (event) {
                event.preventDefault();
                $(".overlay_warning_empty_list").fadeIn("slow");
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

          /*  $(".button_pay").on("submit",function () {
                return true;
            });*/
        }
    })


});