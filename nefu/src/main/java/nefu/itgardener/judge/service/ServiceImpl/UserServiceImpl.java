package nefu.itgardener.judge.service.ServiceImpl;

import nefu.itgardener.judge.common.LibException;
import nefu.itgardener.judge.common.Page;
import nefu.itgardener.judge.common.util.PageUtil;
import nefu.itgardener.judge.common.util.TokenUtil;
import nefu.itgardener.judge.core.mapper.UserMapper;
import nefu.itgardener.judge.core.model.User;
import nefu.itgardener.judge.core.model.Work;
import nefu.itgardener.judge.core.model.WorkVo;
import nefu.itgardener.judge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (works.get(0).getScore() == null) {
                throw new LibException("评委老师未公布分数");
            }
            rtv.put("leaderName", user1.getLeaderName());
            rtv.put("member1", user1.getMember1());
            rtv.put("member2", user1.getMember2());
            rtv.put("workName", works.get(0).getWorkName());
            rtv.put("link", works.get(0).getLink());
            rtv.put("workUrl", works.get(0).getWorkUrl());
            rtv.put("score", works.get(0).getScore());
            rtv.put("honor", works.get(0).getHonor());
            return rtv;
        }

    }

    @Override
    public List<Map<String, Object>> getWorkList(WorkVo workVo) throws LibException {
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
            Map<String, Object> map = new HashMap<>(4);
            map.put("nowPage", page.getNowPage());
            map.put("eachPage", page.getPageSize());
            map.put("totalPage", page.getTotalPage());
            map.put("totalSize", page.getTotalSize());
            rtv.add(map);
            return rtv;
        } else {
            throw new LibException("查询失败");
        }

    }
}
