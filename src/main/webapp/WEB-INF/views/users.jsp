<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<title>Users</title>

 <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body>
<nav class="navbar navbar-dark bg-dark">
      <div class="container">
      
      <a class="navbar-brand" href="/events-page">
      Event Booking System</a>
      
      <div>
      
      <a class="btn btn-outline-light me-2" href="/events-page"> Events </a>
      <a class="btn btn-outline-light " href="/venues-page"> Venues </a>
      
      
      </div>
      
      </div>


</nav>

<div class="container mt-5">

<div class="d-flex justify-content-between align-item-center mb-4">

<h2>List of Available Users</h2>

<a class="btn btn-success" href="/create-user"> + Add User</a>

</div>

   <c:choose>
   
			   <c:when test="${empty users }">
			     
			     <div class="alert alert-warning">
			       No users found.
			     </div>
			   
			   </c:when>
   
   <c:otherwise>
     
    
     <table class="table table-bordered ">
     
     		<thead class="table-dark">
    				 <tr>
     
    				 		<th>Sr.no</th>
    				 		<th>Name</th>
    				 		<th>Email</th>
    				 		<th>Actions</th>
    				 		
    				 </tr>
     
    		 </thead>
    		 <tbody>
    		 
    		 <c:forEach var="user" items="${users}" varStatus="status">
    		 
    		 <tr>
    		      <td>${status.count}</td>
    		      <td>${user.name }</td>
    		      <td>${user.email }</td>
    		      <td>
    		      
    		      <a href="/update-user?userId=${user.id}" class="btn btn-primary">Update</a>
    		      
    		     <%--  <a href="/delete-user?userId=${user.id }" class="btn btn-danger">Delete</a> --%>
    		     
        <!-- use thee class instead of id because id should be unique and it cannot be used inside for each -->
    		     
    		     
    		   <%--   but we cannot give delete user button
    		     <a  class="btn btn-danger deleteUserButton" data-id="${user.id}">Delete</a>
    		       --%>
    		      </td>
    		 
    		 </tr>
    		 
    		 
    		 </c:forEach>
    		 
    		 
    		 
    		 
    		 
    		 
    		 
    		 
    		 </tbody>
     
     </table>
    
   
   
   
   
   
   
   
   </c:otherwise>
   
   
   
    </c:choose>




</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>

			$(".deleteUserButton").click(function(user){
				
				/* const button = $(this);
				const userId = button.data("id"); */
				
				const userId = $(this).data("id");
				
				if(!confirm("Are you sure want to delete this user ?")){
					return ;
				}
				
				$.ajax({
					url : "/api/users/"+userId ,
					type : "DELETE",
					
					success : function(response){
						alert("user deleted successfully");
						
						window.location.href="/users-page";
					},
					
					error : function(xhr){
						alert("something went wrong!")
						
					}
					
				})
			});
				
				
				
				
			






</script>

</body>


</html>