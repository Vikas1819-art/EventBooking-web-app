
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Events</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
        

<link href="https://cdn.jsdelivr.net/npm/tom-select@2.6.2/dist/css/tom-select.bootstrap5.css"
      rel="stylesheet">        
        

</head>

<body>

<nav class="navbar navbar-dark bg-dark">

    <div class="container">

        <a class="navbar-brand" href="/events-page">
            Event Booking System
        </a>
        
        

       <div>
            <a href="/venues-page"
               class="btn btn-outline-light me-2">
                Venues
            </a>


            <sec:authorize access="hasRole('ADMIN')">
	            <a href="/users-page"
	               class="btn btn-outline-light me-2">
	                Users
	            </a>
            </sec:authorize>
            
           
           
           <c:if test="${userId != null}">
        
            <a href="${pageContext.request.contextPath}/bookings-page" 
               class="btn btn-outline-light me-2">
               My Bookings
            </a>
            </c:if>
            
            
            
			<form action="${pageContext.request.contextPath}/logout"
			      method="post"
			      class="d-inline">
			      
			      <input type="hidden"
			      name="${_csrf.parameterName}"
			      value="${_csrf.token}">
			
			    <button type="submit" class="btn btn-danger">
			        Logout
			    </button>
			
			</form>
			            
            
            
            
           
         </div>
 
           
           
        

    </div>

</nav>


<div class="container mt-5">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <h2>Available Events</h2>


	<sec:authorize access="hasRole('ADMIN')">
        <a href="/create-event"
           class="btn btn-primary btn-sm">
            + Create Event
        </a>
        </sec:authorize>
        

    </div>


   

    <form method="get"
          action="${pageContext.request.contextPath}/events-page"
          class="row g-2 mb-3">

        <div class="col-md-5">

            <input type="text"
                   name="search"
                   value="${search}"
                   class="form-control  "
                   placeholder="Search event name">

        </div>


        <div class="col-md-4">

            <select name="venueId"
            		id="venueId"
                    class="form-select  ">

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


        <div class="col-md-3 d-flex gap-2">

            <button type="submit"
                    class="btn btn-primary flex-fill">
                Search
            </button>
            
            
            <a class="btn btn-secondary  flex-fill" href="/events-page">Reset</a>

        </div>

    </form>


    <c:choose>

        <c:when test="${empty events}">

            <div class="alert alert-warning">
                No events available for this name.
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

                            <td>${event.getFormattedEventDate}</td>

                            <td>${event.getFormattedEventTime}</td>

                            <td>

                                <a href="${pageContext.request.contextPath}/seats-page?eventId=${event.id}"
                                   class="btn btn-success btn-sm">
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

<script src="https://cdn.jsdelivr.net/npm/tom-select@2.6.2/dist/js/tom-select.complete.min.js"></script>

  <script>
    new TomSelect("#venueId", {
        create: false
    });
    
    
    new TomSelect("#userId", {
        create: false
    });
    
   </script> 



</body>
</html>



