package life.myheart.community.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import life.myheart.community.dto.AdminUserDTO;
import life.myheart.community.mapper.*;
import life.myheart.community.model.*;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SenQueComService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordExtMapper wordExtMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;
    @Transactional


    public PageUtil getsensitiveword(Integer limit, Integer page) {
        int offset = (page-1)*limit;

        WordExample wordExample = new WordExample();
        List<Word> words = wordMapper.selectByExampleWithRowbounds(wordExample,new RowBounds(offset,limit));

        JSONArray json = new JSONArray();
        for(Word word : words){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",word.getId());
            jsonObject.put("sensitiveword",word.getSensitiveWords());
            jsonObject.put("status",word.getStatus());
            json.add(jsonObject);
        }

        WordExample wordExample1 = new WordExample();
        Long count = wordMapper.countByExample(wordExample1);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setData(json);
        pageUtil.setCount(count);
        return pageUtil;
    }

    public PageUtil selectsensitiveword(String id, String sensitiveword, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<Word> words = wordExtMapper.selectSearch(id,sensitiveword,offset,limit);
        JSONArray json = new JSONArray();
        for(Word word1 : words){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",word1.getId());
            jsonObject.put("sensitiveword",word1.getSensitiveWords());
            jsonObject.put("status",word1.getStatus());
            json.add(jsonObject);
        }

        Long count = wordExtMapper.selectWordRelated(id,sensitiveword);
        PageUtil pageUtil = new PageUtil();
        System.out.println("count:"+count);
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public PageUtil getsenquestions(Integer limit, Integer page) {
        int offset = (page-1)*limit;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andStatusEqualTo(1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,limit));

        JSONArray json = new JSONArray();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",question.getId());
            jsonObject.put("creator",user.getName());
            jsonObject.put("title",question.getTitle());
            jsonObject.put("description",question.getDescription());
            jsonObject.put("sensitiveword",question.getStateContent());
            jsonObject.put("status",question.getStatus());
            json.add(jsonObject);
        }



        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andStatusEqualTo(1);
        Long count = questionMapper.countByExample(questionExample1);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setData(json);
        pageUtil.setCount(count);
        return pageUtil;

    }



    public PageUtil getsencomments(Integer limit, Integer page) {
        int offset = (page-1)*limit;


        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andStatusEqualTo(1);
        List<Comment> comments = commentMapper.selectByExampleWithRowbounds(commentExample,new RowBounds(offset,limit));

        JSONArray json = new JSONArray();
        for(Comment comment : comments){
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",comment.getId());
            jsonObject.put("creator",user.getName());
            jsonObject.put("content",comment.getContent());
            jsonObject.put("sensitiveword",comment.getStateContent());
            jsonObject.put("status",comment.getStatus());
            json.add(jsonObject);
        }


        CommentExample commentExample1 = new CommentExample();
        commentExample1.createCriteria().andStatusEqualTo(1);
        Long count = commentMapper.countByExample(commentExample1);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setData(json);
        pageUtil.setCount(count);
        return pageUtil;

    }

    public PageUtil selectsencom(String id, String creator, String content, String sensitiveword, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<Comment> comments = commentExtMapper.selectSearch(id,creator,content,sensitiveword,offset,limit);
        JSONArray json = new JSONArray();
        for(Comment comment1 : comments){
            User user = userMapper.selectByPrimaryKey(comment1.getCommentator());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",comment1.getId());
            jsonObject.put("creator",user.getName());
            jsonObject.put("content",comment1.getContent());
            jsonObject.put("status",comment1.getStatus());
            jsonObject.put("sensitiveword",comment1.getStateContent());
            json.add(jsonObject);
        }

        Long count = commentExtMapper.selectcomsenRelated(id,creator,content,sensitiveword);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public PageUtil selectsenque(String id, String creator, String title, String content, String sensitiveword, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<Question> questions = questionExtMapper.selectSearchsen(id,creator,title,content,sensitiveword,offset,limit);
        JSONArray json = new JSONArray();
        for(Question question1 : questions){
            User user = userMapper.selectByPrimaryKey(question1.getCreator());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",question1.getId());
            jsonObject.put("creator",user.getName());
            jsonObject.put("title",question1.getTitle());
            jsonObject.put("description",question1.getDescription());
            jsonObject.put("status",question1.getStatus());
            jsonObject.put("sensitiveword",question1.getStateContent());
            json.add(jsonObject);
        }

        Long count = questionExtMapper.selectsenqueRelated(id,creator,title,content,sensitiveword);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }





    public boolean deletesen(long id) {
        int s = wordMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelistsen(long id) {
        int s = wordMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }



    public boolean deletesencom(long id) {

        Comment comment =commentMapper.selectByPrimaryKey(id);
        comment.setStatus(0);
        comment.setStateContent("");

        int s = commentMapper.updateByPrimaryKey(comment);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelistsencom(long id) {
        Comment comment =commentMapper.selectByPrimaryKey(id);
        comment.setStatus(0);
        comment.setStateContent("");

        int s = commentMapper.updateByPrimaryKey(comment);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelistsenque(long id) {
        Question question =questionMapper.selectByPrimaryKey(id);
        question.setStatus(0);
        question.setStateContent("");

        int s = questionMapper.updateByPrimaryKey(question);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletesenque(long id) {
        Question question =questionMapper.selectByPrimaryKey(id);
        question.setStatus(0);
        question.setStateContent("");

        int s = questionMapper.updateByPrimaryKey(question);
        if (s == 1)
            return true;
        else
            return false;
    }




    public boolean addsen(String sensitiveword, Integer status) {
        Word word = new Word();
        word.setSensitiveWords(sensitiveword);
        word.setStatus(status);
        int s = wordMapper.insert(word);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean updatasen(long id, String sensitiveword, Integer status) {
        Word word = wordMapper.selectByPrimaryKey(id);
        word.setSensitiveWords(sensitiveword);
        word.setStatus(status);

        int s = wordMapper.updateByPrimaryKey(word);
        if (s == 1)
            return true;
        else
            return false;
    }


}
