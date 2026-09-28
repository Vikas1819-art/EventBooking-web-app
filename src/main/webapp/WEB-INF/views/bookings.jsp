
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>

    <title>My Bookings</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>

<body>

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="/events-page">
            Event Booking System
        </a>


    <div>
        <a href="/events-page" class="btn btn-outline-light me-2">
            Events
        </a>

        <a href="/venues-page" class="btn btn-outline-light">
            Venues
        </a>
    </div>
</div>

</nav>

<div class="container mt-5">

    <h2 class="mb-4">My Bookings</h2>

   
        <div class="mb-2">
            <strong>User:</strong> ${user.name}
            <br>
            <strong>Email:</strong> ${user.email}
        </div>
        
        
        
        <form action="${pageContext.request.contextPath}/bookings-page" method="get" class="row g-2 mb-4">
        
        <input type="hidden" name="userId" value="${user.id}" >
        
        	<div class="col-md-5">
        	
        	<input type="text" 
		        	name="search" 
		        	value="${search}" 
		        	class="form-control"
		        	 placeholder="Search here...">
        	
        	</div>
        
            <div class="col-md-4">
            
            <select name="status" class="form-select">
            
            <option value="">Select Status</option>
              
              <c:forEach var="status" items="${statuses}">
              
             
                    <option value="${status}" 
                    <c:if test="${status == selectedStatus }"> 
                         selected
                    </c:if>>
                    ${status}
                    
                    </option>
              
              </c:forEach>
                   
            
            </select>
            
            
            </div>
        
            <div class="col-md-3">
            
            <button type="submit" class="btn btn-outline-dark btn-success">Search</button>
            
            <a href="${pageContext.request.contextPath}/bookings-page?userId=${user.id}" 
            class="btn btn-secondary">
             Reset </a>
            
            
            </div>
        
        
        
        
        
        </form>
        
        
        
        
   
   

    <c:choose>

        <c:when test="${empty bookings}">

            <div class="alert alert-warning">
                No bookings found.
            </div>

        </c:when>

        <c:otherwise>

            <table class="table table-bordered">

                <thead class="table-dark">

                    <tr>
                        <th>Event</th>
                        <th>Seat</th>
                        <th>Booking Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>

                </thead>

                <tbody>

                    <c:forEach var="booking" items="${bookings}">

                        <tr data-booking-id="${booking.id}">

                            <td>
                                ${booking.eventName}
                            </td>

                            <td>
                                ${booking.seatNumber}
                            </td>

                            <td>
                                ${booking.bookingDate}
                            </td>

                            <td>

                                <c:choose>

                                    <c:when test="${booking.status == 'PENDING'}">

                                        <span class="badge bg-warning text-dark status">
                                            PENDING
                                        </span>

                                    </c:when>

                                    <c:when test="${booking.status == 'CONFIRMED'}">

                                        <span class="badge bg-success status">
                                            CONFIRMED
                                        </span>

                                    </c:when>

                                    <c:otherwise>

                                        <span class="badge bg-danger status">
                                            CANCELLED
                                        </span>

                                    </c:otherwise>

                                </c:choose>

                            </td>

                       
                                
				 <td>
				
				    <c:if test="${booking.status == 'PENDING'}">
				
				        <button type="button"
				                class="btn btn-success btn-sm confirm-btn"
				                data-id="${booking.id}"
				                data-user-id="${booking.userId}"
				                data-event-id="${booking.eventId}"
				                data-seat-id="${booking.seatId}">
				            Confirm
				        </button>
				
				    </c:if>
				
				    <c:if test="${booking.status == 'PENDING' || booking.status == 'CONFIRMED'}">
				
				        <button type="button"
				                class="btn btn-danger btn-sm cancel-btn"
				                data-id="${booking.id}"
				                data-user-id="${booking.userId}"
				                data-event-id="${booking.eventId}"
				                data-seat-id="${booking.seatId}">
				            Cancel
				        </button>
				
				    </c:if>
				
				</td>



                           

                        </tr>

                    </c:forEach>

                </tbody>

            </table>
            
            
            
            
            <c:if test="${totalPages > 1}">

                <nav>

                    <ul class="pagination justify-content-center">

                        <c:if test="${currentPage > 0}">

                            <li class="page-item">

                                <a class="page-link"
                                   href="?page=${currentPage - 1}&search=${search}&userId=${user.id}&status=${selectedStatus}">
                                    Previous
                                </a>

                            </li>

                        </c:if>


                        <c:forEach begin="0"
                                   end="${totalPages - 1}"
                                   var="i">

                            <li class="page-item
                                ${i == currentPage ? 'active' : ''}">

                                <a class="page-link"
                                   href="?page=${i}&search=${search}&userId=${user.id}&status=${selectedStatus}">
                                    ${i + 1}
                                </a>

                            </li>

                        </c:forEach>


                        <c:if test="${currentPage < totalPages - 1}">

                            <li class="page-item">

                                <a class="page-link"
                                   href="?page=${currentPage + 1}&search=${search}&userId=${user.id}&status=${selectedStatus}">
                                    Next
                                </a>

                            </li>

                        </c:if>

                    </ul>

                </nav>

            </c:if>
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            

       </c:otherwise>

    </c:choose>

</div>




<script>


$(".cancel-btn").click(function () {

    const button = $(this);
    const bookingId = button.data("id");

    if (!confirm("Are you sure you want to cancel this booking?")) {
        return;
    }

    const bookingData = {
        userId: button.data("user-id"),
        eventId: button.data("event-id"),
        seatId: button.data("seat-id"),
        status: "CANCELLED"
    };

    $.ajax({
        url: "${pageContext.request.contextPath}/api/bookings/" + bookingId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(bookingData),

        success: function (booking) {

            const row = button.closest("tr");

            row.find(".status")
                .removeClass("bg-warning bg-success")
                .addClass("bg-danger")
                .removeClass("text-dark")
                .text("CANCELLED");

            button.remove();

            alert("Booking cancelled successfully.");
        },

        error: function (xhr) {

            console.log("Status:", xhr.status);
            console.log("Response:", xhr.responseText);

            alert(xhr.responseText || "Unable to cancel booking.");
        }
    });
});



$(".confirm-btn").click(function () {

    const button = $(this);
    const bookingId = button.data("id");

    if (!confirm("Are you sure you want to confirm this booking?")) {
        return;
    }

    const bookingData = {

        userId: button.data("user-id"),
        eventId: button.data("event-id"),
        seatId: button.data("seat-id"),
        status: "CONFIRMED"

    };

    $.ajax({

        url: "${pageContext.request.contextPath}/api/bookings/" + bookingId,

        type: "PUT",

        contentType: "application/json",

        data: JSON.stringify(bookingData),

        success: function (booking) {

            const row = button.closest("tr");

          
            row.find(".status")
                .removeClass("bg-warning")
                .addClass("bg-success")
                .removeClass("text-dark")
                .text("CONFIRMED");

            /// itt will remove the confirm button after execution
            button.remove();

            alert("Booking confirmed successfully.");

        },

        error: function (xhr) {

            console.log("Status:", xhr.status);
            console.log("Response:", xhr.responseText);

            alert(xhr.responseText || "Unable to confirm booking.");

        }

    });

});




</script>

</body>
</html>

