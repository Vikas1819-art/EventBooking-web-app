<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
	<html>
	<head>
			<title>Generate Seats</title>
	
	<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">
	
	
	</head>
	
	<body>
	
	<nav class="navbar navbar-dark bg-dark">
			<div class="container">
			
			<a class="navbar-brand" href="/events-page"> Event Booking System</a>
			
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
	
	<h4 class="text-center">Venue Capacity : ${venue.capacity}</h4>
	
			<div class="row justify-content-center"> 
			
				<div class="col-md-6">
				
					<div class="card shadow">
					
						<div class="card-body">
						
						
						
						<h6 id="capacityError" class="text-danger text-center"></h6>
						
						 
						 
						
								<h4 class="mb-4 text-center">generate seats for : <i style="color:grey;"><b>${event.name}</b></i>   </h4>
								
				<!-- 				action="generate-page" method="post" -->
								
								<form id="generateSeatsForm">
								
								
									
									
									<input type="hidden" name="eventId" value="${event.id}">
									
									<div class="mb-3">
									
									 <label class="form-label">
                                      Number of rows :
                                     </label>

                        <input type="text"
                               name="rows"
                               id="rows"
                               class="form-control"
                               placeholder="Enter number of rows"
                               >
                               <span id="rowsError" class="text-danger"></span> 
									
									</div>
								
								<div class="mb-3">

                        <label class="form-label">
                            Seats per Row :
                        </label>

                        <input type="text"
                               name="seatsPerRow"
                               id="seatsPerRow"
                               class="form-control"
                               placeholder="Enter number of seats per row"
                               >
                               <span id="seatsPerRowError" class="text-danger"></span>

                    </div>
                    
                    <div class="mb-3">

                        <label class="form-label">
                            Price:
                        </label>

                        <input type="number"
                               name="price"
                               id="price"
                               class="form-control"
                               placeholder="Enter price"
                               >
                               <span id="priceError" class="text-danger"></span>

                    </div>
                    
                    <input type="hidden" name="venueId" id="venueId" value="${event.venueId}">
								
								<div class="d-flex justify-content-center align-items-center" >
								<button type="submit" class="btn btn-primary">
								Generate Seats
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
		
		$("#generateSeatsForm").submit(function(seats){
			
			seats.preventDefault();
			
			const params = new URLSearchParams(window.location.search);
			const eventId = params.get("eventId");
			
			$(".text-danger").text("");
			
			const seatsData = {
					eventId : eventId ,
					venueId : $("#venueId").val(),
					rows : $("#rows").val(),
					seatsPerRow : $("#seatsPerRow").val(),
					price : $("#price").val()
			}
			
			$.ajax({
				
				url : "/api/seats/generate" ,
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(seatsData),
				
				success : function(response){
					alert("seats are generated successfully!!")
					
					window.location.href ="/seats-page?eventId="+eventId ;
				},
				
				error : function(xhr){
					if(xhr.status ===400 ){
						
						const errors = xhr.responseJSON ;
						$.each(errors , function(field , message){
							$("#" + field + "Error").text(message);
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