package life.myheart.community.mapper;

import java.util.List;
import life.myheart.community.model.Tags;
import life.myheart.community.model.TagsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TagsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    long countByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int deleteByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int insert(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int insertSelective(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    List<Tags> selectByExampleWithRowbounds(TagsExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    List<Tags> selectByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    Tags selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int updateByExampleSelective(@Param("record") Tags record, @Param("example") TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int updateByExample(@Param("record") Tags record, @Param("example") TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int updateByPrimaryKeySelective(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbg.generated Sat Apr 11 19:55:17 GMT+08:00 2020
     */
    int updateByPrimaryKey(Tags record);
}