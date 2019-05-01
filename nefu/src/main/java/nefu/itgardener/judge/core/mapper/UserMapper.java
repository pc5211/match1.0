package nefu.itgardener.judge.core.mapper;


import nefu.itgardener.judge.common.Page;
import nefu.itgardener.judge.core.mapper.Provider.UserProvider;
import nefu.itgardener.judge.core.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 条件查询
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    /**
     * 设置token
     *
     * @param user token
     * @return int
     */
    @Update("UPDATE user SET token=#{token} WHERE system_id=#{systemId}")
    int updateTokenBySystemId(User user);

    /**
     * 插入用户
     *
     * @param user 个人信息
     * @return 1
     */
    @Insert("INSERT user SET user_id = #{userId},leader_name = #{leaderName},member1 = #{member1},member2 = #{member2}" +
            ",grade = #{grade},college = #{college} ,phone = #{phone}, password = #{password},type = #{type}")
    int inserUser(User user);

    /**
     * 插入作品
     *
     * @param work work
     * @return workId
     */
    @Insert("INSERT work SET link = #{link},work_name = #{workName},work_url = #{workUrl}")
    @Options(useGeneratedKeys = true, keyProperty = "workId", keyColumn = "work_id")
    int insertWork(Work work);

    /**
     * 更新用户信息
     *
     * @param user user
     * @return int
     */
    @Update("UPDATE user SET work_id = #{workId} WHERE user_id = #{userId}")
    int updateWorkIdByUserId(User user);

    /**
     * 查询作品相关的信息
     *
     * @param work work
     * @return list
     */
    @SelectProvider(type = UserProvider.class, method = "selectWorkByCondition")
    List<Work> selectWorkByCondition(Work work);

    /**
     * 查询作品数量
     *
     * @param workVo 相关条件
     * @return page
     */
    @SelectProvider(type = UserProvider.class, method = "selectWorkNumByCondition")
    Page selectWorkNumByCondition(WorkVo workVo);

    /**
     * 查询具体的作品
     *
     * @param workVo
     * @param page
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectDetailByCondition")
    List<Work> selectDetailByCondition(WorkVo workVo, Page page);

    /**
     * 更新分数
     *
     * @param work work
     * @return int
     */
    @UpdateProvider(type = UserProvider.class, method = "updateWorkByWorkId")
    int updateWorkByWorkId(Work work);

    /**
     * 查看密钥
     *
     * @return config
     */
    @Select("select config_key as configKey config_value as configValue where config_key = 'publish_key' ")
    Config selectConfig();

    @Select("select work_id as workId ,score from work order by score desc")
    List<Work> selectWorkOrderByScore();

    /**
     * 插入通知
     *
     * @param news 通知
     * @return int
     */
    @Insert("insert news set news_time=#{newsTime},news_title = #{newsTitle},news_content=#{newsContent}")
    int insertNews(News news);

    /**
     * 查看所有的通知
     *
     * @return list
     */
    @Select("select news_id as newsId,news_time as newsTime,news_title as newsTitle,news_content as newsContent from" +
            " news order by news_id")
    List<News> selectAllNews();

    /**
     * 根据id查看通知
     *
     * @param news
     * @return
     */
    @Select("select news_id as newsId,news_time as newsTime,news_title as newsTitle,news_content as newsContent from" +
            " news where news_id = #{newsId} ")
    List<News> selectNewsById(News news);

    /**
     * 根据学号查询学生
     *
     * @param student 学生
     * @return list
     */
    @Select("select student_id as studentId , number , type from student where number = #{number}")
    List<Student> selectStudentByNumber(Student student);

    /**
     * 插入学号
     *
     * @param student 学号 和类型
     * @return int
     */
    @Insert("insert student set number = #{number},type = #{type}")
    int insertStudent(Student student);

    /**
     * 查询数量
     *
     * @return
     */
    @Select("select number from student where type = 1")
    List<Student> selectStudentNum();
}
