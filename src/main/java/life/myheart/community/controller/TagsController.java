package life.myheart.community.controller;

import life.myheart.community.service.TagsService;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagsController {
    @Autowired
    private TagsService tagsService;


    @RequestMapping("/gettag")
    @ResponseBody
    public PageUtil gettag(@Param("limit")Integer limit,
                           @Param("page")Integer page){
        PageUtil pageUtil = tagsService.gettag(limit,page);
        return pageUtil;
    }

    @RequestMapping("/selecttag")
    @ResponseBody
    public PageUtil selecttag(@Param("limit")Integer limit,
                              @Param("page")Integer page,
                              @Param("id")String id,
                              @Param("tag")String tag){
        PageUtil pageUtil = tagsService.selecttag(id,tag,limit,page);
        return pageUtil;
    }

    @RequestMapping("/deletetag")
    @ResponseBody
    public boolean deletetag(@Param("id")long id){


        boolean b = tagsService.deletetag(id);

        return b;
    }

    @RequestMapping("/deletelisttag")
    @ResponseBody
    public boolean deletelisttag(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = tagsService.deletelisttag(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }

    @RequestMapping("/updatatag")
    @ResponseBody
    public boolean updatatag(@Param("id") long id,
                             @Param("tag") String tag,
                             @Param("father") long father){
        boolean b = tagsService.updatatag(id,tag,father);

        return b;
    }

    @RequestMapping("/addtag")
    @ResponseBody
    public boolean addtag(@Param("tag") String tag,
                          @Param("father") long father){
        boolean b = tagsService.addtag(tag,father);
        return b;
    }}
