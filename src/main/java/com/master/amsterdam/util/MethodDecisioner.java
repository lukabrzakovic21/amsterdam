package com.master.amsterdam.util;

import com.master.amsterdam.model.PathPattern;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Component
public class MethodDecisioner {

    // for some role gives all methods with their types
    public static Map<Role, List<MethodPair>> allowedMethodsWithTypePerRole =new HashMap<>();

    public static class OpenEndpoints {

        public static String LOGIN = "/auth";
        public static String LOGOUT = "/auth/logout";
        public static String CREATE_REGISTRATION_REQUEST = "/register/create";

    }

    @PostConstruct
    public static void initMethodsWithRoles() {

        var listForCustomer = new ArrayList<MethodPair>();
        var listForVendor = new ArrayList<MethodPair>();
        var listForAdmin = new ArrayList<MethodPair>();

        listForAdmin.add(new MethodPair(PathPattern.REGISTER, "GET"));
        listForAdmin.add(new MethodPair(PathPattern.REGISTER_PATH_VARAIBLE, "GET"));
        listForAdmin.add(new MethodPair(PathPattern.REGISTER_PATH_VARAIBLE, "PATCH"));
        listForAdmin.add(new MethodPair(PathPattern.USER, "GET"));
        listForAdmin.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "GET"));
        listForAdmin.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "PUT"));
        listForAdmin.add(new MethodPair(PathPattern.USER_ACTIVATE, "PATCH"));
        listForAdmin.add(new MethodPair(PathPattern.USER_DEACTIVATE, "DELETE"));
        listForAdmin.add(new MethodPair(PathPattern.USER_ROLE, "PATCH"));
        listForAdmin.add(new MethodPair(PathPattern.USER_PASSWORD, "PATCH"));


        listForCustomer.add(new MethodPair(PathPattern.REGISTER_PATH_VARAIBLE, "GET"));
        listForCustomer.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "GET"));
        listForCustomer.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "PUT"));
        listForCustomer.add(new MethodPair(PathPattern.USER_PASSWORD, "PATCH"));


        listForVendor.add(new MethodPair(PathPattern.REGISTER_PATH_VARAIBLE, "GET"));
        listForVendor.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "GET"));
        listForVendor.add(new MethodPair(PathPattern.USER_PATH_VARIABLE, "PUT"));
        listForVendor.add(new MethodPair(PathPattern.USER_PASSWORD, "PATCH"));

        allowedMethodsWithTypePerRole.put(Role.CUSTOMER, listForCustomer);
        allowedMethodsWithTypePerRole.put(Role.VENDOR, listForVendor);
        allowedMethodsWithTypePerRole.put(Role.ADMIN, listForAdmin);

    }

    public static boolean decise(String methodPath, String methodType, Role userRole) {

        var methodAllowed = new AtomicBoolean(false);
        var allMethodsForRole = allowedMethodsWithTypePerRole.get(userRole);
        allMethodsForRole.forEach(methodPattern-> {
            var replacedMethodName = methodPattern.getMethodName()
                    .replace("*", "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}");
            var pattern = Pattern.compile(replacedMethodName);
            var matcher = pattern.matcher(methodPath);
            if(matcher.matches() && methodPattern.getMethodType().equalsIgnoreCase(methodType)) {
                methodAllowed.set(true);
            }
        });
        var returnValue = methodAllowed.get();
        methodAllowed.set(false);
        return returnValue;
    }
}
