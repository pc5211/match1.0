package nefu.itgardener.judge.core.mapper;

import nefu.itgardener.judge.common.Page;
import nefu.itgardener.judge.core.mapper.Provider.UserProvider;
import nefu.itgardener.judge.core.model.User;
import nefu.itgardener.judge.core.model.Work;
import nefu.itgardener.judge.core.model.WorkVo;
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
    @Options(useGeneratedKeys = true, keyProperty = "wordId", keyColumn = "work_id")
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
}
