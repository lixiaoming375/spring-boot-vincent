package vincent.spring.session.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * SessionController
 *
 * @author: glmapper@leishu
 * @since: 18/11/3 下午3:16
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/")
public class SessionController {

    @ResponseBody
    @RequestMapping(value = "/session")
    public Map<String, Object> getSession(HttpServletRequest request) {
        request.getSession().setAttribute("userName", "glmapper");
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/get")
    public String get(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        return userName;
    }
}

