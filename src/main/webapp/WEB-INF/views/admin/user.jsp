<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<main>
	<div class="container-fluid px-4">
		<h1 class="mt-4">Danh Sách Khách Hàng</h1>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item"><a class="text-decoration-none text-black fw-bolder" 
			href="${ contextPath }/admin">Trang Chủ</a></li>
			<li class="breadcrumb-item active">Danh Sách Khách Hàng</li>
		</ol>
		<div class="card mb-4">
			<div class="card-header">
				<i class="fas fa-table me-1"></i> Danh Sách Khách Hàng
			</div>
			<div class="card-body">
				<table id="datatablesSimple">
					<thead>
						<tr>
							<th>Email</th>
							<th>Username</th>
                            <th>First Name</th>
                            <th>Last Name</th>
							<th>Phone</th>
							<th>VIP</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>Email</th>
							<th>Username</th>
                            <th>First Name</th>
                            <th>Last Name</th>
							<th>Phone</th>
							<th>VIP</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="user" items="${ listUser }">
							<tr>
								<th>${ user.email }</th>
								<th>${ user.username }</th>
								<th>${ user.firstName }</th>
								<th>${ user.lastName }</th>
								<th>${ user.phoneNumber }</th>
								<th>${ user.vip.bacVip }</th>
								<th>${ user.status ? 'Active' : 'InActive' }</th>
								<th><a
									href="${ contextPath }/admin/user/delete/${ user.email }">Delete</a></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</main>