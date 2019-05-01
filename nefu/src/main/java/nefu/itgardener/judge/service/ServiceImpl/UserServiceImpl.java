package nefu.itgardener.judge.service.ServiceImpl;

import nefu.itgardener.judge.common.LibException;
import nefu.itgardener.judge.common.Page;
import nefu.itgardener.judge.common.RestData;
import nefu.itgardener.judge.common.util.PageUtil;
import nefu.itgardener.judge.common.util.TokenUtil;
import nefu.itgardener.judge.core.mapper.UserMapper;
import nefu.itgardener.judge.core.model.*;
import nefu.itgardener.judge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.print.DocFlavor;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, Object> postLogin(User user) throws LibException {
        Map<String, Object> rtv = null;
        List<User> users = userMapper.selectByCondition(user);
        if (null != users && 1 == users.size()) {
            user = users.get(0);
            user.setToken(TokenUtil.getToken());
            if (0 < userMapper.updateTokenBySystemId(user)) {
                rtv = new HashMap<>(3);
                user = users.get(0);
                rtv.put("systemId", user.getSystemId());
                rtv.put("token", user.getToken());
                rtv.put("type", user.getType());
            }

        } else {
            throw new LibException("用户名或密码不正确!");
        }
        return rtv;
    }

    @Override
    public boolean postSign(User user) throws LibException {
        user.setType("S");
        int i = userMapper.inserUser(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int postUpload(Work work) throws LibException {
        userMapper.insertWork(work);
        int workId = work.getWorkId();
        if (workId > 0) {
            return workId;
        } else {
            throw new LibException("添加作品信息异常");
        }
    }

    @Override
    public boolean putUserInfo(User user) {
        int i = userMapper.updateWorkIdByUserId(user);
        return i > 0;
    }

    @Override
    public Map<String, Object> getWorkByUserId(User user) throws LibException {
        Map<String, Object> rtv = new HashMap<>(16);
        List<User> users = userMapper.selectByCondition(user);

        if (null == users || users.size() != 1) {
            throw new LibException("用户邮箱出现异常");
        } else {
            User user1 = users.get(0);
            Work work = new Work();
            work.setWorkId(users.get(0).getWorkId());
            List<Work> works = userMapper.selectWorkByCondition(work);
            rtv.put("leaderName", user1.getLeaderName());
            rtv.put("member1", user1.getMember1());
            rtv.put("member2", user1.getMember2());
            if (users.get(0).getWorkId() != null) {
                rtv.put("workName", works.get(0).getWorkName());
                rtv.put("link", works.get(0).getLink());
                if (works.get(0).getLink() == null) {
                    rtv.put("state", 0);
                } else {
                    rtv.put("state", 1);
                }
                if (works.get(0).getYytg1() != null || works.get(0).getYytg2() != null || works.get(0).getYytg3() != null || works.get(0).getYytg4() != null || works.get(0).getYytg5() != null) {
                    rtv.put("state", 2);
                }

                rtv.put("workUrl", works.get(0).getWorkUrl());
                if (works.get(0).getScore() != null) {
                    rtv.put("score", works.get(0).getScore());
                    rtv.put("state", 3);
                }
                if (works.get(0).getHonor() != null) {
                    rtv.put("honor", works.get(0).getHonor());
                }
            } else {
                rtv.put("workName", null);
                rtv.put("link", null);
                rtv.put("honor", null);
                rtv.put("state", -1);
            }


            return rtv;
        }

    }

    @Override
    public Map<String, Object> getWorkList(WorkVo workVo) throws LibException {
        Page page = userMapper.selectWorkNumByCondition(workVo);
        if (null == workVo.getPage()) {
            workVo.setPage(1);
        }
        page.setNowPage(workVo.getPage());
        page = PageUtil.checkPage(page);
        List<Work> works = userMapper.selectDetailByCondition(workVo, page);
        if (works != null) {
            List<Map<String, Object>> rtv = new ArrayList<>();
            for (Work work : works) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("workId", work.getWorkId());
                map.put("workName", work.getWorkName());
                map.put("score", work.getScore());
                map.put("honor", work.getHonor());
                rtv.add(map);
            }
            Map<String, Object> mapRtv = new HashMap<>(16);
            mapRtv.put("workList", rtv);
            Map<String, Object> map = new HashMap<>(4);
            map.put("nowPage", page.getNowPage());
            map.put("eachPage", page.getPageSize());
            map.put("totalPage", page.getTotalPage());
            map.put("totalSize", page.getTotalSize());
            mapRtv.put("page", map);

            return mapRtv;
        } else {
            throw new LibException("查询失败");
        }

    }

    @Override
    public RestData getWorkScore(User user) throws LibException {
        Work work = new Work();
        work.setWorkId(user.getWorkId());
        List<Work> works = userMapper.selectWorkByCondition(work);
        work = works.get(0);
        if (null == works) {
            throw new LibException("输入的作品编号有误");
        } else {
            Map<String, Object> map = new HashMap<>(16);
            map.put("workId", work.getWorkId());
            map.put("workName", work.getWorkName());
            map.put("workUrl", work.getWorkUrl());
            map.put("link", work.getLink());

            if (user.getUserId().contains("1")) {
                map.put("yytg1", work.getYytg1());
                map.put("kszx1", work.getKszx1());
                map.put("khfw1", work.getKhfw1());
            }
            if (user.getUserId().contains("2")) {
                map.put("yytg2", work.getYytg2());
                map.put("kszx2", work.getKszx2());
                map.put("khfw2", work.getKhfw2());
            }
            if (user.getUserId().contains("3")) {
                map.put("yytg3", work.getYytg3());
                map.put("kszx3", work.getKszx3());
                map.put("khfw3", work.getKhfw3());
            }
            if (user.getUserId().contains("4")) {
                map.put("yytg4", work.getYytg4());
                map.put("kszx4", work.getKszx4());
                map.put("khfw4", work.getKhfw4());
            }
            if (user.getUserId().contains("5")) {
                map.put("yytg5", work.getYytg5());
                map.put("kszx5", work.getKszx5());
                map.put("khfw5", work.getKhfw5());
            }
            return new RestData(map);
        }

    }

    @Override
    public RestData postWorkScore(Work work) throws LibException {
        int i = userMapper.updateWorkByWorkId(work);
        if (i > 0) {
            return new RestData(true);
        } else {
            throw new LibException("更新异常");
        }
    }

    @Override
    public RestData postPublishWorkScore() throws LibException {
        Work work = new Work();
        int count = 0;
        List<Work> works = userMapper.selectWorkByCondition(work);
        System.out.println(works.size());
        for (Work work1 : works) {
            if (null == work1.getKhfw1() || null == work1.getKhfw2() || null == work1.getKhfw3() || null == work1.getKhfw4() || null == work1.getKhfw5()) {
                throw new LibException("还有老师评分未完成，请完成打分再公布分数");
            } else {
                int score = work1.getKhfw1() + work1.getKhfw2() + work1.getKhfw3() +
                        work1.getKhfw4() + work1.getKhfw5() + work1.getKszx1() + work1.getKszx2()
                        + work1.getKszx3() + work1.getKszx4() + work1.getKszx5() +
                        work1.getYytg5() + work1.getYytg4() + work1.getYytg3() + work1.getYytg2()
                        + work1.getYytg5();

                work1.setScore(score / 5);
                userMapper.updateWorkByWorkId(work1);
            }
            count++;
        }
        int firstNum = count * 10 / 100;
        int secondNum = count * 20 / 100;
        int thirdNum = count * 30 / 100;
        List<Work> works1 = userMapper.selectWorkOrderByScore();
        int count1 = 1;
        int count2 = 1;
        int count3 = 1;
        List<Map<String, Object>> listmap = new ArrayList<>();
        for (Work work1 : works1) {
            User user = new User();
            Map<String, Object> map = new HashMap<>(16);
            if (count1 <= firstNum) {
                work1.setHonor("一等奖");
                userMapper.updateWorkByWorkId(work1);
                user.setWorkId(work1.getWorkId());
                List<User> users = userMapper.selectByCondition(user);
                if (users == null) {
                    throw new LibException("用户信息查询异常");
                }
                user = users.get(0);
                map.put("leaderName", user.getLeaderName());
                map.put("member1", user.getMember1());
                map.put("member2", user.getMember2());
                map.put("workName", work1.getWorkName());
                map.put("score", work1.getScore());
                map.put("honor", work1.getHonor());
                listmap.add(map);
                count1++;
            } else if (count2 <= secondNum) {
                work1.setHonor("二等奖");
                userMapper.updateWorkByWorkId(work1);
                user.setWorkId(work1.getWorkId());
                List<User> users = userMapper.selectByCondition(user);
                if (users == null) {
                    throw new LibException("用户信息查询异常");
                }
                user = users.get(0);
                map.put("leaderName", user.getLeaderName());
                map.put("member1", user.getMember1());
                map.put("member2", user.getMember2());
                map.put("workName", work1.getWorkName());
                map.put("score", work1.getScore());
                map.put("honor", work1.getHonor());
                listmap.add(map);
                count2++;
            } else if (count3 <= thirdNum) {
                work1.setHonor("三等奖");
                userMapper.updateWorkByWorkId(work1);
                user.setWorkId(work1.getWorkId());
                List<User> users = userMapper.selectByCondition(user);
                if (users == null) {
                    throw new LibException("用户信息查询异常");
                }
                user = users.get(0);
                map.put("leaderName", user.getLeaderName());
                map.put("member1", user.getMember1());
                map.put("member2", user.getMember2());
                map.put("workName", work1.getWorkName());
                map.put("score", work1.getScore());
                map.put("honor", work1.getHonor());
                listmap.add(map);
                count3++;
            } else {
                work1.setHonor("成功参与");
                userMapper.updateWorkByWorkId(work1);
                user.setWorkId(work1.getWorkId());
                List<User> users = userMapper.selectByCondition(user);
                if (users == null) {
                    throw new LibException("用户信息查询异常");
                }
                user = users.get(0);
                map.put("leaderName", user.getLeaderName());
                map.put("member1", user.getMember1());
                map.put("member2", user.getMember2());
                map.put("workName", work1.getWorkName());
                map.put("score", work1.getScore());
                map.put("honor", work1.getHonor());
                listmap.add(map);
            }
        }
        return new RestData(listmap);
    }

    @Override
    public RestData postNews(News news) throws LibException {
        int i = userMapper.insertNews(news);
        if (i > 0) {
            return new RestData(true);
        } else {
            throw new LibException("发布失败");
        }
    }

    @Override
    public RestData getNews() {
        List<News> list = userMapper.selectAllNews();
        List<Map<String, Object>> listmap = new ArrayList<>();
        for (News news :
                list) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("newsId", news.getNewsId());
            map.put("newsTime", news.getNewsTime());
            map.put("newsTitle", news.getNewsTitle());
            listmap.add(map);
        }
        return new RestData(listmap);
    }

    @Override
    public RestData getDetailNews(Integer newsId) throws LibException {
        Map<String, Object> map = new HashMap<>(16);
        News news = new News();
        news.setNewsId(newsId);
        List<News> listNews = userMapper.selectNewsById(news);
        if (listNews == null || listNews.size() != 1) {
            throw new LibException("查询异常");
        } else {
            map.put("newsId", listNews.get(0).getNewsId());
            map.put("newsTime", listNews.get(0).getNewsTime());
            map.put("newsTitle", listNews.get(0).getNewsTitle());
            map.put("newsContent", listNews.get(0).getNewsContent());
        }
        return new RestData(map);
    }

    @Override
    public RestData postStudentMessage(Student student) throws LibException {
        List<Student> students = userMapper.selectStudentByNumber(student);
        Map<String, Object> map = new HashMap<>(16);
        if (students.size() == 0) {
            student.setType(1);
            int i = userMapper.insertStudent(student);
            List<Student> students1 = userMapper.selectStudentNum();
            if (i > 0) {
                map.put("num", students1.size());
                return new RestData(map);
            } else {
                throw new LibException("签到异常，请重新签到");
            }
        } else {

            throw new LibException("请勿重复签到");
        }

    }
}
