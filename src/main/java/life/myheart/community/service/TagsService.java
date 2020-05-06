package life.myheart.community.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import life.myheart.community.dto.TagDTO;
import life.myheart.community.mapper.TagsExtMapper;
import life.myheart.community.mapper.TagsMapper;
import life.myheart.community.model.Tags;
import life.myheart.community.model.TagsExample;
import life.myheart.community.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private TagsExtMapper tagsExtMapper;


    public List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagsExample tagsExample = new TagsExample();
        tagsExample.createCriteria()
                .andFatherEqualTo((long)0);
        List<Tags> tagsList = tagsMapper.selectByExample(tagsExample);


        for (Tags tag : tagsList){
            TagDTO tagdot = new TagDTO();
            tagdot.setId(tag.getId());
            tagdot.setTag(tag.getTag());

            TagsExample tagsExample1 = new TagsExample();
            tagsExample1.createCriteria()
                    .andFatherEqualTo(tag.getId());
            List<Tags> tags = tagsMapper.selectByExample(tagsExample1);

            if (tags != null)
            tagdot.setTags(tags);
            tagDTOS.add(tagdot);
        }
        return tagDTOS;
    }

    public String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<Tags> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

    public PageUtil gettag(Integer limit, Integer page) {
        int offset = (page-1)*limit;
        List<Tags> tags = tagsExtMapper.selectRelated(offset,limit);

        JSONArray json = new JSONArray();
        for(Tags tag : tags){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",tag.getId());
            jsonObject.put("tag",tag.getTag());
            if (tag.getFather()!=0) {
                Tags ftag = tagsMapper.selectByPrimaryKey(tag.getFather());
                jsonObject.put("father", ftag.getTag());
            }
            if (tag.getFather()==0) {
                jsonObject.put("father", "主要分类");
            }
            json.add(jsonObject);
        }
        TagsExample tagsExample = new TagsExample();
        Long count = tagsMapper.countByExample(tagsExample);
        PageUtil pageUtil = new PageUtil();
        System.out.println("count:"+count);
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public boolean deletetag(long id) {
        int s = tagsMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean deletelisttag(long id) {
        int s = tagsMapper.deleteByPrimaryKey(id);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean updatatag(long id, String tag, long father) {
        Tags tags = tagsMapper.selectByPrimaryKey(id);
        tags.setTag(tag);
        tags.setFather(father);

        int s = tagsMapper.updateByPrimaryKey(tags);
        if (s == 1)
            return true;
        else
            return false;
    }

    public boolean addtag(String tag, long father) {
        Tags tags = new Tags();
        tags.setTag(tag);
        tags.setFather(father);
        int s = tagsMapper.insert(tags);
        if (s == 1)
            return true;
        else
            return false;
    }

    public PageUtil selecttag(String id, String tag, Integer limit, Integer page) {
        int offset = (page-1)*limit;

        List<Tags> tags = tagsExtMapper.selectSearch(id,tag,offset,limit);
        JSONArray json = new JSONArray();
        for(Tags tag1 : tags){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",tag1.getId());
            jsonObject.put("tag",tag1.getTag());
            if (tag1.getFather()!=0) {
                Tags ftag = tagsMapper.selectByPrimaryKey(tag1.getFather());
                jsonObject.put("father", ftag.getTag());
            }
            if (tag1.getFather()==0) {
                jsonObject.put("father", "主要分类");
            }
            json.add(jsonObject);
        }

        Long count = tagsExtMapper.selectTagRelated(id,tag);
        PageUtil pageUtil = new PageUtil();
        System.out.println("count:"+count);
        pageUtil.setCount(count);
        pageUtil.setData(json);
        return pageUtil;
    }

    public static void main(String[] args) {
        int i = (5 - 1) >>> 1;
        System.out.println(i);
    }
}
