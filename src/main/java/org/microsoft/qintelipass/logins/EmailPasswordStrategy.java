package org.microsoft.qintelipass.logins;

import org.microsoft.qintelipass.ILoginStrategy;
import org.springframework.stereotype.Service;
import org.microsoft.qintelipass.models.User;
import org.microsoft.qintelipass.response.ResponseBody;
import org.microsoft.qintelipass.ILoginable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service
public class EmailPasswordStrategy implements ILoginStrategy {

    @Autowired
    private ILoginable loginService;

    @Override
    public String getType() {
    return "EMAIL_PWD";
    }
    @Override
    public ResponseBody<User> authenticate(Map<String, Object> params) {
        User user;

        try {
            user = loginService.loginByEmailAndPassword(
                    (String) params.get("email"),
                    (String) params.get("password")
            );
        } catch (Exception e) {
            return ResponseBody.<User>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }

        if (user != null) {
            return ResponseBody.<User>builder()
                    .success(true)
                    .payload(user)
                    .build();
        } else {
            return ResponseBody.<User>builder()
                    .success(false)
                    .message("wrong password or email")
                    .build();
        }
    }
}
