package sampm.uz.library_system.utils;

public class Constants {

    public final static String AUTH = "/api/auth";
    public static  final String REGISTER_USER = "/register_user";
    public static  final String REGISTER_STUDENT = "/register_student";
    public static  final String VERIFY_STUDENT = "/verify_student";
    public static  final String LOGIN = "/login";
    public static final String ADD_STUDENT = "/add_student";
    public static final String GET_STUDENT = "/get_student/{id}";
    public static final String GET_ALL_STUDENT = "/get_all_student";
    public static final String GET_ALL_NON_EXIST_STUDENT = "/get_all_non_exist_student";
    public static final String UPDATE_STUDENT = "/update_student/{studentId}";
    public static final String GRADUATE_STUDENT = "/graduate_student/{studentId}";
    public static final String DELETE_STUDENT = "/delete_student/{id}";
    public static final String BOOK_TO_STUDENT = "/book_to_student/{bookId}/{studentId}";





    public static final String GET_BOOK = "/get_book/{id}";
    public static final String SEARCH = "/search";
    public static final String GET_ALL_BOOK = "/get_all_book";
    public static final String UPDATE_BOOK = "/update_book/{id}";
    public static final String DELETE_BOOK = "/delete_book/{id}";
    public static final String ADD_BOOK = "/add_book";
    public static final String GET_SOME_BOOKS = "/getSomeBooks/{bookId}/{studentId}";
    public static final String INCREASE_BOOK = "/increase_book/{bookId}";
    public static final String DECREASE_BOOK = "/decrease_book/{bookId}";
    public static final String GET_ALL_DELETED_BOOK= "/get_all_deleted_book";
    public static final String GET_ALL_AVAILABLE_BOOK= "/get_all_available_book";
    public static final String GET_ALL_NOT_AVAILABLE_BOOK= "/get_all_not_available_book";
    public static final String RETURN_BOOK = "/return_book/{studentId}/{bookId}";

}
