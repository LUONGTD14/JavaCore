package Bai14;

public class GoodStudent extends Student {
    private float gpa;
    private String bestRewardName;

    public GoodStudent(float gpa, String bestRewardName) {
        super();
        this.gpa = gpa;
        this.bestRewardName = bestRewardName;
    }

    public GoodStudent(String fullName, String doB, String sex, String phoneNumber, String universityName, String gradeLevel, float gpa, String bestRewardName) {
        super(fullName, doB, sex, phoneNumber, universityName, gradeLevel);
        this.gpa = gpa;
        this.bestRewardName = bestRewardName;
    }

    @Override
    public void ShowMyInfor() {
        String info = String.format("Good student: %s, %s, %s, %s, %s, %s, %.2f, %s",
                getFullName(), getDoB(), getSex(), getPhoneNumber(), getUniversityName(),
                getGradeLevel(), getGpa(), getBestRewardName());
        System.out.println(info);
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getBestRewardName() {
        return bestRewardName;
    }

    public void setBestRewardName(String bestRewardName) {
        this.bestRewardName = bestRewardName;
    }
}
