package nefu.itgardener.judge.service;

import nefu.itgardener.judge.common.LibException;
import nefu.itgardener.judge.core.model.User;
import nefu.itgardener.judge.core.model.Work;
import nefu.itgardener.judge.core.model.WorkVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    /**
     * 用户登陆
     *
     * @param user 用户实体
     * @return 结果集
     * @throws LibException 异常信息
     */
    Map<String, Object> postLogin(User user) throws LibException;

    /**
     * 用户注册
     *
     * @param user 信息
     * @return true
     * @throws LibException 异常
     */
    boolean postSign(User user) throws LibException;

    /**
     * 添加作品信息
     *
     * @param work
     * @return
     * @throws LibException
     */
    int postUpload(Work work) throws LibException;

    /**
     * 更新信息
     *
     * @param user
     * @return
     */
    boolean putUserInfo(User user);

    /**
     * 查询得分相关信息
     *
     * @param user
     * @return
     * @throws LibException
     */
    Map<String, Object> getWorkByUserId(User user) throws LibException;

    /**
     * 分页查询所有的作品 每页5条
     *
     * @param workVo
     * @return
     * @throws LibException
     */
    List<Map<String, Object>> getWorkList(WorkVo workVo) throws LibException;
}
