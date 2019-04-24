package nefu.itgardener.judge.core.model;

/**
 * @author : pc
 * @date : 2019/04/19
 * @since : Java 8
 */

public class User {
    /**
     * systemID
     */
    private Integer systemId;
    /**
     * 队长邮箱
     */
    private String userId;
    /**
     * 密码
     */
    private String password;
    /**
     * 对长姓名
     */
    private String leaderName;
    /**
     * 成员1
     */
    private String member1;
    /**
     * 成员2
     */
    private String member2;
    /**
     * 电话号
     */
    private String phone;
    /**
     * 用户类型S=学生 T=老师
     */
    private String type;
    /**
     * 年级
     */
    private String grade;
    /**
     * 专业
     */
    private String major;
    /**
     * 学校
     */
    private String college;
    /**
     * 作品编号
     */
    private Integer workId;
    /**
     * token
     */
    private String token;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
