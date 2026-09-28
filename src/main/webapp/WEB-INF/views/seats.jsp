
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Select Seats</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
        
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    
</head>


<body>

<nav class="navbar navbar-dark bg-dark">
   <div class="container">
   
   		<a class="navbar-brand"> Event Booking System</a>

				<div>
				
				<a href="/events-page" class="btn btn-outline-light me-2"> Events</a>
				<a href="/venues-page" class="btn btn-outline-light">Venues</a>
				
				</div>

   </div>

</nav>





 <div class="container mt-4">

   
 <div class="d-flex justify-content-between align-items-center mb-2">
 
    <h2 class="text-center">
        Select Your Seat
    </h2>
    
				   <c:if test="${not hasSeats}">
				    <a href="/generate-page?eventId=${event.id}"
				       class="btn btn-success">
				        Generate Seats
				    </a>
				</c:if>

</div>
  

    <div class="text-center mt-3">

        <h4>
            ${event.name}
        </h4>

        <p class="mb-1">
            Venue: ${event.venueName}
        </p>

        <p>
            Date: ${event.eventDate}
            |
            Time: ${event.eventTime}
        </p>

    </div>


    

    <div class="stage">
        STAGE
    </div>




    <div class="seat-layout">

        <div id="seatLayout">

            <c:set var="previousRow" value="" />


            <c:forEach var="seat" items="${seats}">

             
                <c:set
                    var="currentRow"
                    value="${seat.seatNumber.substring(0, 1)}"
                />

                <c:if test="${currentRow != previousRow}">

                 
                    <c:if test="${not empty previousRow}">
                        </div>
                    </c:if>

                 
                    <div class="seat-row">

                        <span class="row-label">
                            ${currentRow}
                        </span>


                    <c:set
                        var="previousRow"
                        value="${currentRow}"
                    />

                </c:if>

                <c:set var="isBooked" value="false" />


                <c:forEach var="booking" items="${bookings}">

                    <c:if test="${booking.seatId == seat.id &&
                                 (booking.status == 'PENDING' ||
                                  booking.status == 'CONFIRMED')}">

                        <c:set
                            var="isBooked"
                            value="true"
                        />

                    </c:if>

                </c:forEach>


                <button
                    type="button"
                    class="seat ${isBooked ? 'booked' : ''}"

                    data-seat-id="${seat.id}"

                    data-price="${seat.price}"

                    data-seat-number="${seat.seatNumber}"

                    ${isBooked ? 'disabled' : ''}>

                    ${seat.seatNumber}

                </button>


            </c:forEach>        

            <c:if test="${not empty previousRow}">
                </div>
            </c:if>

        </div>

    </div>


  

    <div class="text-center mt-4">

        <span class="badge bg-success p-2 me-2">
            Available
        </span>

        <span class="badge bg-primary p-2 me-2">
            Selected
        </span>

        <span class="badge bg-danger p-2">
            Booked
        </span>

    </div>


   <!-- information of the selected seat -->

    <div class="text-center mt-4">

        <h5>
            Selected Seat:
            <span id="selectedSeat">
                None
            </span>
        </h5>


        <h5>
            Price:
            ₹<span id="selectedPrice">
                0
            </span>
        </h5>


        <button
            id="bookButton"
            class="btn btn-primary mt-2"
            disabled>

            Book Selected Seat

        </button>

    </div>

</div>




<script
    src="https://code.jquery.com/jquery-3.7.1.min.js">
</script>




<script
    src="${pageContext.request.contextPath}/js/seats.js">
</script>


</body>

</html>

