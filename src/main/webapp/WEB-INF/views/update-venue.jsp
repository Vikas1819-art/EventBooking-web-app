<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
		<title>Update User</title>


<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">


</head>

<body>

		<nav class="navbar navbar-dark bg-dark">
				<div class="container">

		<a class="navbar-brand" href="/event-page"> Event Booking System</a>


	<div>
        <a href="/events-page" class="btn btn-outline-light me-2">
            Events
        </a>

       <a href="/venues-page" class="btn btn-outline-light">
            Venues
        </a>
        
        <!-- <a href="/users-page" class="btn btn-outline-light me-2">
            Users
        </a> -->
        
    </div>

</div>
</nav>

		<div class="container mt-5">
		
		<div class=" row justify-content-center ">
			<div class="col-md-4">
		
			<div class="card shadow">
					<div class="card-body">
					
					<h4 class="mb-4 text-center">Update venue for : <i style="color:grey;"><b>${venue.name}</b></i>   </h4>
					
			<!-- 		action="update-venue" method="post" -->
					
					<form id="updateVenueForm" >
						
						<input type="hidden" name="venueId" value="${venue.id }">
						
						<%-- <div class="mb-3">
						<label class="form-label">Name</label>
						<input class="form-control" type="text" name="name" value="${venue.name}">
						</div> --%>
						
						<div class="mb-3">
						
						<label class="form-label">Address</label>
						<input class="form-control" 
								type="text" 
								name="address" 
								id="address"
								value="${venue.address}">
								<span id="addressError" class="text-danger"></span>
								
						</div>
						
						<div class="mb-3">
						<label class="form-label">Capacity</label>
						<input class="form-control"
								 type="text"
								  name="capacity" 
								  id="capacity"
								  value="${venue.capacity}">
								  <span id="capacityError" class="text-danger"></span>
						
						</div>
						
						<div class="d-flex justify-content-center align-items-center ">
							<button type="submit" class="btn btn-primary">
							Update Venue
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

			$(document).ready(function(){
				
				$("#updateVenueForm").submit(function(venue){
					
					venue.preventDefault();
					
					$(".text-danger").text("");
					
					const params = new URLSearchParams(window.location.search);
					const venueId = params.get("venueId");
					
					const venueData ={
							address : $("#address").val(),
							capacity:$("#capacity").val()
					}
					
					$.ajax({
						url : "/api/venues?id="+venueId ,
						type: "PUT",
						contentType : "application/json",
						data : JSON.stringify(venueData),
						
						success : function(response){
							
							alert("venue updated successfully!!");
							
							window.location.href="/venues-page"
							
						},
						
						error : function(xhr){
							if(xhr.status === 400){
								 const errors = xhr.responseJSON;
								 
								 $.each(errors , function(field , message){
									 $("#"+ field + "Error").text(message);
								 })
								
								
							}else{
								alert("something went wrong!!")
								
							}
						}
					})
					
					
				});
				
			});


</script>




</body>





</html>