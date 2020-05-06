package life.myheart.community.service;

import life.myheart.community.mapper.UserMapper;
import life.myheart.community.model.User;
import life.myheart.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private UserMapper userMapper;

    public User login(String name, String password,String radio) {


        UserExample userExample = new UserExample();
        List<User> user = null;
        User usera = null;

        if (name != "" && name != null && password != "" && password != null&& radio != "" && radio != null) {
            userExample.createCriteria()
                    .andNameEqualTo(name)
                    .andPasswordEqualTo(password)
                    .andTypeEqualTo(radio);
            user = userMapper.selectByExample(userExample);
            /*String userId = user.stream().map(User::getId).collect(Collectors.toList());*/
            for (User a:user){
                usera = a;
            }
            if (user.size() != 0) {
                return usera;
            } else {

                return usera;
            }
        } else {
            return usera;

        }
    }
    public boolean selectRegister(String username) {
        List<User> user = null;
        UserExample userExample =new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(username);
        user = userMapper.selectByExample(userExample);
        System.out.println(user);
        boolean a = user.isEmpty();
        System.out.println(a);
        return a;
    }

    public User register(String username, String password ,String radio) {
        User user = new User();
        String img = "/images/default-avatar.png";
        user.setName(username);
        user.setPassword(password);
        user.setType(radio);
        user.setAvatarUrl(img);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        userMapper.insert(user);

        return user;
    }
}

