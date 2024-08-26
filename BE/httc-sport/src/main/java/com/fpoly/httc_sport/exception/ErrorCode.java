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
	PASSWORD_NULL(1002, "Mật khẩu không được để trống", HttpStatus.BAD_REQUEST),
	NEW_PASSWORD_NULL(1002, "Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST),
	CONFIRMATION_PASSWORD_NULL(1002, "Mật khẩu xác nhận không được để trống", HttpStatus.BAD_REQUEST),
	EMAIL_NULL(1002, "Email không được để trống", HttpStatus.BAD_REQUEST),
	EMAIL_INVALID(1002, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID_BLANK(1002, "Tên đăng nhập yêu cầu tối thiểu {min} ký tự và không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1002, "Tên đăng nhập yêu cầu không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID_BLANK(1002, "Mật khẩu yêu cầu thiểu {min} ký tự và không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1002, "Mật khẩu yêu cầu không được có khoảng trống hoặc ký tự đặc biệt", HttpStatus.BAD_REQUEST),
	PHONE_NUMBER_INVALID(1002, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1002, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
	EMAIL_EXISTED(1002, "Email đã được sử dụng", HttpStatus.BAD_REQUEST),
	USER_ENABLED(1002, "Tài khoản đã được xác thực", HttpStatus.NOT_ACCEPTABLE),
	USER_NOT_EXISTED(1002, "Tài khoản hoặc mật khẩu không chính xác", HttpStatus.NOT_FOUND),
	EMAIL_NOT_EXISTED(1002, "Email không chính xác", HttpStatus.BAD_REQUEST),
	VERIFICATION_TOKEN_EXPIRED(1002, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	VERIFICATION_TOKEN_NOT_FOUND(1002, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	FORGOT_PASSWORD_TOKEN_EXPIRED(1002, "Mã xác thực đã hết hạn", HttpStatus.NOT_ACCEPTABLE),
	FORGOT_PASSWORD_TOKEN_NOT_FOUND(1002, "Mã xác thực không tồn tại", HttpStatus.NOT_FOUND),
	WRONG_PASSWORD(1002, "Mật khẩu không chính xác", HttpStatus.NOT_ACCEPTABLE),
	WRONG_CONFIRMATION_PASSWORD(1002, "Mật khẩu xác nhận không chính xác", HttpStatus.NOT_ACCEPTABLE),
	ROLE_NOT_EXISTED(1002, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
	PERMISSION_NOT_EXISTED(1002, "Không tìm thấy quyền hạn", HttpStatus.NOT_FOUND),
	VIP_NOT_EXISTED(1002, "Không tìm thấy bậc vip này", HttpStatus.NOT_FOUND),
	PITCH_NAME_NULL(1003, "Tên sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_ADDRESS_NULL(1003, "Địa chỉ sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NULL(1003, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TYPE_NAME_NULL(1003, "Loại sân không được trống", HttpStatus.BAD_REQUEST),
	PITCH_TOTAL_NULL(1003, "Tổng sân không được để trống", HttpStatus.BAD_REQUEST),
	PITCH_PRICE_NULL(1003, "Giá sân không được để trống và yêu cầu tối thiểu 2.000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_MIN_PRICE(1003, "Giá sân yêu cầu tối thiểu 2.000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_MAX_PRICE(1003, "Giá sân quá cao, giá sân hợp lệ từ 2.000 VNĐ - 10.000.000 VNĐ", HttpStatus.BAD_REQUEST),
	PITCH_IMAGES_NULL(1003, "Không được để trống hình ảnh sân", HttpStatus.BAD_REQUEST),
	PITCH_IMAGES_SIZE(1003, "Sân bắt buộc tối thiểu 5 hình ảnh và tối đa 10 hình ảnh", HttpStatus.BAD_REQUEST),
	PITCH_EXISTED(1003, "Đã có sân ở địa chỉ này", HttpStatus.BAD_REQUEST),
	PITCH_NOT_EXISTED(1003, "Sân không tồn tại", HttpStatus.NOT_FOUND),
	RENT_INFO_EMAIL_NULL(1004, "Vui lòng nhập Email", HttpStatus.BAD_REQUEST),
	RENT_INFO_PHONE_NUMBER_NULL(1004, "Vui lòng nhập số điện thoại", HttpStatus.BAD_REQUEST),
	RENT_INFO_FIRST_NAME_NULL(1004, "Vui lòng nhập tên", HttpStatus.BAD_REQUEST),
	FIRST_NAME_INVALID(1004, "Tên không hợp lệ", HttpStatus.BAD_REQUEST),
	LAST_NAME_INVALID(1004, "Họ không hợp lệ", HttpStatus.BAD_REQUEST),
	RENT_INFO_RENT_DATE_NULL(1004, "Vui lòng chọn ngày đặt sân", HttpStatus.BAD_REQUEST),
	RENT_INFO_START_TIME_NULL(1004, "Vui lòng chọn thời gian đặt sân", HttpStatus.BAD_REQUEST),
	RENT_INFO_RENT_TIME_NULL(1004, "Vui lòng chọn thời gian muốn đặt", HttpStatus.BAD_REQUEST),
	RENT_INFO_TYPE_PITCH_NULL(1004, "Loại sân không được để trống", HttpStatus.BAD_REQUEST),
	RENT_INFO_PAYMENT_METHOD_NULL(1004, "Phương thức thanh toán không được để trống", HttpStatus.BAD_REQUEST),
	RENT_INFO_EXISTED(1004, "Sân đã được đặt", HttpStatus.BAD_REQUEST),
	RENT_INFO_EXCHANGED(1004, "Thông tin đặt sân đã được đổi thành hóa đơn, không thể cập nhật", HttpStatus.BAD_REQUEST),
	RENT_INFO_NOT_EXISTED(1004, "Không tìm thấy thông tin đặt sân", HttpStatus.NOT_FOUND),
	RENT_INFO_PAY_REMAINING_USER_NOT_FOUND(1004, "Không tìm thông tin tài khoản hợp lệ để thanh toán qua ví", HttpStatus.NOT_FOUND),
	UNPAID(1004, "Thông tin đặt sân này chưa được thanh toán tiền cọc", HttpStatus.NOT_ACCEPTABLE),
	PAID(1004, "Thông tin đặt sân này đã được thanh toán", HttpStatus.NOT_ACCEPTABLE),
	PAYMENT_METHOD_NOT_EXISTED(1005, "Phương thức thanh toán không hợp lệ", HttpStatus.NOT_FOUND),
	PAYMENT_METHOD_INVALID(1005, "Vui lòng đăng nhập hoặc nhập đúng email đã đăng ký tài khoản để thanh toán thông qua ví", HttpStatus.BAD_REQUEST),
	PAYMENT_NOT_EXISTED(1005, "Thông tin thanh toán không hợp lệ", HttpStatus.NOT_FOUND),
	PAYMENT_AMOUNT_MIN(1005, "Số tiền nạp vào ít nhất 2.000 VNĐ", HttpStatus.BAD_REQUEST),
	PAYMENT_AMOUNT_MAX(1005, "Số tiền nạp vào nhiều nhất 500.000 VNĐ", HttpStatus.BAD_REQUEST),
	BILL_NOT_EXISTED(1006, "Không tìm thấy hóa đơn", HttpStatus.NOT_FOUND),
	BILL_EXISTED(1006, "Hóa đơn đã tồn tại", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_NULL(1007, "Đánh giá sao không được trống", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_MIN(1007, "Đánh giá thấp nhất 1 sao", HttpStatus.BAD_REQUEST),
	REVIEW_RATE_MAX(1007, "Đánh giá cao nhất 5 sao", HttpStatus.BAD_REQUEST),
	REVIEW_NOT_EXISTED(1007, "Không tìm thấy nhận xét", HttpStatus.NOT_FOUND),
	REVIEW_EXISTED(1007, "Đã gửi đánh giá hóa đơn này", HttpStatus.BAD_REQUEST),
	WALLET_NOT_ENOUGH(1008, "Số dư trong ví không đủ để thanh toán", HttpStatus.BAD_REQUEST),
	WALLET_NOT_EXISTED(1008, "Mã ví không hợp lệ", HttpStatus.BAD_REQUEST),
	TRANSACTION_NOT_EXISTED(1009, "Không tìm thấy giao dịch", HttpStatus.BAD_REQUEST),
	CHAT_ROOM_NOT_EXIST(1010, "Không tìm thấy lịch sử tin nhắn này", HttpStatus.NOT_FOUND),
	
	
	NOT_EXISTED(1099, "Đối tượng không tồn tại", HttpStatus.NOT_FOUND),
	EXISTED(1099, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(1090, "Vui lòng đăng nhập", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1090, "Không đủ quyền hạn để thực hiện hành động này", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED(1090, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", HttpStatus.GONE);
	
	int code;
	String message;
	HttpStatusCode statusCode;
}
