package life.myheart.community.mapper;

import life.myheart.community.model.User;

import java.util.List;

public interface UserExtMapper {
    List<User> selectSearch(String id, String name, String phone, int offset, Integer limit);
    List<User> selectSearch(String name);
    Long selectUserRelated(String id, String name, String phone);

}
