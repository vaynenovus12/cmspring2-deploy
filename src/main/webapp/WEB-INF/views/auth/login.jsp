<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
   <title>Login - Support Ticketing</title>
   <!-- Bootstrap CDN (standalone page) -->
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
         rel="stylesheet">
   <style>
       body {
           background-color: #f4f6f9;
       }
       .login-card {
           margin-top: 120px;
       }
   </style>
</head>
<body>
<div class="container">
   <div class="row justify-content-center">
       <div class="col-md-5 col-lg-4">
           <div class="card shadow login-card">
               <div class="card-body">
                   <h4 class="mb-1 text-center">Support Ticketing 909090909090ddwd742389472389423</h4>
                   <div class="text-muted text-center mb-4">
                       Sign in to continue
                   </div>
                   <c:if test="${not empty error}">
                       <div class="alert alert-danger">
                           ${error}
                       </div>
                   </c:if>
                   <form method="post"
                         action="${pageContext.request.contextPath}/login"
                         class="row g-3">
                       <div class="col-12">
                           <label class="form-label">Username (Email)</label>
                           <input type="text"
                                  name="username"
                                  class="form-control"
                                  required />
                       </div>
                       <div class="col-12">
                           <label class="form-label">Password</label>
                           <input type="password"
                                  name="password"
                                  class="form-control"
                                  required />
                       </div>
                       <div class="col-12 d-grid">
                           <button type="submit"
                                   class="btn btn-primary">
                               Login
                           </button>
                       </div>
                       <div class="col-12 text-muted small text-center">
                           First time password: <b>ChangeMe@123</b>
                       </div>
                   </form>
               </div>
           </div>
       </div>
   </div>
</div>
</body>
</html>
