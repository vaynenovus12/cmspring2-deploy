<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-people-fill"></i> Customers</h2>
    <a href="${pageContext.request.contextPath}/customers/new" class="btn btn-primary btn-sm">
        <i class="bi bi-plus-lg"></i> Add New Customer
    </a>
</div>

<div class="card shadow-sm mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/customers" class="row g-2">
            <div class="col-auto">
                <input type="text" name="q" value="${q}" class="form-control" placeholder="Search customer name..." />
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-secondary">Search</button>
                <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-secondary">Reset</a>
            </div>
        </form>
    </div>
</div>

<div class="table-responsive">
    <table class="table table-striped table-hover align-middle border">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th class="text-center">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${customers}" var="c">
                <tr>
                    <td>${c.customerId}</td>
                    <td class="fw-bold">${c.name}</td>
                    <td>${c.email}</td>
                    <td>${c.phone}</td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/customers/edit/${c.customerId}" class="btn btn-sm btn-outline-primary me-1">
                             Edit
                        </a>
                        <a href="${pageContext.request.contextPath}/customers/delete/${c.customerId}" 
                           class="btn btn-sm btn-outline-danger" 
                           onclick="return confirm('Delete this customer?')">
                             Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty customers}">
                <tr>
                    <td colspan="5" class="text-center text-muted py-4">No customers found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>