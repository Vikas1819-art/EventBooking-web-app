<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCKTYPE html>
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
     
     <a class="navbar-brand" href="/events-page"> Event Booking System</a>
     
    
     
     <div>
     
     <a href="/events-page" class="btn btn-outline-light me-2">Events</a>
     
     <a href="/users-page" class="btn btn-outline-light">Users</a>
     
     </div>


      </div>
</nav>

<div class="container mt-5">

<div class="row justify-content-center">
				<div class="col-md-6">
					<div class="card shadow">
						<div class="card-body">
						  
		 <h4 class="mb-4 text-center">Update user for : <i style="color:grey;"><b>${user.name}</b></i>   </h4>
						  
						  <!-- action="update-user" method="post" -->
						  
						<form  id="updateUserForm">
						
						<input type="hidden" name="userId" value="${user.id}">
						
						<div class="mb-3">
						
						<label class="form-label">User Name</label>
						<input type="text" 
								name="name" 
								id="name"
								 value="${user.name}" 
								class="form-control"
								placeholder="enter new name"
								>
								<span id="nameError" class="text-danger"></span>
						
						</div>
						
						
						
						<div class="mb-3">
						<label class="form-label"> Password </label>
						<input type="password"
								name="password"
								id="password"
								class="form-control"
									placeholder="enter new password"
									>
									<span id="passwordError" class="text-danger"></span>
						</div>
						
						<div class="d-flex justify-content-center align-items-center" >
								<button type="submit" class="btn btn-primary">Update User</button>
						
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
	  
	  $("#updateUserForm").submit(function(user){
		  
		  user.preventDefault();
		  
		  $(".text-danger").text("");
		  
		
		 
		 const params = new URLSearchParams(window.location.search);
		 const userId = params.get("userId");
		  
		  const userData = {
				  name : $("#name").val(),
				  password: $("#password").val()
		  }
		  
		  $.ajax({
			  
			  url :"/api/users/"+ userId ,
			  type : "PUT",
			  contentType:"application/json",
			  data : JSON.stringify(userData),
			  
			  success : function(response){
				  
				  alert("user updated successfully");
				  
				  window.location.href="/users-page";
			  },
			  
			  error : function(xhr){
				  
				  if(xhr.status === 400){
					  const errors = xhr.responseJSON ;
					  
					  $.each(errors , function(field , message){
						  $("#" + field + "Error").text(message);
					  })
				  }else {
					  alert("something went wrong")
				  }
			  }
			  
		  });
		  
	  });
	  
  });





</script>











</body>



</html>