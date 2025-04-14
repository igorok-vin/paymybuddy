$(document).ready(function () {
    /* Warning for emty list modal dialog or
    Deletion confirmation modal dialog  */

    /*Шукаємо всі span на сторінці якщо в якомусь із них(тілько в одному має зявитись) зявляєтьмся надпис з змфної correct то надпис стає червоним і при повторному нажиманні на delete в формі зявляється повідомлення про пустий список.
    * Якщо список з контактами і напису немає зявляється вікно тільки з підвердженням видалення.
    * При пустому списку разом з попередження вискакує вікно про видалення то приходиться його ховати через hide() */


    var correct = 'Add contact before proceed !!!!!';
    var amount = '0.0';
    var description = $("#text").val();;

        /*Warning for emty list modal dialog on contact.html*/
/*
    if (($(".send_money__form span").text() === correct)){

        $(".send_money__form span").css({
            'color': 'red'
        });

        /!*Warning for emty list modal dialog on transaction.html*!/
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

    } else if (amount==='0.0') {
        $(".contact__selection input").css({
            'color': 'red'
        });
        /!*Warning for emty list modal dialog on transaction.html*!/
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

    }else if (description===''||' ') {

      /!*  $(".send_money__form span").css({
            'color': 'red'
        });
        $(".contact__selection input").css({
            'color': 'red'
        });
*!/
        /!*Warning for emty list modal dialog on transaction.html*!/
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

    } else {
        $(".button_pay").on("submit",function () {
            return true;
        });

    }*/


        if (($(".send_money__form span").text() != correct) /*&& (amount!='0.0')*/ /*&& (description==='')*/) {

            $(".button_pay").on("submit",function () {
                return true;
            });

        } else {

            $(".send_money__form span").css({
                'color': 'red'
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
        };




         /*   $("#transactionForm").on("submit",function (event) {
                event.preventDefault();
                $(".overlay_warning_money").fadeIn("slow");
                return false;
            });*/

          /*  $(".button_pay").on("submit",function () {
                $(".overlay_warning_money").fadeIn("slow");
                return false;
            });
*/
/*
    var msg ="There are not enough funds on the balance to complete the transaction. Current balance account is: ";
    const temp = document.getElementById("lowMoney").innerHTML;
    $(".button_pay").on("submit",function () {
        if($(".text-danger").text() === msg){

            $(".overlay_warning_empty_list").fadeIn("slow");
            return false;
        }
        return true;
    });*/



    /*$('input[type="number"]').each(function () {
        var inputValue = $('#send_money__input').val();
    if ( inputValue != '0.0' /!*&& (description==='')*!/) {

        $(".button_pay").on("submit",function () {
            return true;
        });

    } else {

        $(".contact__selection input").css({
            'color': 'red'
        });

        /!*Warning for emty list modal dialog on transaction.html*!/
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
    }
    });*/

    /*var correct = 'Add contact before proceed !!!!!';
    var amount = '0.0';
    var description = '';
    $('.send_money__form span').each(function () {
        /!*Warning for emty list modal dialog on contact.html*!/
        if (($(".send_money__form span").text() === correct) && ($(".send_money__input_number").text()===amount) && ($(".send_money__description").text()===description)) {

            $(".send_money__form span").css({
                'color': 'red'
            });

            /!*Warning for emty list modal dialog on transaction.html*!/
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

        } else /!*if ($(this).text() != correct) *!/{
            $(".btn-primary").click(function () {
                $(".overlay_deletion_confirmation").fadeIn("slow");
            });
            $(".modal_window_deletion_confirmation__close").click(function () {
                $(".overlay_deletion_confirmation").fadeOut("slow");
            });

          /!*  $(".button_pay").on("submit",function () {
                return true;
            });*!/
        }
    })*/


});