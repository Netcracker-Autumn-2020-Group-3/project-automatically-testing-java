package ua.netcracker.group3.automaticallytesting.controller.Constant;

public interface SqlConstant {
//    String USER_TABLE = "\"user\"";
    String GET_EMAIL_BY_ID = "SELECT email FROM \\\"user\\\" WHERE user_id =";
    String GET_ALL_USER = "SELECT * FROM \"user\"";
    String GET_USER_BY_EMAIL = "SELECT * FROM \"user\" WHERE email = ?";
}
