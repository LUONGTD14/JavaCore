package Bai14;

import java.util.ArrayList;
import java.util.Scanner;

public class Main implements InputException {
    public static void main(String[] args) throws InvalidFullNameException, InvalidDOBException, InvalidPhoneNumberException {
        ArrayList<GoodStudent> goodStudents = new ArrayList<>();
        ArrayList<NormalStudent> normalStudents = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap so sinh vien");
        int num = scanner.nextInt();
        if (num > 2) {
            System.out.println("so luong < 2");
            num = scanner.nextInt();
        } else {
            for (int i = 0; i < num; i++) {
                System.out.println("loai student ");
            }
        }
    }

    @Override
    public boolean invalidFullNameException(String fullName) {
        return fullName.length() >= 10 && fullName.length() <= 50;
    }

    @Override
    public boolean invalidDOBException(String doB) {
        return doB.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    }

    @Override
    public boolean invalidPhoneNumberException(String phoneNumber) {
        return phoneNumber.matches("^(090|098|091|031|035|038)\\\\d{7}$");
    }
}
