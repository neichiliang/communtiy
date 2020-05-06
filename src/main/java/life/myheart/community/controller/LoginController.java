package life.myheart.community.controller;

import life.myheart.community.model.User;
import life.myheart.community.service.LoginService;
import life.myheart.community.utils.SCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/admin/page/login-1.html")
    public String login1() {
        return "page/login-1";
    }


    @RequestMapping("/admin/page/register.html")
    public String login2() {
        return "page/register.html";
    }


    @RequestMapping("/loginaction")
    public String loginaction() {
        return "indexuser";
    }

    @RequestMapping("/register")
    public String register(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "captcha") String captcha,
                           HttpSession session){
        User user = new User();
        String radio = "user";
        if (session.getAttribute("verification").equals(captcha.toUpperCase())) {
            boolean a = loginService.selectRegister(username);
            if (a != false){
                System.out.println(radio);
                user = loginService.register(username, password, radio);
            }
            else
                return "page/register.html";
            return "page/login-1";
        }
        return "page/register.html";
    }

    //登陆
    @RequestMapping("/loginsuccess")
    public String index(@RequestParam(name = "username") String name,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "captcha") String captcha,
                        @RequestParam(name = "radio") String radio,
                        HttpServletRequest http,
                        HttpSession session) {
        User user = loginService.login(name, password, radio);
        if (session.getAttribute("verification").equals(captcha.toUpperCase())) {
            if (radio.equals("user")) {
                if (user != null) {
                    http.getSession().setAttribute("user", user);
                    return "redirect:/";
                } else
                    return "page/login-1";
            }
            if (radio.equals("admin")) {
                if (user != null) {

                    http.getSession().setAttribute("user", user);
                    return "redirect:/admin/";
                } else
                    return "page/login-1";
            }
        }else
            http.getSession().setAttribute("captcha", false);
        return "page/login-1";
    }


    /**
     * 生成图片验证码
     */
    @RequestMapping(value = "/userInfo/verification", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String verification(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //实例生成验证码对象
        SCaptcha instance = new SCaptcha();
        //将验证码存入session
        session.setAttribute("verification", instance.getCode());
        //向页面输出验证码图片
        instance.write(response.getOutputStream());
        System.out.println(instance.getCode() + "0");
        return "page/login-1";

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
