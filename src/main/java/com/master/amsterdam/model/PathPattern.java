package com.master.amsterdam.model;

import com.master.amsterdam.util.Role;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class PathPattern {

//    String DATABASE = "/database";
//    String DATABASE_PATH_VARIABLE = "/database/{id}";
//
//    String EMAIL = "/email";
//
//    String ENVIRONMENT = "/environment";
//    String ENVIRONMENT_PATH_VARIABLE = "/environment/{id}";
//
//
//    String DATABASE_BY_ENVIRONMENT = "/database/environment/{id}";
//    String ENVIRONMENT_BY_DATABASE = "/environment/database/{id}";
//
//    String DATABASE_ADD_TO_ENVS_PATH_VARIABLE = "/database/add-to-envs/{id}";
//    String DATABASE_REMOVE_FROM_ENVS_PATH_VARIABLE = "/database/remove-from-envs/{id}";
//
//    String ACCESS_DURATION = "/access-duration";
//    String ACCESS_DURATION_PATH_VARIABLE = "/access-duration/{id}";
//
//    String REQUEST = "/request/**";
//    String REQUEST_PATH_VARIABLE = "/request/{id}";
//    String REQUEST_CANCEL = "/request/cancel";
//    String REQUEST_CANCEL_PATH_VARIABLE = "/request/cancel/{id}";
//    String REQUEST_ENABLE_PATH_VARIABLE = "/request/enable/{id}";
//    String REQUEST_DISABLE_PATH_VARIABLE = "/request/disable/{id}";
//    String REQUEST_NUMBER = "/request/chart";
//    String REQUEST_CREDENTIAL = "/request/password/{id}";
//    String REQUEST_USER_ALL = "/request/user-all";
//
//    String USER = "/user/**";
//    String USER_PATH_VARIABLE = "/user/{id}";
//    String USER_DISABLE_PATH_VARIABLE = "/user/disable/{id}";
//    String USER_ROLE_PATH_VARIABLE = "/user/role/{id}";
//    String USER_ROLE = "/user/role";
//    String USER_SEARCH = "/user/search";

    public static String REGISTER = "/register";
    public static String REGISTER_PATH_VARAIBLE = "/register/*";

    public static String AUTH = "/auth";
    public static String LOGOUT = "/auth/logout";


}
