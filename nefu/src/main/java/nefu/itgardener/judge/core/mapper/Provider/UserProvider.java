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
                if (user.getWorkId() != null) {
                    WHERE("work_id = #{workId}");
                }
            }
        }.toString();
    }


    public String selectWorkByCondition(Work work) {
        return new SQL() {
            {
                SELECT("work_id as workId,work_name as workName,score,link,honor,work_url as workUrl" +
                        ",yytg1,yytg2,yytg3,yytg4,yytg5,kszx1,kszx2,kszx3,kszx4,kszx5,khfw1,khfw2,khfw3,khfw4,khfw5");
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
                    WHERE("work_name = " + workVo.getWorkName());
                }
                if (workVo.getWorkId() != null) {
                    WHERE("work_id = " + workVo.getWorkId());
                }
                ORDER_BY(" work_id LIMIT " + finalLimit);

            }

        }.toString();
    }

    public String updateWorkByWorkId(Work work) {
        return new SQL() {
            {
                UPDATE("work");
                if (null != work.getYytg1()) {
                    SET("yytg1 = #{yytg1} ");
                }
                if (null != work.getYytg5()) {
                    SET("yytg5 = #{yytg5} ");
                }
                if (null != work.getYytg2()) {
                    SET("yytg2 = #{yytg2} ");
                }
                if (null != work.getYytg3()) {
                    SET("yytg3 = #{yytg3} ");
                }
                if (null != work.getYytg4()) {
                    SET("yytg4 = #{yytg4} ");
                }
                if (null != work.getKszx1()) {
                    SET("kszx1 = #{kszx1} ");
                }
                if (null != work.getKszx2()) {
                    SET("kszx2 = #{kszx2} ");
                }
                if (null != work.getKszx3()) {
                    SET("kszx3 = #{kszx3} ");
                }
                if (null != work.getKszx4()) {
                    SET("kszx4 = #{kszx4} ");
                }
                if (null != work.getKszx5()) {
                    SET("kszx5 = #{kszx5} ");
                }
                if (null != work.getKhfw1()) {
                    SET("khfw1 = #{khfw1}");
                }
                if (null != work.getKhfw2()) {
                    SET("khfw2 = #{khfw2}");
                }
                if (null != work.getKhfw3()) {
                    SET("khfw3 = #{khfw3}");
                }
                if (null != work.getKhfw4()) {
                    SET("khfw4 = #{khfw4}");
                }
                if (null != work.getKhfw5()) {
                    SET("khfw5 = #{khfw5}");
                }
                if (work.getScore() != null) {
                    SET("score = #{score}");
                }
                if (work.getHonor() != null) {
                    SET("honor = #{honor}");
                }
                WHERE("work_id = #{workId}");
            }
        }.toString();
    }

    public String selectWorkOrderByScore() {
        return new SQL() {
            {
                SELECT("work_id as workId ,score");
                FROM("work");
                ORDER_BY("score DESC");

            }
        }.toString();
    }


}
