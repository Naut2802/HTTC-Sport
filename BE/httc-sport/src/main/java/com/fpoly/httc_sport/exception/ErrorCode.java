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
	USERNAME_INVALID_BLANK(1006, "Tên đăng nhập yêu cầu tối thiểu {min} ký tự và không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1006, "Tên đăng nhập yêu cầu không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID_BLANK(1007, "Mật khẩu yêu cầu thiểu {min} ký tự và không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1007, "Mật khẩu yêu cầu không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PHONE_NUMBER_INVALID(1008, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1009, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
	EMAIL_EXISTED(1010, "Email đã được sử dụng", HttpStatus.BAD_REQUEST),
	USER_ENABLED(1011, "Tài khoản đã được xác thực", HttpStatus.NOT_ACCEPTABLE),
	USER_NOT_EXISTED(1012, "Tài khoản hoặc mật khẩu không chính xác", HttpStatus.NOT_FOUND),
	EMAIL_NOT_EXISTED(1013, "Email không chính xác", HttpStatus.BAD_REQUEST),
	VERIFICATION_TOKEN_EXPIRED(1014, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	VERIFICATION_TOKEN_NOT_FOUND(1015, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	FORGOT_PASSWORD_TOKEN_EXPIRED(1016, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	FORGOT_PASSWORD_TOKEN_NOT_FOUND(1017, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	WRONG_PASSWORD(1018, "Mật khẩu không chính xác", HttpStatus.NOT_ACCEPTABLE),
	WRONG_CONFIRMATION_PASSWORD(1019, "Mật khẩu xác nhận không chính xác", HttpStatus.NOT_ACCEPTABLE),
	ROLE_NOT_EXISTED(1020, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
	PERMISSION_NOT_EXISTED(1021, "Không tìm thấy quyền hạn", HttpStatus.NOT_FOUND),
	PITCH_NAME_NULL(1022, "Tên sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_ADDRESS_NULL(1023, "Địa chỉ sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NULL(1024, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NAME_NULL(1025, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TOTAL_NULL(1026, "Tổng sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_PRICE_NULL(1027, "Giá sân yêu cầu tối thiểu 30000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_MIN_PRICE(1028, "Giá sân yêu cầu tối thiểu 300000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_EXISTED(1029, "Đã có sân ở địa chỉ này", HttpStatus.BAD_REQUEST),
	PITCH_NOT_EXISTED(1030, "Sân không tồn tại", HttpStatus.NOT_FOUND),
	RENT_INFO_EMAIL_NULL(1031, "Vui lòng nhập Email", HttpStatus.BAD_REQUEST),
	RENT_INFO_PHONE_NUMBER_NULL(1032, "Vui lòng nhập số điện thoại", HttpStatus.BAD_REQUEST),
	RENT_INFO_FIRST_NAME_NULL(1033, "Vui lòng nhập tên", HttpStatus.BAD_REQUEST),
	RENT_INFO_FIRST_NAME_INVALID(1033, "Tên không hợp lệ", HttpStatus.BAD_REQUEST),
	RENT_INFO_LAST_NAME_INVALID(1033, "Họ không hợp lệ", HttpStatus.BAD_REQUEST),
	RENT_INFO_RENT_DATE_NULL(1034, "Vui lòng chọn ngày đặt sân", HttpStatus.BAD_REQUEST),
	RENT_INFO_START_TIME_NULL(1035, "Vui lòng chọn thời gian đặt sân", HttpStatus.BAD_REQUEST),
	RENT_INFO_RENT_TIME_NULL(1036, "Vui lòng chọn thời gian muốn đặt", HttpStatus.BAD_REQUEST),
	RENT_INFO_TYPE_PITCH_NULL(1037, "Loại sân không được để trống", HttpStatus.BAD_REQUEST),
	RENT_INFO_PAYMENT_METHOD_NULL(1038, "Phương thức thanh toán không được để trống", HttpStatus.BAD_REQUEST),
	RENT_INFO_EXISTED(1039, "Sân đã được đặt", HttpStatus.BAD_REQUEST),
	RENT_INFO_NOT_EXISTED(1040, "Không tìm thấy thông tin đặt sân", HttpStatus.NOT_FOUND),
	PAYMENT_METHOD_NOT_EXISTED(1041, "Phương thức thanh toán không hợp lệ", HttpStatus.NOT_FOUND),
	PAYMENT_METHOD_INVALID(1041, "Vui lòng đăng nhập hoặc nhập đúng email đã đăng ký tài khoản để thanh toán thông qua ví", HttpStatus.BAD_REQUEST),
	PAYMENT_NOT_EXISTED(1042, "Thông tin thanh toán không hợp lệ", HttpStatus.NOT_FOUND),
	UNPAID(1043, "Thông tin đặt sân này chưa được thanh toán tiền cọc", HttpStatus.NOT_ACCEPTABLE),
	BILL_NOT_EXISTED(1044, "Không tìm thấy hóa đơn", HttpStatus.NOT_FOUND),
	BILL_EXISTED(1045, "Hóa đơn đã tồn tại", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_NULL(1046, "Đánh giá sao không được trống", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_MIN(1047, "Đánh giá thấp nhất 1 sao", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_MAX(1048, "Đánh giá cao nhất 5 sao", HttpStatus.BAD_REQUEST),
	REVIEW_NOT_EXISTED(1049, "Không tìm thấy nhận xét", HttpStatus.NOT_FOUND),
	REVIEW_EXISTED(1050, "Đã gửi đánh giá hóa đơn này", HttpStatus.BAD_REQUEST),
	VIP_NOT_EXISTED(1051, "Không tìm thấy bậc vip này", HttpStatus.NOT_FOUND),
	WALLET_NOT_ENOUGH(1052, "Số dư trong ví không đủ để thanh toán", HttpStatus.BAD_REQUEST),
	
	
	NOT_EXISTED(1096, "Đối tượng không tồn tại", HttpStatus.NOT_FOUND),
	EXISTED(1097, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(1098, "Vui lòng đăng nhập", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1099, "Không đủ quyền hạn để thực hiện hành động này", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED(1100, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", HttpStatus.GONE);
	
	int code;
	String message;
	HttpStatusCode statusCode;
}
