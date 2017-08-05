/**
 * Created by Tony on 2017/8/2.
 */
$(function () {
    setInterval(function () {
        $(".time").text(date2string(new Date(), 2));
    }, 1000);
});