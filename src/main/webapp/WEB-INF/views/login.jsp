<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/login">
            Event Booking System
        </a>
    </div>
</nav>

<div class="container mt-5">

    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow">
                <div class="card-body">

                    <h2 class="mb-4 text-center">Login</h2>

                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger text-center">
                            Invalid email or password.
                        </div>
                    </c:if>

                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success text-center">
                            You have logged out successfully.
                        </div>
                    </c:if>

                    <c:if test="${param.registered != null}">
                        <div class="alert alert-success text-center">
                            Registration successful. Please login.
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login"
                          method="post">

                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}">

                        <div class="mb-3">
                            <label for="email" class="form-label">
                                Email
                            </label>

                            <input type="email"
                                   id="email"
                                   name="email"
                                   class="form-control"
                                   placeholder="Enter your email"
                                   autocomplete="username"
                                   required>
                                   
                              <div class="invalid-feedback">
                              Please enter a valid email address.
                              
                              </div>     
                                   
                                   
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
                                   autocomplete="current-password"
                                   required>
                                   
                                   <div class="invalid-feedback">
                              Please enter a valid password.
                              
                              </div>
                                   
                        </div>

                        <div class="d-flex justify-content-center">
                            <button type="submit" class="btn btn-primary">
                                Login
                            </button>
                        </div>

                    </form>

                    <hr>

                    <p class="text-center mb-0">
                        Don't have an account?
                        <a href="${pageContext.request.contextPath}/register">
                            Register here
                        </a>
                    </p>

                </div>
            </div>

        </div>
    </div>

</div>

</body>
</html>