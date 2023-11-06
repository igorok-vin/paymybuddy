$(document).ready(function () {
    /* Warning for emty list modal dialog or
    Deletion confirmation modal dialog  */

    /*Шукаємо всі span на сторінці якщо в якомусь із них(тілько в одному має зявитись)
    зявляєтьмся надпис з змфної correct то надпис стає червоним і при повторному
    нажиманні на delete в формі зявляється повідомлення про пустий список.
    Якщо список з контактами і напису немає зявляється вікно тільки з підвердженням
    видалення. При пустому списку разом з попередження вискакує вікно про видалення
    то приходиться його ховати через hide() */


   /* $('#balance_submit').on('click',function () {
        $(' .balance__form .send_money__input_number').each(function () {
            if($(this).val()==0.0){
                var modal = document.getElementById("myModal");
                modal.style.display="flex";
            }

                /!*$('.overlay_warning_empty_list').show();*!/
            /!*alert('Не все поля формы заполнены верно.');*!/
        });
    });*/

   /* $('document').ready(function() {
        $('#button').on('click', function() {
            $('.form_box .rfield').each(function() {
                if ($(this).val() != '') {
                    // Если поле не пустое удаляем класс-указание
                    $(this).removeClass('empty_field');
                } else {
                    // Если поле пустое добавляем класс-указание
                    $(this).addClass('empty_field');
                }
            });

            if ($('.form_box .rfield.empty_field').length) alert('Не все поля формы заполнены верно.')
        });
    });*/



       /* $('input').each(function () {
            if($(this).text() == 0.0 ) {
                $("#balanceForm").on("submit",function (event) {
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

           /* $(".btn-primary").click(function () {
                $("#myModal_transaction_not_enough_funds").hide();
                $("globalError").hide();
                $("#emty_contact_list").fadeIn("slow");
            });

            $(".modal_window_warning_empty_list__close").click(function () {
                $(".overlay_deletion_confirmation").hide();
                $(".overlay_warning_empty_list").fadeOut("slow");
            });*/

            /*Warning for emty list modal dialog on transaction.html*/
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

          /*  $(".button_pay").on("submit",function () {
                return true;
            });*/
        }
    });





    /*var button = document.getElementById('balance_submit');
    button.onclick=function () {
        var balance = document.getElementById('balance').value;
        if (balance == 0.0) {

            $("#balanceForm").on("submit",function (event) {
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
    };*/

   /* var balance_submit = document.getElementById('balance_submit');
    balance_submit.addEventListener('click', n);
    function n(){
        var value = document.getElementById('balance');
        if((value.value)==0.0) {

            $("#balanceForm").on("submit",function (event) {
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
    }*/

    /*let balance_submit;
    balance_submit.onclick = function () {
        var val = document.getElementById(bala)

    }*/


});