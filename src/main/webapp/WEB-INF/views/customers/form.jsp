<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="d-flex justify-content-between align-items-center mb-3">
   <div>
       <h3 class="mb-0">
           <c:choose>
               <c:when test="${customer.customerId == null}">Add Customer</c:when>
               <c:otherwise>Edit Customer</c:otherwise>
           </c:choose>
       </h3>
       <small class="text-muted">Customer details</small>
   </div>
   <a class="btn btn-outline-secondary"
      href="${pageContext.request.contextPath}/customers">
       Back
   </a>
</div>
<div class="card shadow-sm">
   <div class="card-body">
       <form method="post"
             action="${pageContext.request.contextPath}/customers/save"
             class="row g-3">
           <!-- IMPORTANT: needed for EDIT to work -->
           <input type="hidden" name="customerId" value="${customer.customerId}" />
           <div class="col-md-6">
               <label class="form-label">Name</label>
               <input type="text"
                      class="form-control"
                      name="name"
                      value="${customer.name}"
                      required />
           </div>
           <div class="col-md-6">
               <label class="form-label">Email</label>
               <input type="email"
                      class="form-control"
                      name="email"
                      value="${customer.email}"
                      required />
           </div>
           <div class="col-md-6">
               <label class="form-label">Phone</label>
               <input type="text"
                      class="form-control"
                      name="phone"
                      value="${customer.phone}" />
           </div>
           <div class="col-12 d-flex gap-2">
               <button type="submit" class="btn btn-primary">
                   Save
               </button>
               <a class="btn btn-outline-secondary"
                  href="${pageContext.request.contextPath}/customers">
                   Cancel
               </a>
           </div>
       </form>
   </div>
</div>
