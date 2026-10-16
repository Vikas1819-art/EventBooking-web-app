
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>

<head>

    <title>Select Seats</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
        
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">


		<meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
 
    
</head>


<body>

<nav class="navbar navbar-dark bg-dark">
   <div class="container">
   
   		<a class="navbar-brand"> Event Booking System</a>

				<div>
				
				<a href="/events-page" class="btn btn-outline-light me-2"> Events</a>
				<a href="/venues-page" class="btn btn-outline-light me-2">Venues</a>
				
				
		     <c:if test="${userId != null}">
	            <a href="${pageContext.request.contextPath}/bookings-page" 
	               class="btn btn-outline-light">
	               My Bookings
	            </a>
             </c:if>
				
				
				</div>

   </div>

</nav>





 <div class="container mt-4 mb-5">

   
 <div class="d-flex justify-content-between align-items-center mb-2">
 
    <h2 class="text-center">
        Select Your Seat
    </h2>
    
    
          <sec:authorize access="hasRole('ADMIN')">   
				<c:if test="${not hasSeats}">
				    <a href="/generate-page?eventId=${event.id}"
				       class="btn btn-success">
				        Generate Seats
				    </a>
				</c:if>
         </sec:authorize> 
</div>
  

    <div class="text-center mt-3">

        <h4>
            ${event.name}
        </h4>

        <p class="mb-1">
            Venue: ${event.venueName}
        </p>

        <p>
            Date: ${event.getFormattedEventDate}
            |
            Time: ${event.getFormattedEventTime}
        </p>

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
    

    <div class="stage">
        STAGE
    </div>
    
    




    <div class="seat-layout">

        <div id="seatLayout">

            <c:set var="previousRow" value="" />


            <c:forEach var="seat" items="${seats}">

             
                
                
                <c:set
				    var="currentRow"
				    value="${seat.rowLabel}"
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


  

   


   <!-- information of the selected seat -->

    
		<div class="text-center mt-4">
		
		    <h5>
		        Selected Seats:
		        <span id="selectedSeat">None</span>
		    </h5>
		
		    <h5>
		        Total Price: ₹<span id="selectedPrice">0</span>
		    </h5>
		
		    <button
		        id="bookButton"
		        class="btn btn-primary mt-2"
		        disabled>
		        Book Selected Seats
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

