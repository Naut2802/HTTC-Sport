package com.fpoly.httc_sport.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_KEY(1001, "Thông tin không được để trống", HttpStatus.BAD_REQUEST),
	USERNAME_NULL(1002, "Tên tài khoản không được để trống", HttpStatus.BAD_REQUEST),
	PASSWORD_NULL(1003, "Mật khẩu không được để trống", HttpStatus.BAD_REQUEST),
	NEW_PASSWORD_NULL(1003, "Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST),
	CONFIRMATION_PASSWORD_NULL(1003, "Mật khẩu xác nhận không được để trống", HttpStatus.BAD_REQUEST),
	EMAIL_NULL(1004, "Email không được để trống", HttpStatus.BAD_REQUEST),
	EMAIL_INVALID(1005, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1006, "Tên đăng nhập yêu cầu tối thiểu {min} ký tự", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1007, "Mật khẩu yêu cầu tối thiểu {min} ký tự", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1008, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
	EMAIL_EXISTED(1009, "Email đã đuược sử dụng", HttpStatus.BAD_REQUEST),
	USER_ENABLED(1010, "Tài khoản đã được xác thực", HttpStatus.NOT_ACCEPTABLE),
	USER_NOT_EXISTED(1011, "Tài khoản hoặc mật khẩu không chính xác", HttpStatus.NOT_FOUND),
	VERIFICATION_TOKEN_EXPIRED(1012, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	VERIFICATION_TOKEN_NOT_FOUND(1013, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	FORGOT_PASSWORD_TOKEN_EXPIRED(1014, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	FORGOT_PASSWORD_TOKEN_NOT_FOUND(1015, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	WRONG_PASSWORD(1016, "Mật khẩu không chính xác", HttpStatus.NOT_ACCEPTABLE),
	WRONG_CONFIRMATION_PASSWORD(1017, "Mật khẩu xác nhận không chính xác", HttpStatus.NOT_ACCEPTABLE),
	ROLE_NOT_EXISTED(1018, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
	PERMISSION_NOT_EXISTED(1019, "Không tìm thấy quyền hạn", HttpStatus.NOT_FOUND),
	PITCH_NAME_NULL(1020, "Tên sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_ADDRESS_NULL(1021, "Địa chỉ sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NULL(1022, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NAME_NULL(1023, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TOTAL_NULL(1024, "Tổng sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_PRICE_NULL(1025, "Giá sân yêu cầu tối thiểu 30000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_MIN_PRICE(1026, "Giá sân yêu cầu tối thiểu 300000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_EXISTED(1027, "Đã có sân ở địa chỉ này", HttpStatus.BAD_REQUEST),
	PITCH_NOT_EXISTED(1028, "Sân không tồn tại", HttpStatus.NOT_FOUND),
	
	
	NOT_EXISTED(1096, "Đối tượng không tồn tại", HttpStatus.NOT_FOUND),
	EXISTED(1097, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(1098, "Vui lòng đăng nhập", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1099, "Không đủ quyền hạn để thực hiện hành động này", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED(1100, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", HttpStatus.GONE);
	
	int code;
	String message;
	HttpStatusCode statusCode;
}
