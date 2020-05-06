package life.myheart.community.mapper;

import life.myheart.community.model.Comment;

import java.util.List;

public interface CommentExtMapper {
    List<Comment> selectSearch(String id, String creator, String content, String sensitiveword, int offset, Integer limit);
    int incCommentCount(Comment comment);

    Long selectcomsenRelated(String id, String creator, String content, String sensitiveword);


}