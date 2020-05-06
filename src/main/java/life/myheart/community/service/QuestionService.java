package life.myheart.community.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import life.myheart.community.dto.PaginationDTO;
import life.myheart.community.dto.QuestionDTO;
import life.myheart.community.dto.QuestionQueryDTO;
import life.myheart.community.enums.SortEnum;
import life.myheart.community.exception.CustomizeErrorCode;
import life.myheart.community.exception.CustomizeException;
import life.myheart.community.mapper.QuestionExtMapper;
import life.myheart.community.mapper.QuestionMapper;
import life.myheart.community.mapper.UserMapper;
import life.myheart.community.model.Question;
import life.myheart.community.model.QuestionExample;
import life.myheart.community.model.User;
import life.myheart.community.utils.PageUtil;
import life.myheart.community.utils.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public PaginationDTO list(String search, String tag, String sort, Integer page, Integer size) {
        System.out.println("sort:"+sort);

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);

                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = page < 1 ? 0 : size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        //size*(page-1)
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        String qsensitive = sensitiveFilter.filter(question.getTitle())+sensitiveFilter.filter(question.getDescription());

        if (!StringUtils.isBlank(qsensitive)) {
            question.setStatus(1);
            question.setStateContent(qsensitive);
            questionMapper.updateByPrimaryKey(question);
        }else {
            question.setStatus(0);
            questionMapper.updateByPrimaryKey(question);
        }

        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            // 更新

            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
            }

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    public List<Question> quehot() {
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("view_count DESC");
        List<Question> questions = questionMapper.selectByExample(questionExample);
        List<Question> question1 = new ArrayList<>();
        int i = 0;
        for (Question question:questions) {

            question1.add(question);
            i++;
            if(i>8)
                break;
        }
        return question1;
    }

    public boolean deletequestion(long id) {
        int s = questionMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelistquestion(long id) {
        int s = questionMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }

    public PageUtil getquestion(Integer limit, Integer page) {
        int offset = (page-1)*limit;


        QuestionExample questionExample = new QuestionExample();
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,limit));
        JSONArray json = new JSONArray();
        for(Question question : questions){

            JSONObject jsonObject = new JSONObject();
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            jsonObject.put("id",question.getId());
            jsonObject.put("name",user.getName());
            jsonObject.put("title",question.getTitle());
            jsonObject.put("description",question.getDescription());
            jsonObject.put("tag",question.getTag());

            json.add(jsonObject);
        }
        QuestionExample questionExample1 = new QuestionExample();
        Long count = questionMapper.countByExample(questionExample1);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setData(json);
        pageUtil.setCount(count);
        return pageUtil;
    }

    public PageUtil selectquestion(String name, String title, String description, String tag, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<Question> questions = questionExtMapper.selectSearch(name, title, description, tag,offset,limit);
        JSONArray json = new JSONArray();
        for(Question question1 : questions){
            User user = userMapper.selectByPrimaryKey(question1.getCreator());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",user.getName());
            jsonObject.put("title",question1.getTitle());
            jsonObject.put("description",question1.getDescription());
            jsonObject.put("tag",question1.getTag());
            json.add(jsonObject);
        }

        Long count = questionExtMapper.selectQuestionRelated(name, title, description, tag);
        PageUtil pageUtil = new PageUtil();
        System.out.println("count:"+count);
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public boolean updataque(long id, String name, String title, String description, String tag) {
        Question question = questionMapper.selectByPrimaryKey(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        int s = questionMapper.updateByPrimaryKey(question);
        if (s == 1)
            return true;
        else
            return false;
    }

}
