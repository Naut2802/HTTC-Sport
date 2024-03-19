<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<main>
	<div class="container-fluid px-4">
		<h1 class="mt-4">Điều Chỉnh Sân</h1>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item"><a class="text-decoration-none text-black fw-bolder"
			 href="${ contextPath }/home">Trang
					Chủ</a></li>
			<li class="breadcrumb-item active">Điều Chỉnh Sân</li>
		</ol>
		<div class="row mb-4">
			<div class="col-lg-6">
				<div class="card shadow-lg border-3 rounded-lg mt-2 mb-3"
					style="height: 95.7%;">
					<div class="card-header">
						<h5 class="text-center font-weight-light">Hình Ảnh Sân</h5>
					</div>
					<div class="card-body">
						<div class="col">
							<div class="row" id="imgs">
								<c:set var="index" scope="request" value="0" />
								<c:forEach var="image" items="${ listHinhAnh }">
									<div class="col-6 w-50 my-3" style="position: relative;">
										</a> <img src="/imgs/${ image.tenAnh }" class="img-fluid rounded"
											alt="" style="width: 500px; height: 170px;">
									</div>
									<c:set var="index" scope="request" value="${ index + 1 }" />
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-6 bg-success-subtle mb-3">
				<div class="card shadow-lg border-3 rounded-lg mt-2">
					<div class="card-header">
						<h5 class="text-center font-weight-light">Điều Chỉnh Sân Bóng</h5>
					</div>
					<div class="card-body">
						<form action="${ contextPath }/admin/san" method="post"
							enctype="multipart/form-data">
							<div class="row mb-3">
								<input class="form-control" name="maSan" type="hidden"
									value="${ san.maSan }" />
								<div class="col">
									<div class="form-floating mt-3">
										<select class="form-select" id="loaiSan" name="loaiSan">
											<c:forEach var="loaiSan" items="${ listLoaiSan }">
												<option value="${ loaiSan.maLoai }"
													<c:if test="${ san.loaiSan.tenLoai == loaiSan.tenLoai }">selected="selected"</c:if>>${ loaiSan.tenLoai }</option>
											</c:forEach>
										</select> <label for="loaiSan">Loại sân</label>
									</div>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col">
									<div class="form-floating mt-3">
										<input class="form-control" name="tenSan" id="tenSan"
											type="text" value="${ san.tenSan }" required
											requiredmsg="Vui lòng Nhập Tên Sân" /> <label for="tenSan">Tên
											Sân</label>
									</div>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col">
									<div class="form-floating mt-3">
										<input class="form-control" name="giaSan" id="giaSan"
											type="number" value="${ san.giaSan }" min="0" max="999999"
											required requiredmsg="Giá Sân Không Hợp Lệ hoặc Đang Trống" />
										<label for="giaSan">Giá Sân</label>
									</div>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col">
									<label class="form-label">Trạng thái: </label>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="trangThaiSan" value="true"
											${ san.trangThaiSan || san == null ? 'checked' : '' }> <label
											class="form-check-label" for="inlineRadio1">Hoạt Động</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="trangThaiSan" value="false"
											${ san.trangThaiSan || san == null ? '' : 'checked' }> <label
											class="form-check-label" for="inlineRadio2">Ngưng Hoạt Động</label>
									</div>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col">
									<div class="form-floating mt-2">
										<input class="form-control" name="imgs" type="file"
											id="imgInput" multiple="multiple"> <label
											for="anhSan">Ảnh Sân</label>
									</div>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col">
									<div class="form-floating mt-2">
										<input class="form-control" name="diaChi" type="text"
											id="diaChi" multiple="multiple" required
											requiredmsg="Vui Lòng Nhập Địa Chỉ" value="${ san.diaChi }"> <label
											for=""diaChi"">Địa Chỉ</label>
									</div>
								</div>
							</div>
							<div class="form-floating mt-2" style="height: 100%;">
								<textarea class="form-control h-100" name="moTa">${ san.moTa }</textarea>
								<label for="anhSan">Mô Tả</label>
							</div>
							<div class="mt-3 mb-4 form-inline">
								<button formaction="${ contextPath }/admin/san/create"
									class="btn btn-outline-primary ${ isEdit ? 'd-none' : '' }">Thêm</button>
								<button formaction="${ contextPath }/admin/san/update"
									class="btn btn-outline-info ${ isEdit ? '' : 'd-none' }">Cập Nhật</button>
								<a href="${ contextPath }/admin/san/reset"
									class="btn btn-outline-dark ${ isEdit ? 'd-none' : '' }">Làm Mới</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="card mb-4">
			<div class="card-header">
				<i class="fas fa-table me-1"></i> Danh sách sân bóng
			</div>
			<div class="card-body">
				<table id="datatablesSimple">
					<thead>
						<tr>
							<th>Mã Sân</th>
							<th>Loại Sân</th>
							<th>Tên Sân</th>
							<th>Giá Sân</th>
							<th>Mô Tả</th>
							<th>Trạng thái</th>
							<th>Khác</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="san" items="${ listSan }">
							<tr>
								<td>${ san.maSan }</td>
								<td>${ san.loaiSan.tenLoai }</td>
								<td>${ san.tenSan }</td>
								<td><fmt:formatNumber type="currency" value="${ san.giaSan }" currencyCode="VND" maxFractionDigits="0" /></td>
								<td>${ san.moTa }</td>
								<td>${ san.trangThaiSan ? 'Hoạt Động' : 'Ngưng Hoạt Động' }</td>
								<td><a
									href="${ contextPath }/admin/san/edit/${ san.maSan }">Chỉnh Sửa</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</main>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var elements = $("input, select");
    for (var i = 0; i < elements.length; i++) {
        elements[i].oninvalid = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                 e.target.setCustomValidity(e.target.getAttribute("requiredmsg"));
            }
        };
        elements[i].oninput = function(e) {
            e.target.setCustomValidity("");
        };
    }
})

document.getElementById('imgInput').addEventListener('change', function (event) {
	var imgs = document.getElementById('imgs');
	while (imgs.hasChildNodes()) {
	  imgs.removeChild(imgs.firstChild);
	}
	
	if(event.target.files && event.target.files.length > 0) {
		for(var i = 0; i < event.target.files.length; i++) {
			var file = event.target.files[i];
			var imageURL = URL.createObjectURL(file);
		
			// Tạo một div element
			var div = document.createElement('div');
			
			// Thiết lập các thuộc tính cho div element
			div.className = 'col-6 w-50 my-3';
			div.style.position = 'relative';
			
			// Tạo một image element
			var image = document.createElement('img');
			image.className = 'img-fluid rounded';
	        image.src = imageURL;
			image.alt = '';
			image.style.width = '500px';
			image.style.height = '170px';
			
			// Thêm image vào div
			div.appendChild(image);
			
			console.log(div)
            imgs.appendChild(div)
		}
	}
})
</script>