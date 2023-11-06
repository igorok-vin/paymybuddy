$(document).ready(function () {
    /*Warning for zero top-upa mount modal dialog on balance.html*/

    //Знаходимо вікно на HTML сторінці через Id
  /*  var modal = document.getElementById("myModal_balance");*/


    //Знаходимо кнопку форми на HTML сторінці через Id яка буде визивати модальне вікно
    // якщо не буде виконуватись певна умова
    var button = document.getElementById("balance_button");

    /*Описується що відбувається при нажиманні на кнопку. Сама кнопка в HTML типу
    * кнопка (type="button") а не type="submit". При submit відбувається POST відправка
    * на сервер потім зявляється модальне вікно на 1сек і відразу оновлення сторінки.
    * Щоб працювало правильно функція submit переноситься в script.js. Якщо умова виконується
    * і сума>0 то тип кнопки міняємо через setAttribute і відбувається submit.
    * Якщо значення суми=0 зявляється модальне вікно*/
    button.onclick = function (){
        /*через функцію вказуєм місце вводу суми на HTML cторінці*/
        $('.balance__form .send_money__input_number').each(function (){
            if($(this).val()==0.0) { //провіряємо умову чи сума=0 , якщо true то зявляється вікно
                $(".overlay_warning_empty_list").fadeIn("slow");
                /*закриття вікна нажимання на хрестик*/
                $(".modal_window_warning_empty_list__close").click(function () {
                    $(".overlay_warning_empty_list").fadeOut("slow");
                });
                /*закриття вікна нажимання на кнопку*/
                $(".button_warning").click(function () {
                    $(".overlay_warning_empty_list").fadeOut("slow");
                });

            }else {
                /*якщо попередня умова false то міняться тип кнопки на submit
                і підтверджується операція*/
                /*через querySelector по ID знаходимо кнопку*/
                const b = document.querySelector('#balance_button');
                /*міняєм атрибут type з button який default на HTML сторінці
                * на submit*/
                b.setAttribute('type','submit');
            }
        });
    };
});