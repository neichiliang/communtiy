package life.myheart.community.controller;

import life.myheart.community.service.UserService;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class AdminUserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userin/{id}")
    public String userin(@PathVariable(name = "id") long id,Model model){

        model.addAttribute("user", userService.selectuser(id));
        return "userin";
    }
    @RequestMapping("/userin/")
    public String userin1(){


        return "userin";
    }
    @PostMapping("/saveimg")
    public String saveimg(HttpServletRequest req,
                          @RequestParam("file") MultipartFile file,
                          Model m,
                          @Param("id")long id){
        try {
            String fileName = file.getOriginalFilename();
//            String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\";
            String filePath = System.getProperty("user.dir")+"/src/main/resources/static/images/";
            System.out.println(filePath + fileName);
            File destFile = new File(filePath + fileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            String fileName1 = "/images/"+fileName;
            boolean a = userService.updatauser(id,fileName1);
            m.addAttribute("fileName", fileName1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        return "redirect:/userin/"+id;

    }
    @PostMapping("/save")
    public String save(@Param("id")long id,
                       @Param("name")String name,
                       @Param("password")String password,
                       @Param("sex")String sex,
                       @Param("phone")String phone){


        boolean a = userService.updatauser1(id,name,phone,sex,password);
        return "redirect:/userin/"+id;
    }
    @RequestMapping("/getuser")
    @ResponseBody
    public PageUtil getuser(@Param("limit")Integer limit,
                            @Param("page")Integer page){
        PageUtil pageUtil = userService.getuser(limit,page);
        return pageUtil;
    }

    @RequestMapping("/deleteuser")
    @ResponseBody
    public boolean deleteuser(@Param("id")long id){


        boolean b = userService.deleteuser(id);

        return b;
    }

    @RequestMapping("/deletelistuser")
    @ResponseBody
    public boolean deletelistuser(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = userService.deletelistuser(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }

    @RequestMapping("/updatauser")
    @ResponseBody
    public boolean updatauser(@Param("id") long id,
                              @Param("name") String name,
                              @Param("phone") String phone,
                              @Param("type") String type,
                              @Param("sex") String sex){
        boolean b = userService.updatauser(id,name,phone,type,sex);

        return b;
    }
    @RequestMapping("/adduser")
    @ResponseBody
    public boolean adduser(@Param("name") String name,
                           @Param("password") String password,
                           @Param("phone") String phone,
                           @Param("type") String type,
                           @Param("sex") String sex){
        boolean b = userService.adduser(password,name,phone,type,sex);
        return b;
    }

    @RequestMapping("/selectuser")
    @ResponseBody
    public PageUtil selectuser(@Param("limit")Integer limit,
                               @Param("page")Integer page,
                               @Param("id")String id,
                               @Param("name")String name,
                               @Param("phone")String phone){
        PageUtil pageUtil = userService.selectuser(id,name,phone,limit,page);
        return pageUtil;
    }

}
