<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Create Event</title>


<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">


<link href="https://cdn.jsdelivr.net/npm/tom-select@2.6.2/dist/css/tom-select.bootstrap5.css"
      rel="stylesheet">
      
      <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
      
      

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

<div class="container mt-5 mb-5">

<div class="row justify-content-center">

    <div class="col-md-6">

        <div class="card shadow">

            <div class="card-body">

                <h2 class="mb-4 text-center">Create Event</h2>

				 <!-- action="create-event" method="post" -->

                <form id="createEventForm">

                    <div class="mb-3">

                        <label class="form-label">
                            Event Name
                        </label>

                        <input type="text"
                        		id="name"
                               name="name"
                               class="form-control"
                               placeholder="Enter event name"
                               >
                               <span id="nameError" class="text-danger"></span>

                    </div>


                    <div class="mb-3">

                        <label class="form-label">
                            Event Date & Time
                        </label>

                        <input type="datetime-local"
                               name="eventDateTime"
                               id="eventDateTime"
                               class="form-control"
                               placeholder="select the date and time"
                               
                               >
                                <span id="eventDateTimeError" class="text-danger"></span>

                    </div>


                 
                   
                    
                    
			      <div class="mb-3">
			
			    <label class="form-label">
			        Venue
			    </label>
			
			    <select name="venueId" id="venueId" class="form-select" size="5" >
			
			        <option value="">-- Select Venue --</option>
			
			        <c:forEach var="venue" items="${venues}">
			
			            <option value="${venue.id}">
			                ${venue.name}
			            </option>
			
			        </c:forEach>
			
			    </select>
			     <span id="venueIdError" class="text-danger"></span>
			
			</div>


                   <div class="d-flex justify-content-center align-item-center">
                    <button type="submit"
                            class="btn btn-primary  ">
                        Create Event
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

$(document).ready(function () {

    $("#createEventForm").submit(function (event) {

        event.preventDefault();

        // Clear previous errors
        $(".text-danger").text("");

        const eventData = {
            name: $("#name").val(),
            eventDateTime: $("#eventDateTime").val(),
            venueId: $("#venueId").val()
        };
        
        
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        $.ajax({

            url: "/api/events",
            type: "POST",
            headers:{
            	[csrfHeader]:csrfToken
            },
            contentType: "application/json",
            data: JSON.stringify(eventData),

            success: function (response) {

                alert("Event created successfully.");

                window.location.href = "/events-page";
            },

            error: function (xhr) {

                if (xhr.status === 400) {

                    const errors = xhr.responseJSON;

                    $.each(errors, function (field, message) {

                        $("#" + field + "Error").text(message);

                    });

                } else {

                    alert("Unable to create the event. Please try again.");
                }
            }

        });

    });

});
		
		$("input , select").on("input change" , function(){
			const field = $(this).attr("name");
			$("#"+ field +"Error").text("");
		})
		
		



</script>






<script src="https://cdn.jsdelivr.net/npm/tom-select@2.6.2/dist/js/tom-select.complete.min.js"></script>

   <script>
    new TomSelect("#venueId", {
        create: false
    });
   </script>

</body>
</html>
