<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
	<html>
	<head>
			<title>Create User</title>
	
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
	
			<div class="row justify-content-center"> 
			
				<div class="col-md-6">
				
					<div class="card shadow">
					
						<div class="card-body">
						
						
								<h2 class="mb-4 text-center">Create User</h2>
								
						<!-- 		action="create-user" method="post" -->
								
								<form id="createUserForm">
									
									<div class="mb-3">
									
									 <label class="form-label">
                                      Name
                                     </label>

                        <input type="text"
                               name="name"
                               id="name"
                               class="form-control"
                               placeholder="Enter User name"
                               > 
                               <span id="nameError" class="text-danger"></span>
									
									</div>
								
								<div class="mb-3">

                        <label class="form-label">
                            Email
                        </label>

                        <input type="text"
                               name="email"
                               id="email"
                               class="form-control"
                               placeholder="Enter user email"
                               >
                               <span id="emailError" class="text-danger"></span>

                    </div>
                    
                    <div class="mb-3">

                        <label class="form-label">
                            Password
                        </label>

                        <input type="password"
                               name="password"
                               id="password"
                               class="form-control"
                               placeholder="Enter Password"
                               >
                               <span id="passwordError" class="text-danger"></span>

                    </div>
								
								<div class="d-flex justify-content-center align-item-center" >
								<button type="submit" class="btn btn-primary">
								Create User
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
		
		$("#createUserForm").submit(function(user){
			
			user.preventDefault();
			
			$(".text-danger").text("");
			
			const userData = {
					name: $("#name").val(),
					email:$("#email").val(),
					password:$("#password").val()
			};
			
			$.ajax({
				
				url: "/api/users",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(userData),
				
				success : function(response){
					alert("user created successfully!!");
					
					window.location.href="/users-page"
				},
				
				error : function(xhr){
					
					if(xhr.status === 400){
						
						const errors = xhr.responseJSON ;
						
						$.each(errors ,function(field , message){
							
							$("#" + field + "Error").text(message);
							
						})
						
					}else{
						
						alert("something went wrong!");
						
					}
					
				}
				
			});
			
			
			
			
		});
		
	});
	
	
	
	
	</script>
	
	
	
	
	
	
	
	
	
	
	
	</body>
	
	
	
	
	
	</html>