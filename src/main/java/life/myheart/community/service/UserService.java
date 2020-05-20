package life.myheart.community.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import life.myheart.community.dto.AdminUserDTO;
import life.myheart.community.mapper.QuestionMapper;
import life.myheart.community.mapper.UserExtMapper;
import life.myheart.community.mapper.UserMapper;
import life.myheart.community.model.Question;
import life.myheart.community.model.QuestionExample;
import life.myheart.community.model.User;
import life.myheart.community.model.UserExample;
import life.myheart.community.utils.PageUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private QuestionMapper questionMapper;


    public PageUtil getuser(Integer limit, Integer page) {
        int offset = (page-1)*limit;

        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExampleWithRowbounds(userExample,new RowBounds(offset,limit));

        JSONArray json = new JSONArray();
        for(User user : users){
            AdminUserDTO adminUserDTO = new AdminUserDTO();
            adminUserDTO.setId(user.getId());
            adminUserDTO.setName(user.getName());
            adminUserDTO.setSex(user.getSex());
            adminUserDTO.setPhone(user.getPhone());
            adminUserDTO.setType(user.getType());
            json.add(adminUserDTO);
        }
        UserExample userExample1 = new UserExample();
        Long count = userMapper.countByExample(userExample1);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setData(json);
        pageUtil.setCount(count);
        return pageUtil;
    }

    public boolean deleteuser(long id) {

        int s = userMapper.deleteByPrimaryKey(id);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        int s1 = questionMapper.deleteByExample(questionExample);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelistuser(long id) {
        int s = userMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;

    }

    public User selectuser(long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    public PageUtil selectuser(String id, String name, String phone, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<User> users = userExtMapper.selectSearch(id,name,phone,offset,limit);
        JSONArray json = new JSONArray();
        for(User user1 : users){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",user1.getId());
            jsonObject.put("name",user1.getName());
            jsonObject.put("phone",user1.getPhone());
            json.add(jsonObject);
        }

        Long count = userExtMapper.selectUserRelated(id,name,phone);
        PageUtil pageUtil = new PageUtil();
        System.out.println("count:"+count);
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public boolean adduser(String password, String name, String phone, String type, String sex) {
        String img = "/images/default-avatar.png";
        User user = new User();
        user.setAvatarUrl(img);
        user.setName(name);
        user.setPassword(password);
        user.setPhone(phone);
        user.setType(type);
        user.setSex(sex);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        int s = userMapper.insert(user);
        if (s == 1)
            return true;
        else
            return false;

    }

    public boolean updatauser(long id, String name, String phone, String type, String sex) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setName(name);
        user.setPhone(phone);
        user.setType(type);
        user.setSex(sex);
        user.setGmtModified(System.currentTimeMillis());
        int s = userMapper.updateByPrimaryKey(user);
        if (s == 1)
            return true;
        else
            return false;

    }

    public boolean updatauser(long id, String img) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setAvatarUrl(img);
        user.setGmtModified(System.currentTimeMillis());
        int s = userMapper.updateByPrimaryKey(user);
        if (s == 1)
            return true;
        else
            return false;

    }

    public boolean updatauser1(long id, String name, String phone, String sex, String password) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);
        user.setSex(sex);
        user.setGmtModified(System.currentTimeMillis());
        int s = userMapper.updateByPrimaryKey(user);
        if (s == 1)
            return true;
        else
            return false;
    }
}
