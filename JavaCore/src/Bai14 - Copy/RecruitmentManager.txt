package Bai14;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecruitmentManager {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void showAllStudents() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                int nameComparison = s2.getFullName().compareTo(s1.getFullName());
                if (nameComparison != 0) return nameComparison;
                return s1.getPhoneNumber().compareTo(s2.getPhoneNumber());
            }
        });

        for (Student student : students) {
            student.ShowMyInfor();
        }
    }

    public List<Student> selectCandidates(int numberToHire) {
        List<GoodStudent> goodStudents = new ArrayList<>();
        List<NormalStudent> normalStudents = new ArrayList<>();

        for (Student student : students) {
            if (student instanceof GoodStudent) {
                goodStudents.add((GoodStudent) student);
            } else if (student instanceof NormalStudent) {
                normalStudents.add((NormalStudent) student);
            }
        }

        Collections.sort(goodStudents, new Comparator<GoodStudent>() {
            @Override
            public int compare(GoodStudent s1, GoodStudent s2) {
                int gpaComparison = Float.compare(s2.getGpa(), s1.getGpa());
                if (gpaComparison != 0) return gpaComparison;
                return s1.getFullName().compareTo(s2.getFullName());
            }
        });

        List<Student> selectedCandidates = new ArrayList<>();
        selectedCandidates.addAll(goodStudents.subList(0, Math.min(goodStudents.size(), numberToHire)));

        if (selectedCandidates.size() < numberToHire) {
            Collections.sort(normalStudents, new Comparator<NormalStudent>() {
                @Override
                public int compare(NormalStudent s1, NormalStudent s2) {
                    int testComparison = Float.compare(s2.getEntryTestScore(), s1.getEntryTestScore());
                    if (testComparison != 0) return testComparison;
                    int englishComparison = Integer.compare(s2.getEnglishScore(), s1.getEnglishScore());
                    if (englishComparison != 0) return englishComparison;
                    return s1.getFullName().compareTo(s2.getFullName());
                }
            });

            int remainingSlots = numberToHire - selectedCandidates.size();
            selectedCandidates.addAll(normalStudents.subList(0, Math.min(normalStudents.size(), remainingSlots)));
        }

        return selectedCandidates;
    }


}
