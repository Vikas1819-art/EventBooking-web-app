
$(document).ready(function () {

    let selectedSeats = [];

    // Select or deselect multiple available seats
    $(".seat:not(.booked)").click(function () {

        const seat = {
            id: $(this).data("seat-id"),
            seatNumber: $(this).data("seat-number"),
            price: Number($(this).data("price"))
        };

        const index = selectedSeats.findIndex(
            s => String(s.id) === String(seat.id)
        );

        if (index >= 0) {
            selectedSeats.splice(index, 1);
            $(this).removeClass("selected");
        } else {
            selectedSeats.push(seat);
            $(this).addClass("selected");
        }

        updateSelection();
    });

    function updateSelection() {

        $("#selectedSeat").text(
            selectedSeats.length
                ? selectedSeats.map(s => s.seatNumber).join(", ")
                : "None"
        );

        const total = selectedSeats.reduce(
            (sum, seat) => sum + seat.price,
            0
        );

        $("#selectedPrice").text(total.toFixed(2));

        $("#bookButton")
            .prop("disabled", selectedSeats.length === 0)
            .text("Book Selected Seats (" + selectedSeats.length + ")");
    }

    $("#bookButton").click(function () {

        if (selectedSeats.length === 0) return;

        const eventId = new URLSearchParams(
            window.location.search
        ).get("eventId");

        if (!eventId) {
            alert("Event ID is missing.");
            return;
        }

        const bookingData = {
            eventId: Number(eventId),
            seatIds: selectedSeats.map(s => s.id)
        };

        const button = $(this);
        button.prop("disabled", true);
		
		
		const csrfToken = document.querySelector('meta[name="_csrf"]').content;
		const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        $.ajax({
            url: "/api/bookings/bulk",
            type: "POST",
		    headers:{
				[csrfHeader]:csrfToken
			},
            contentType: "application/json",
            data: JSON.stringify(bookingData),

            success: function () {
                alert("All selected seats booked successfully!");
                window.location.reload();
            },

            error: function (xhr) {
                alert(
                    xhr.responseText ||
                    "Unable to book the selected seats."
                );

                button.prop("disabled", selectedSeats.length === 0);
            }
        });
    });

});