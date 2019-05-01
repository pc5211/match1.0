package nefu.itgardener.judge.web.api;

import nefu.itgardener.judge.common.LibException;
import nefu.itgardener.judge.common.RestData;
import nefu.itgardener.judge.core.model.User;
import nefu.itgardener.judge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : pc
 * @date : 2019/04/19
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class LoginApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public LoginApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestData postLogin(@RequestBody User user) {
        if (user.getPassword() == null || null == user.getUserId()) {
            return new RestData(1, "账号或者密码为空");
        } else {
            try {
                Map<String, Object> map = userService.postLogin(user);
                return new RestData(map);

            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            }
        }

    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public RestData postSign(@RequestBody User user) {
        if (user.getPassword() == null || null == user.getUserId()) {
            return new RestData(1, "账号或者密码为空");
        } else {
            try {
                boolean b = userService.postSign(user);
                if (b) {
                    return new RestData(b);
                } else {
                    return new RestData(1, "注册失败");
                }
            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            }
        }
    }
}
