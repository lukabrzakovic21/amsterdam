package com.master.amsterdam.model;

public class PathPattern {

    public static String REGISTER = "/register";
    public static String REGISTER_PATH_VARAIBLE = "/register/*";
    public static String CREATE_REGISTRATION_REQUEST = "/register/create";

    public static String USER = "/user";
    public static String USER_PATH_VARIABLE = "/user/*";
    public static String USER_ACTIVATE = "/user/activate/*";
    public static String USER_DEACTIVATE = "/user/deactivate/*";
    public static String USER_ROLE = "/user/role/*";
    public static String USER_PASSWORD = "/user/password/*";

    public static String INVOICE_CREATE = "/invoice/create";
    public static String INVOICE_PATH_VARIABLE = "/invoice/*";
    public static String INVOICE_WITHDRAW = "/invoice/withdraw/*";
    public static String INVOICE_INCREASE = "/invoice/increase/*";

    public static String ITEM_CREATE = "/item/create";
    public static String ITEM = "/item";
    public static String ITEM_PATH_VARIABLE = "/item/*";
    public static String REMOVE_TEMP_PATH_VARIABLE = "/item/temp/*";
    public static String REMOVE_PERM_PATH_VARIABLE = "/item/perm/*";
    public static String BUY_ITEM_PATH_VARIABLE = "/item/buy/*";
    public static String ITEM_HISTORY_PATH_VARIABLE = "/item/history/*";
    public static String ITEM_INTEREST_PATH_VARIABLE = "/item/interest/*";




    public static String AUTH = "/auth";
    public static String LOGOUT = "/auth/logout";


}
