
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Create Venue</title>


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

        <a href="/venues-page" class="btn btn-outline-light">
            Venues
        </a>
    </div>

</div>


</nav>

<div class="container mt-5">

<div class="row justify-content-center">

    <div class="col-md-6">

        <div class="card shadow">

            <div class="card-body">

                <h2 class="mb-4 text-center">Create Venue</h2>

				<!-- action=create-venue method="post" -->
                <form  id="createVenueForm" > 

                    <div class="mb-3">

                        <label class="form-label">
                            Venue Name
                        </label>

                        <input type="text"
                               name="name"
                               id="name"
                               class="form-control"
                               placeholder="Enter event name"
                            
                               >
                               <span id="nameError" class="text-danger"></span>

                    </div>


                    <div class="mb-3">

                        <label class="form-label">
                            Venue Address
                        </label>

                        <input type="text"
                               name="address"
                               id="address"
                               class="form-control"
                               placeholder="Enter venue address"
                               
                               >
                               <span id="addressError" class="text-danger"></span>

                    </div>


                    <div class="mb-3">

                        <label class="form-label">
                            Venue Capacity 
                        </label>

                        <input type="number"
                               name="capacity"
                               id="capacity"
                               class="form-control"
                               
                             
                               >
                               <span id="capacityError" class="text-danger"></span>

                    </div>


                   

<div class="d-flex justify-content-center align-item-center">
                    <button type="submit"
                            class="btn btn-primary ms-12">
                        Create Venue
                    </button>
</div>
                   

                </form>

            </div>

        </div>

    </div>

</div>

</div>


<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>

$(document).ready(function() {

    $("#createVenueForm").submit(function(event) {

        event.preventDefault();

        $(".text-danger").text("");

        const venueData = {
            name: $("#name").val(),
            address: $("#address").val(),
            capacity: Number($("#capacity").val())
        };

        $.ajax({

            url: "/api/venues",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(venueData),

            success: function(response) {

                alert("Venue created successfully!");

                window.location.href = "/venues-page";
            },

            error: function(xhr) {

                if (xhr.status === 400) {

                    const errors = xhr.responseJSON;

                    $.each(errors, function(field, message) {

                        $("#" + field + "Error").text(message);

                    });

                } else {

                    alert("Something went wrong");

                }

            }

        });

    });

});

</script>

 



</body>
</html>
