package nefu.itgardener.judge.web.api;

import nefu.itgardener.judge.common.LibException;
import nefu.itgardener.judge.common.RestData;
import nefu.itgardener.judge.core.model.*;
import nefu.itgardener.judge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author : pc
 * @date : 2019/04/21
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class UserApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestData postFile(@RequestParam("file") MultipartFile file, String link, String workName, String userId) {
        if (null == link || workName == null) {
            return new RestData(1, "不允许为空");
        }
        Work work = new Work();
        User user = new User();
        user.setUserId(userId);
        work.setLink(link);
        work.setWorkName(workName);

        if (file.isEmpty()) {
            return new RestData(1, "文件不存在");
        }
        try {

            //获得文件的字节流
            byte[] bytes = file.getBytes();
            String workUrl = "" + file.getOriginalFilename();
            work.setWorkUrl(workUrl);
            int i = userService.postUpload(work);
            user.setWorkId(i);
            boolean b = userService.putUserInfo(user);
            //获得path对象，也即是文件保存的路径对象
            Path path = Paths.get(workUrl);
            //调用静态方法完成将文件写入到目标路径
            Files.write(path, bytes);
            if (b) {
                return new RestData(true);
            } else {
                return new RestData(1, "更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new RestData(1, "上传失败");
        }

    }

    @RequestMapping(value = "/work/{userId}", method = RequestMethod.GET)
    public RestData getWork(@PathVariable String userId) {
        User user = new User();
        user.setUserId(userId);
        try {
            Map<String, Object> map = userService.getWorkByUserId(user);
            return new RestData(map);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/workList", method = RequestMethod.GET)
    public RestData getWorkList(WorkVo workVo) {
        logger.info("getWorkList" + workVo.toString());
        try {
            Map<String, Object> map = userService.getWorkList(workVo);
            return new RestData(map);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/workScore", method = RequestMethod.GET)
    public RestData getWorkScore(User user) {
        try {

            return userService.getWorkScore(user);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    public RestData postScore(@RequestBody Work work) {
        try {
            return userService.postWorkScore(work);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public RestData postPublishScore() {
        try {
            return userService.postPublishWorkScore();
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public RestData postNews(@RequestBody News news) {
        try {
            return userService.postNews(news);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public RestData getNews() {
        logger.info("getNews");
        return userService.getNews();

    }

    @RequestMapping(value = "/newsContent/{newsId}", method = RequestMethod.GET)
    public RestData getDetailsNews(@PathVariable Integer newsId) {
        try {
            return userService.getDetailNews(newsId);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }

    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public RestData postStudentMessage(@RequestBody Student student) {
        try {
            return userService.postStudentMessage(student);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }

    }
}
