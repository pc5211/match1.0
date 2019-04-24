package nefu.itgardener.judge.core.mapper.Provider;

import nefu.itgardener.judge.common.Page;
import nefu.itgardener.judge.common.util.PageUtil;
import nefu.itgardener.judge.core.model.User;
import nefu.itgardener.judge.core.model.Work;
import nefu.itgardener.judge.core.model.WorkVo;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String selectByCondition(User user) {
        return new SQL() {
            {
                SELECT("system_id as systemId, user_id as userId ,password ,leader_name as leaderName" +
                        ",major,college,phone,type,grade,work_id as workId,member1,member2,token");
                FROM("user");
                if (user.getUserId() != null) {
                    WHERE("user_id = #{userId}");
                }
                if (user.getPassword() != null) {
                    WHERE("password = #{password}");
                }
            }
        }.toString();
    }


    public String selectWorkByCondition(Work work) {
        return new SQL() {
            {
                SELECT("work_id as workId,work_name as workName,score,link,honor,work_url as workUrl");
                FROM("work");
                if (work.getWorkId() != null) {
                    WHERE("work_id = #{workId}");
                }
            }
        }.toString();
    }

    public String selectWorkNumByCondition(WorkVo workVo) {
        return new SQL() {
            {
                SELECT("count(work_id) as totalSize");
                FROM("work");
                if (workVo.getIsScore() != null) {
                    if (workVo.getIsScore() == 0) {
                        WHERE("score is  null");
                    } else {
                        WHERE("score is not null");
                    }
                }
                if (workVo.getWorkName() != null) {
                    WHERE("work_name = #{workName}");
                }
                if (workVo.getWorkId() != null) {
                    WHERE("work_id = #{workId}");
                }

            }

        }.toString();
    }

    public String selectDetailByCondition(WorkVo workVo, Page page) {
        String limit = "4";
        if (page != null) {
            limit = PageUtil.getLimit(page.getNowPage(), page.getPageSize());
        }
        String finalLimit = limit;
        return new SQL() {
            {
                SELECT("work_id as workId ,work_name as workName,score,honor");
                FROM("work");
                if (workVo.getIsScore() != null) {
                    if (workVo.getIsScore() == 0) {
                        WHERE("score is  null");
                    } else {
                        WHERE("score is not null");
                    }
                }
                if (workVo.getWorkName() != null) {
                    WHERE("work_name = #{workName}");
                }
                if (workVo.getWorkId() != null) {
                    WHERE("work_id = #{workId}");
                }
                ORDER_BY(" work_id LIMIT " + finalLimit);

            }

        }.toString();
    }

}
