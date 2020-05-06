package life.myheart.community.mapper;

import life.myheart.community.model.Word;

import java.util.List;

public interface WordExtMapper {
    List<Word> selectSearch(String id, String sensitiveword, int offset, Integer limit);

    Long selectWordRelated(String id, String sensitiveword);
}
