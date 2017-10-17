package http.service;

import http.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author liyebing created on 16/12/22.
 * @version $Id$
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * http get 请求
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "sayHello.json", method = RequestMethod.GET)
    public ApiResponse sayHello(@RequestParam(value = "userName", required = true) String userName, HttpServletRequest request) {

        String result = "hello," + userName;

        HttpSession session = request.getSession();
        String logined = (String) session.getAttribute("userName");
        if (logined != null) {
            return ApiResponse.buildSuccess(result + " already logined!");
        }
        session.setAttribute("userName", userName);

        try {
            Thread.sleep(new Random().nextInt(10));
        } catch (Exception e) {
        }

        return ApiResponse.buildSuccess(result);
    }


}
