package com.master.amsterdam.util;

import ch.qos.logback.core.joran.sanity.Pair;
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

    @PostConstruct
    public static void initMethodsWithRoles() {

        var listForUser = new ArrayList<MethodPair>();
        listForUser.add(new MethodPair(PathPattern.REGISTER, "GET"));
        listForUser.add(new MethodPair(PathPattern.REGISTER_PATH_VARAIBLE, "GET"));
        allowedMethodsWithTypePerRole.put(Role.USER, listForUser);

    }

    public static boolean decise(String methodPath, String methodType, Role userRole) {

        var methodAllowed = new AtomicBoolean(false);
        var allMethodsForRole = allowedMethodsWithTypePerRole.get(userRole);
        allMethodsForRole.forEach(methodPattern-> {
            var pattern = Pattern.compile(methodPattern.getMethodName());
            var matcher = pattern.matcher(methodPath);
            if(matcher.find() && methodPattern.getMethodType().equalsIgnoreCase(methodType)) {
                methodAllowed.set(true);
            }
        });
        return methodAllowed.get();
    }
}
