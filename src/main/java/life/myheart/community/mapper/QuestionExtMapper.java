package life.myheart.community.mapper;

import life.myheart.community.dto.QuestionQueryDTO;
import life.myheart.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectSearch(String name, String title, String description, String tag, int offset, Integer limit);

    Long selectQuestionRelated(String name, String title, String description, String tag);

    List<Question> selectSearchsen(String id, String creator, String title, String content, String sensitiveword, int offset, Integer limit);

    Long selectsenqueRelated(String id, String creator, String title, String content, String sensitiveword);
}