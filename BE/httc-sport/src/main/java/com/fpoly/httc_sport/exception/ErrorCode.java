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
	INVALID_KEY(1001, "Thông tin đăng nhập không chính xác", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1002, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
	USER_ENABLED(1003, "Tài khoản đã được xác thực", HttpStatus.NOT_ACCEPTABLE),
	VERIFICATION_TOKEN_EXPIRED(1004, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	VERIFICATION_TOKEN_NOT_FOUND(1005, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	FORGOT_PASSWORD_TOKEN_EXPIRED(1006, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	FORGOT_PASSWORD_TOKEN_NOT_FOUND(1007, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	USERNAME_INVALID(1008, "Tên đăng nhập yêu cầu tối thiểu 6 ký tự", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(1009, "Mật khẩu yêu cầu tối thiểu 5 ký tự", HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(1010, "Tài khoản hoặc mật khẩu không chính xác", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1011, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1012, "Không đủ quyền hạn để thực hiện hành động này", HttpStatus.FORBIDDEN),
	PERMISSION_NOT_EXISTED(1013, "Không tìm thấy", HttpStatus.NOT_FOUND),
	ROLE_NOT_EXISTED(1014, "Không tìm thấy", HttpStatus.NOT_FOUND),
	WRONG_PASSWORD(1015, "Mật khẩu không chính xác", HttpStatus.NOT_ACCEPTABLE),
	WRONG_CONFIRMATION_PASSWORD(1016, "Mật khẩu xác nhận không chính xác", HttpStatus.NOT_ACCEPTABLE),
	EXISTED(1015, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
	TOKEN_EXPIRED(1100, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", HttpStatus.GONE);
	
	int code;
	String message;
	HttpStatusCode statusCode;
}
