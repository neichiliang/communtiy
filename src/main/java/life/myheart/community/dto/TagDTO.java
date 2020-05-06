package life.myheart.community.dto;

import life.myheart.community.model.Tags;
import lombok.Data;

import java.util.List;


@Data
public class TagDTO {
    private long id;
    private String tag;
    private List<Tags> tags;
}
