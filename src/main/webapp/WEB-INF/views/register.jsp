<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/login">
            Event Booking System
        </a>

        <a href="${pageContext.request.contextPath}/login"
           class="btn btn-outline-light">
            Login
        </a>
    </div>
</nav>

<div class="container mt-5">

    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow">
                <div class="card-body">

                    <h2 class="mb-4 text-center">Create Account</h2>

                    <c:if test="${not empty duplicateEmailError}">
                        <div class="alert alert-danger text-center">
                            <c:out value="${duplicateEmailError}"/>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register"
                          method="post">

                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}">

                        <div class="mb-3">
                            <label for="name" class="form-label">
                                Full Name
                            </label>

                            <input type="text"
                                   id="name"
                                   name="name"
                                   class="form-control"
                                   placeholder="Enter your full name"
                                   value="<c:out value='${user.name}'/>"
                                   >

                           <c:if test="${result.hasFieldErrors('name')}">
							        <span class="text-danger field-error">
							            ${result.getFieldError('name').defaultMessage}
							        </span>
							    </c:if>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">
                                Email
                            </label>

                            <input type="email"
                                   id="email"
                                   name="email"
                                   class="form-control"
                                   placeholder="Enter your email"
                                   value="<c:out value='${user.email}'/>"
                                   >

                            <c:if test="${result.hasFieldErrors('email')}">
							        <span class="text-danger field-error">
							            ${result.getFieldError('email').defaultMessage}
							        </span>
							    </c:if>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">
                                Password
                            </label>

                            <input type="password"
                                   id="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="Enter your password"
                                   autocomplete="new-password"
                                   >

                            <c:if test="${result.hasFieldErrors('password')}">
							        <span class="text-danger field-error">
							            ${result.getFieldError('password').defaultMessage}
							        </span>
							    </c:if>
                        </div>

                        <div class="d-flex justify-content-center">
                            <button type="submit" class="btn btn-primary">
                                Register
                            </button>
                        </div>

                    </form>

                    <hr>

                    <p class="text-center mb-0">
                        Already have an account?
                        <a href="${pageContext.request.contextPath}/login">
                            Login here
                        </a>
                    </p>

                </div>
            </div>

        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>
		$(document).ready(function() {
		
		    $('input ').on('input', function() {
		        $(this).closest('.mb-3').find('.field-error').remove();
		    });
		
		   
		
		});
		
		</script>

</body>
</html>