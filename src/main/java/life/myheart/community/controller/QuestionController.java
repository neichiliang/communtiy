package life.myheart.community.controller;

import life.myheart.community.dto.CommentDTO;
import life.myheart.community.dto.QuestionDTO;
import life.myheart.community.enums.CommentTypeEnum;
import life.myheart.community.exception.CustomizeErrorCode;
import life.myheart.community.exception.CustomizeException;
import life.myheart.community.service.CommentService;
import life.myheart.community.service.QuestionService;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDTO questionDTO = questionService.getById(questionId);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

    @RequestMapping("/updataque")
    @ResponseBody
    public boolean updataque(@Param("id") long id,
                             @Param("name") String name,
                             @Param("title") String title,
                             @Param("description") String description,
                             @Param("tag") String tag){
        boolean b = questionService.updataque(id,name,title,description,tag);
        return b;
    }

    @RequestMapping("/getquestion")
    @ResponseBody
    public PageUtil getquestion(@Param("limit")Integer limit,
                                @Param("page")Integer page){
        PageUtil pageUtil = questionService.getquestion(limit,page);
        return pageUtil;
    }

    @RequestMapping("/selectquestion")
    @ResponseBody
    public PageUtil selectquestion(@Param("limit")Integer limit,
                                   @Param("page")Integer page,
                                   @Param("name")String name,
                                   @Param("title")String title,
                                   @Param("description")String description,
                                   @Param("tag")String tag){
        System.out.println(title);
        PageUtil pageUtil = questionService.selectquestion(name,title,description,tag,limit,page);
        return pageUtil;
    }

    @RequestMapping("/deletequestion")
    @ResponseBody
    public boolean deletequestion(@Param("id")long id){


        boolean b = questionService.deletequestion(id);

        return b;
    }

    @RequestMapping("/deletelistquestion")
    @ResponseBody
    public boolean deletelistquestion(@Param("id") long id[]){
        for (int i=0;i<id.length;i++) {
            boolean b = questionService.deletelistquestion(id[i]);
            if (b == false)
                return b;
        }
        return true;
    }

}
