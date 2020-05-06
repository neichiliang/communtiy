package life.myheart.community.mapper;

import life.myheart.community.model.Tags;

import java.util.List;

public interface TagsExtMapper {
    List<Tags> selectRelated(Integer offset,Integer limit);

    List<Tags> selectSearch(String id, String tag, int offset, Integer limit);

    long selectTagRelated(String id, String tag);
}
