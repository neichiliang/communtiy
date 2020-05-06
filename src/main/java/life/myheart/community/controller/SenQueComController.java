package life.myheart.community.controller;

import life.myheart.community.service.SenQueComService;
import life.myheart.community.service.QuestionService;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SenQueComController {

    @Autowired
    private SenQueComService senQueComService;

    @Autowired
    private QuestionService questionService;






    @RequestMapping("/getsensitiveword")
    @ResponseBody
    public PageUtil getsensitiveword(@Param("limit")Integer limit,
                           @Param("page")Integer page){
        PageUtil pageUtil = senQueComService.getsensitiveword(limit,page);
        return pageUtil;
    }
    @RequestMapping("/selectsensitiveword")
    @ResponseBody
    public PageUtil selectsensitiveword(@Param("limit")Integer limit,
                              @Param("page")Integer page,
                              @Param("id")String id,
                              @Param("name")String sensitiveword){
        PageUtil pageUtil = senQueComService.selectsensitiveword(id,sensitiveword,limit,page);
        return pageUtil;
    }


    @RequestMapping("/getsenquestions")
    @ResponseBody
    public PageUtil getsenquestions(@Param("limit")Integer limit,
                            @Param("page")Integer page){
        PageUtil pageUtil = senQueComService.getsenquestions(limit,page);
        return pageUtil;
    }


    @RequestMapping("/getsencomments")
    @ResponseBody
    public PageUtil getsencomments(@Param("limit")Integer limit,
                                    @Param("page")Integer page){
        PageUtil pageUtil = senQueComService.getsencomments(limit,page);
        return pageUtil;
    }

    @RequestMapping("/selectsencom")
    @ResponseBody
    public PageUtil selectsencom(@Param("limit")Integer limit,
                              @Param("page")Integer page,
                              @Param("id")String id,
                              @Param("creator")String creator,
                              @Param("content")String content,
                              @Param("sensitiveword")String sensitiveword){
        PageUtil pageUtil = senQueComService.selectsencom(id,creator,content,sensitiveword,limit,page);
        return pageUtil;
    }

    @RequestMapping("/selectsenque")
    @ResponseBody
    public PageUtil selectsenque(@Param("limit")Integer limit,
                              @Param("page")Integer page,
                              @Param("id")String id,
                              @Param("creator")String creator,
                              @Param("title")String title,
                              @Param("content")String content,
                              @Param("sensitiveword")String sensitiveword){
        System.out.println(id);
        PageUtil pageUtil = senQueComService.selectsenque(id,creator,title,content,sensitiveword,limit,page);
        return pageUtil;
    }

    @RequestMapping("/deletesen")
    @ResponseBody
    public boolean deletesen(@Param("id")long id){


        boolean b = senQueComService.deletesen(id);

        return b;
    }

    @RequestMapping("/deletelistsen")
    @ResponseBody
    public boolean deletelistsen(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = senQueComService.deletelistsen(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }


    @RequestMapping("/deletesencom")
    @ResponseBody
    public boolean deletesencom(@Param("id")long id){


        boolean b = senQueComService.deletesencom(id);

        return b;
    }

    @RequestMapping("/deletelistsencom")
    @ResponseBody
    public boolean deletelistsencom(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = senQueComService.deletelistsencom(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }

    @RequestMapping("/deletesenque")
    @ResponseBody
    public boolean deletesenque(@Param("id")long id){


        boolean b = senQueComService.deletesenque(id);

        return b;
    }

    @RequestMapping("/deletelistsenque")
    @ResponseBody
    public boolean deletelistsenque(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = senQueComService.deletelistsenque(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }

    @RequestMapping("/updatasen")
    @ResponseBody
    public boolean updatasen(@Param("id") long id,
                             @Param("sensitiveword") String sensitiveword,
                             @Param("status") Integer status){
        boolean b = senQueComService.updatasen(id,sensitiveword,status);

        return b;
    }

    @RequestMapping("/addsen")
    @ResponseBody
    public boolean addsen(@Param("sensitiveword") String sensitiveword,
                          @Param("status") Integer status){
        boolean b = senQueComService.addsen(sensitiveword,status);
        return b;
    }


}
