
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Events</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
        
        
        <style>
        
        .form-select {
        	color:white
        }
        
        </style>

</head>

<body>

<nav class="navbar navbar-dark bg-dark">

    <div class="container">

        <a class="navbar-brand" href="/events-page">
            Event Booking System
        </a>
        
        

        <div class="d-flex align-items-center gap-2">

            <a href="/venues-page"
               class="btn btn-outline-light me-2">
                Venues
            </a>

            <a href="/users-page"
               class="btn btn-outline-light me-2">
                Users
            </a>
            
            
            <!-- changes here has done -->
            <a href="${pageContext.request.contextPath}/bookings-page" 
               class="btn btn-outline-light">
               My Bookings
            </a>
            
            
           </div> 
            
            <div class="d-flex align-items-center gap-2"  >
        
       <form action="set-user" method="get" class="d-flex align-items-center gap-2">
        <select name = "userId" class="form-select bg-black text-white " required>
        
        	<option value=""> Select User</option>
        	
	        	<c:forEach var="user" items="${users}">
	        	
	        			<option value="${user.id}"
	        			
	        			<c:if test="${userId == user.id}">selected</c:if>
	        			
	        			
	        			> ${user.name} </option>
	        	
	        	
	        	</c:forEach>
        </select>   
        
        <button type="submit" class="btn btn-secondary " style="height:38px; width:175px">Set User</button>
        
        </form>
        </div>
            

        

    </div>

</nav>


<div class="container mt-5">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <h2>Available Events</h2>

        <a href="/create-event"
           class="btn btn-primary">
            + Create Event
        </a>

    </div>


   

    <form method="get"
          action="${pageContext.request.contextPath}/events-page"
          class="row g-2 mb-4">

        <div class="col-md-5">

            <input type="text"
                   name="search"
                   value="${search}"
                   class="form-control"
                   placeholder="Search event name">

        </div>


        <div class="col-md-4">

            <select name="venueId"
                    class="form-select">

                <option value="">All Venues</option>

                <c:forEach var="venue" items="${venues}">

                    <option value="${venue.id}"
                        <c:if test="${venueId == venue.id}">
                            selected
                        </c:if>>
                        ${venue.name}
                    </option>

                </c:forEach>

            </select>

        </div>


        <div class="col-md-3">

            <button type="submit"
                    class="btn btn-primary w-100">
                Search / Filter
            </button>

        </div>

    </form>


    <c:choose>

        <c:when test="${empty events}">

            <div class="alert alert-warning">
                No events found.
            </div>

        </c:when>


        <c:otherwise>

            <table class="table table-bordered">

                <thead class="table-dark">

                    <tr>

                        <th>Event</th>
                        <th>Venue</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Action</th>

                    </tr>

                </thead>


                <tbody>

                    <c:forEach var="event" items="${events}">

                        <tr>

                            <td>${event.name}</td>

                            <td>${event.venueName}</td>

                            <td>${event.eventDate}</td>

                            <td>${event.eventTime}</td>

                            <td>

                                <a href="${pageContext.request.contextPath}/seats-page?eventId=${event.id}"
                                   class="btn btn-success">
                                    Select Seats
                                </a>

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
                                   href="?page=${currentPage - 1}&search=${search}&venueId=${venueId}">
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
                                   href="?page=${i}&search=${search}&venueId=${venueId}">
                                    ${i + 1}
                                </a>

                            </li>

                        </c:forEach>


                        <c:if test="${currentPage < totalPages - 1}">

                            <li class="page-item">

                                <a class="page-link"
                                   href="?page=${currentPage + 1}&search=${search}&venueId=${venueId}">
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

</body>
</html>

