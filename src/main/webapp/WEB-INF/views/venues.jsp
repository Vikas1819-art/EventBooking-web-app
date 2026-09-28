<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Venues</title>


<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">


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

       <!--  <a href="/venues-page" class="btn btn-outline-light">
            Venues
        </a> -->
        
        <a href="/users-page" class="btn btn-outline-light me-2">
            Users
        </a>
        
    </div>

</div>


</nav>

<div class="container mt-5">


<div class="d-flex justify-content-between align-items-center mb-4">

    <h2>Available Venues</h2>

    <a href="/create-venue" class="btn btn-primary">
        + Create Venue
    </a>

</div>


<div class="row">

    <c:forEach var="venue" items="${venues}">

        <div class="col-md-4 mb-4">

            <div class="card shadow-sm h-100">

                <div class="card-body">

		<div class="d-flex justify-content-between m-2"  >

                    <h5 class="card-title">
                        ${venue.name}
                    </h5>
                    
                    <a class="btn btn-success" href="/update-venue?venueId=${venue.id}">Update</a>
                    
		</div>
                    <p class="card-text">
                        <strong>Address:</strong>
                        ${venue.address}
                    </p>

                    <p class="card-text">
                        <strong>Capacity:</strong>
                        ${venue.capacity}
                    </p>

                </div>

            </div>

        </div>

    </c:forEach>


    <c:if test="${empty venues}">

        <div class="alert alert-info">
            No venues available.
        </div>

    </c:if>

</div>


</div>

<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
