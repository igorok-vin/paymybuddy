
$(window).on("load", function () {

        $(".home__item").addClass('animate__animated');
        setTimeout(function () {
                $(".home__item").removeClass('animate__animated');
        },2000);
});
