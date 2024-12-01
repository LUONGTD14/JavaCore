package Bai14;

public class NormalStudent extends Student{
    private int englishScore, entryTestScore;

    public NormalStudent(int englishScore, int entryTestScore) {
        super();
        this.englishScore = englishScore;
        this.entryTestScore = entryTestScore;
    }

    @Override
    public void ShowMyInfor() {
        String info = String.format("Normal student: %s, %s, %s, %s, %s, %s, %d, %d",
                getFullName(), getDoB(), getSex(), getPhoneNumber(), getUniversityName(),
                getGradeLevel(), getEnglishScore(), getEntryTestScore());
        System.out.println(info);
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }

    public int getEntryTestScore() {
        return entryTestScore;
    }

    public void setEntryTestScore(int entryTestScore) {
        this.entryTestScore = entryTestScore;
    }
}
