package life.myheart.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @RequestMapping("/admin/")
    public String indexadmin(){
        return "index";
    }

    @RequestMapping("/admin/page/user.html")
    public String user(){
        return "page/user";
    }

    @RequestMapping("/admin/page/sensitive.html")
    public String sensitive(){
        return "page/sensitive";
    }

    @RequestMapping("/admin/page/question.html")
    public String question(){
        return "page/question";
    }

    @RequestMapping("/admin/page/adduser.html")
    public String adduser(){
        return "page/adduser";
    }

    @RequestMapping("/admin/page/classification.html")
    public String classification(){
        return "page/classification";
    }

    @RequestMapping("/admin/page/sensitivequestion.html")
    public String sensitivequestion(){
        return "page/sensitivequestion";
    }

    @RequestMapping("/admin/page/sensitivecomment.html")
    public String sensitivecomment(){
        return "page/sensitivecomment";
    }

    @RequestMapping("/admin/page/add.html/{id}")
    public String pageadd(@PathVariable(name = "id") String id){
        return "page/add"+id;
    }



    @RequestMapping("/admin/page/form.html")
    public String indexadmin6(){
        return "page/form";
    }


    @RequestMapping("/admin/page/form-step.html")
    public String indexadmin7(){
        return "page/form-step";
    }


}
