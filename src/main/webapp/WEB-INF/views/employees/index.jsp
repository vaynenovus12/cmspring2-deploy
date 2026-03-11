<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-person-badge"></i> Employees</h2>
    <a href="${pageContext.request.contextPath}/employees/new" class="btn btn-success btn-sm">
        <i class="bi bi-plus-lg"></i> Add New Employee
    </a>
</div>

<div class="card shadow-sm mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/employees" class="row g-2">
            <div class="col-auto">
                <input type="text" name="q" value="${q}" class="form-control" placeholder="Search employee name..." />
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-secondary">Search</button>
                <a href="${pageContext.request.contextPath}/employees" class="btn btn-outline-secondary">Reset</a>
            </div>
        </form>
    </div>
</div>

<div class="table-responsive border rounded">
    <table class="table table-hover mb-0">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th class="text-center">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${employees}" var="e">
                <tr>
                    <td>${e.employeeId}</td>
                    <td>${e.name}</td>
                    <td><a href="mailto:${e.email}" class="text-decoration-none">${e.email}</a></td>
                    <td class="text-center">
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/employees/edit/${e.employeeId}" class="btn btn-sm btn-info text-white">Edit</a>
                            <a href="${pageContext.request.contextPath}/employees/delete/${e.employeeId}" 
                               class="btn btn-sm btn-danger" 
                               onclick="return confirm('Are you sure?')">Delete</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty employees}">
                <tr>
                    <td colspan="4" class="text-center py-4 text-muted">No employees found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>