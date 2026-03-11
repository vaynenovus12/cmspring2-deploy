<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<div class="d-flex justify-content-between align-items-center mb-3">
	<div>
		<h3 class="mb-0">Tickets</h3>
		<small class="text-muted">Manage support tickets</small>
	</div>
	<a class="btn btn-primary"
		href="${pageContext.request.contextPath}/tickets/new"> + Add
		Ticket </a>
</div>
<div class="card shadow-sm mb-3">
	<div class="card-body">
		<form class="row g-2 align-items-center" method="get"
			action="${pageContext.request.contextPath}/tickets">
			<div class="col-sm-8 col-md-6 col-lg-5">
				<input type="text" class="form-control" name="q" value="${q}"
					placeholder="Search ticket..." />
			</div>
			<div class="col-auto">
				<button type="submit" class="btn btn-outline-primary">
					Search</button>
			</div>
			<div class="col-auto">
				<a class="btn btn-outline-secondary"
					href="${pageContext.request.contextPath}/tickets"> Reset </a>
			</div>
		</form>
	</div>
</div>
<div class="card shadow-sm">
	<div class="card-body p-0">
		<div class="table-responsive">
			<table class="table table-striped table-hover mb-0 align-middle">
				<thead class="table-light">
					<tr>
						<th style="width: 120px;">Ticket No</th>
						<th>Subject</th>
						<th style="width: 220px;">Customer</th>
						<th style="width: 180px;">Assigned To</th>
						<th style="width: 140px;">Status</th>
						<th style="width: 180px;">Created</th>
						<th style="width: 170px;">Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tickets}" var="t">
						<tr>
							<td class="fw-semibold">${t.ticketNo}</td>
							<td>${t.subject}</td>
							<!-- Navigation property -->
							<td>${t.customer.name}</td>
							<td><c:choose>
									<c:when test="${not empty t.assignedEmployee}">
										<span class="text-dark">${t.assignedEmployee.name}</span>
									</c:when>
									<c:otherwise>
										<span class="text-muted italic">Unassigned</span>
									</c:otherwise>
								</c:choose></td>
							<td><span class="badge bg-secondary"> ${t.status} </span></td>
							<td>${t.createdAt}</td>
							<td><a class="btn btn-sm btn-outline-primary"
								href="${pageContext.request.contextPath}/tickets/edit/${t.ticketId}">
									Edit </a> <a class="btn btn-sm btn-outline-danger ms-1"
								href="${pageContext.request.contextPath}/tickets/delete/${t.ticketId}"
								onclick="return confirm('Delete this ticket?')"> Delete </a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty tickets}">
						<tr>
							<td colspan="6" class="text-center p-4 text-muted">No
								tickets found.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>
