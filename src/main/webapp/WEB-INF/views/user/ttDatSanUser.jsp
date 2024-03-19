<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
prefix="fmt"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<main>
  <div class="container-fluid px-4">
    <h1 class="mt-4">Thông Tin Đặt Sân</h1>
    <ol class="breadcrumb mb-4">
      <li class="breadcrumb-item">
        <a class="text-decoration-none text-black fw-bolder" href="${ contextPath }/home">Trang Chủ</a>
      </li>
      <li class="breadcrumb-item active">Thông tin đặt sân</li>
    </ol>
    <div class="card mb-4"></div>
    <div class="card mb-4">
      <div class="card-header"><i class="fas fa-table me-1"></i> Thông tin đặt sân của khách hàng</div>
      <div class="card-body">
        <table id="datatablesSimple">
          <thead>
            <tr>
              <th>Tên Sân</th>
              <th>Email</th>
              <th>SĐT</th>
              <th>Ngày Đá</th>
              <th>Tổng Tiền</th>
              <th>Chi Tiết</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="tt" items="${ ttdatSan }">
              <tr>
                <td>${tt.san.tenSan }</td>
                <td>${tt.user == null ? tt.email : tt.user.email }</td>
                <td>${tt.user == null ? tt.phoneNumber : tt.user.phoneNumber }</td>
                <td>${tt.ngayDat }</td>
                <td>
                  <fmt:formatNumber
                    type="currency"
                    value="${ tt.tongTien }"
                    currencyCode="VND"
                    maxFractionDigits="0" />
                </td>
                <td>
                	<a class="link-success link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover"
                    href="#" data-bs-toggle="modal" data-bs-target="#staticBackdrop${ tt.id }">Xem Chi Tiết</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</main>

<!-- Modal -->
<c:forEach var="tt" items="${ ttdatSan }">
  <div
    class="modal fade"
    id="staticBackdrop${ tt.id }"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel${ tt.id }"
    aria-hidden="true">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5 fw-semibold" id="exampleModalLabel">Thông Tin Chi Tiết</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form class="form-control">
           <input type="hidden" name="id" value="${ tt.id }">
          <div class="modal-body">
          <div class="row">
          	<div class="col-lg-5 mb-3">
          		<div class="card">
          			<div class="card-body">
          				<h3 class="card-title mb-0 fw-semibold">
          					<a class="text-decoration-none text-black"
          					href="${ contextPath }/san-bong-da/${ tt.san.maSan }"
          					>${tt.san.tenSan }</a>
          				</h3>
          			</div>
          			<img class="card-img-bottom img-fluid" src="/imgs/${ tt.san.listHinhAnh.get(0).tenAnh }">
          		</div>
          	</div>
          	<div class="col-lg-7">
          		<div class="row mb-3">
              <div class="col-md-6">
                <div class="form-floating mb-3 mb-md-0">
                  <input	
                    class="form-control"
                    id="username"
                    type="text"
                    readonly
                    name="username"
                    placeholder=""
                    value="${ tt.user.username }" />
                  <label for="username">Tài khoản</label>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-floating">
                  <input
                    class="form-control"
                    id="email"
                    type="email"
                    readonly
                    name="email"
                    placeholder=""
                    value="${ tt.user.email }" />
                  <label for="email">Email</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="col-md-6">
                <div class="form-floating">
                  <input
                    class="form-control"
                    id="fullname"
                    type="text"
                    readonly
                    name="fullname"
                    placeholder=""
                    value="${ tt.user.lastName } ${ tt.user.firstName }" />
                  <label for="fullname">Họ Tên</label>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-floating">
                  <input
                    class="form-control"
                    id="phonenumber"
                    type="text"
                    readonly
                    name="phoneNumber"
                    placeholder=""
                    value="${ tt.user.phoneNumber }" />
                  <label for="phonenumber">Số Điện Thoại</label>
                </div>
              </div>
            </div>
             <div class="row mb-3">
            <div class="col">
              <div class="form-floating">
                <input
                  class="form-control"
                  id="inputEmail"
                  type="date"
                  name="ngayDa"
                  placeholder="name@example.com"
                  value="${ tt.ngayDat }" />
                <label for="inputEmail">Ngày Đá</label>
              </div>
            </div>
          </div>
            <div class="row">
            <div class="col-md-6">
              <div class="form-floating mb-3">
                <input
                  class="form-control"
                  id="ThoiGianBatDau"
                  name="ThoiGianBatDau"
                  type="time" readonly="readonly"
                  placeholder=""
                  value="${ tt.thoiGianNhanSan }" />
                <label for="ThoiGianBatDau">Thời Gian Bắt Đầu</label>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-floating">
                <input
                  class="form-control"
                  id="ThoiGianKetThuc"
                  name="ThoiGianKetThuc"
                  type="time" readonly="readonly"
                  placeholder=""
                  value="${ tt.thoiGianKetThuc }" />
                <label for="ThoiGianKetThuc">Thời Gian Kết Thúc</label>
              </div>
            </div>
          </div>
            <div class="row mb-3">
              <div class="col">
                <div class="form-floating">
                  <input
                    class="form-control"
                    id="tongTien"
                    type="text"
                    readonly
                    name="tongTien"
                    placeholder=""
                    value="${ tt.tongTien }" />
                  <label for="tongTien">Tổng Tiền</label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="daThanhToan"
                    type="text"
                    readonly
                    name="tienDaThanhToan"
                    placeholder=""
                    value="${ tt.tienCoc }" />
                  <label for="daThanhToan">Số Tiền Đã Thanh Toán</label>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-floating">
                  <input
                    class="form-control"
                    id="conLai"
                    type="text"
                    readonly
                    name="tienConLai"
                    placeholder=""
                    value="${ tt.tongTien - tt.tienCoc }" />
                  <label for="conLai">Số Tiền Còn Lại</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="col form-floating">
                <div class="form-floating">
                  <%-- <textarea class="form-control" id="ghiChu" name="ghiChu" readonly placeholder="">
                  ${tt.ghiChu == null ? 'Không có' : tt.ghiChu}
                  </textarea> --%>
                  <input
                    class="form-control"
                    id="ghiChu"
                    type="text"
                    readonly
                    name="tongTien"
                    placeholder=""
                    value="${ tt.ghiChu == null ? 'Không có' : tt.ghiChu }" />
					<label for="ghiChu">Ghi Chú</label>
                </div>
              </div>
            </div>
          	</div>
          </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Quay Về</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</c:forEach>
