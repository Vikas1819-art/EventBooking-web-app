
$(document).ready(function () {

    let selectedSeat = null;


    // Select a seat
    $(".seat:not(.booked)").click(function () {

        $(".seat.selected")
            .removeClass("selected");


        $(this).addClass("selected");


        selectedSeat = {

            id: $(this).data("seat-id"),

            seatNumber: $(this).data("seat-number"),

            price: $(this).data("price")

        };


        $("#selectedSeat")
            .text(selectedSeat.seatNumber);


        $("#selectedPrice")
            .text(selectedSeat.price);


        $("#bookButton")
            .prop("disabled", false);

    });


    // Book selected seat
    $("#bookButton").click(function () {

        if (!selectedSeat) {
            return;
        }


        const params =
            new URLSearchParams(window.location.search);

        const eventId =
            params.get("eventId");


        const bookingData = {

           /* userId: 1,*/ // currently passing user id using http session

            eventId: Number(eventId),

            seatId: selectedSeat.id

        };


        $.ajax({

            url: "/api/bookings",

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(bookingData),


            success: function (booking) {

                alert(
                    "Seat " +
                    booking.seatNumber +
                    " booked successfully!"
                );


                $(".seat.selected")
                    .removeClass("selected")
                    .addClass("booked")
                    .prop("disabled", true);


                selectedSeat = null;


                $("#selectedSeat")
                    .text("None");


                $("#selectedPrice")
                    .text("0");


                $("#bookButton")
                    .prop("disabled", true);

            },


            error: function (xhr) {

                alert(
                    xhr.responseText ||
                    "Unable to book seat."
                );

            }

        });

    });

});

