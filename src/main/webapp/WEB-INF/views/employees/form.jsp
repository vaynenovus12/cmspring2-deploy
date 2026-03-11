<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="mb-0">
            <c:choose>
                <c:when test="${employee.employeeId == null}">
                    <i class="bi bi-person-plus"></i> Add New Employee
                </c:when>
                <c:otherwise>
                    <i class="bi bi-pencil-square"></i> Edit Employee: ${employee.name}
                </c:otherwise>
            </c:choose>
        </h2>
        <small class="text-muted">Manage corporate staff details and system access</small>
    </div>
    <a href="${pageContext.request.contextPath}/employees" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Back to List
    </a>
</div>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/employees/save" method="post">
                    
                    <input type="hidden" name="employeeId" value="${employee.employeeId}" />

                    <div class="mb-3">
                        <label class="form-label fw-bold">Full Name</label>
                        <input type="text" 
                               name="name" 
                               class="form-control" 
                               value="${employee.name}" 
                               placeholder="e.g. John Doe"
                               required />
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Email Address</label>
                        <input type="email" 
                               name="email" 
                               class="form-control" 
                               value="${employee.email}" 
                               placeholder="john.doe@company.com"
                               required />
                        <c:if test="${employee.employeeId == null}">
                            <div class="form-text text-info">
                                <i class="bi bi-info-circle"></i> A system user account will be created automatically using this email.
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${employee.employeeId == null}">
                        <div class="alert alert-light border small">
                            <strong>Note:</strong> New employees are assigned a default password: 
                            <code class="text-danger">ChangeMe@123</code>
                        </div>
                    </c:if>

                    <hr class="my-4">

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary px-4">
                            <i class="bi bi-check-lg"></i> Save Employee
                        </button>
                        <a href="${pageContext.request.contextPath}/employees" class="btn btn-light px-4">
                            Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>