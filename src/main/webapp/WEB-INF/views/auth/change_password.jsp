<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="row justify-content-center">
   <div class="col-md-7 col-lg-5">
       <div class="card shadow-sm mt-4">
           <div class="card-body">
               <h4 class="mb-1">Change Password</h4>
               <div class="text-muted mb-3">
                   You must change your password before continuing.
               </div>
               <c:if test="${not empty error}">
                   <div class="alert alert-danger">${error}</div>
               </c:if>
               <form method="post" action="${pageContext.request.contextPath}/change-password" class="row g-3">
                   <div class="col-12">
                       <label class="form-label">Current Password</label>
                       <input type="password" name="currentPassword" class="form-control" required />
                   </div>
                   <div class="col-12">
                       <label class="form-label">New Password</label>
                       <input type="password" name="newPassword" class="form-control" required />
                       <div class="form-text">Minimum 8 characters</div>
                   </div>
                   <div class="col-12">
                       <label class="form-label">Confirm New Password</label>
                       <input type="password" name="confirmPassword" class="form-control" required />
                   </div>
                   <div class="col-12 d-grid">
                       <button type="submit" class="btn btn-primary">Update Password</button>
                   </div>
               </form>
           </div>
       </div>
   </div>
</div>

