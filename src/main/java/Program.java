import entity.Role;
import entity.User;
import repository.UserRepository;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        bai2();
        bai3(2);
        bai4(6);
        bai6();


    }

    static void bai2() {
        System.out.println("----------------Bai2----------------");
        List<User> all = UserRepository.findAll();
        System.out.printf("%-10s | %-20s | %-15s\n", "ID", "FULL_NAME", "EMAIL");
        System.out.println("____________________________________________________________");
        for (User user : all) {
            System.out.printf("%-10s | %-20s | %-15s\n", user.getUserId(), user.getFullName(), user.getEmail());
        }
    }

    static void bai3(int id) {
        System.out.println();
        System.out.println("----------------Bai3----------------");
        User user = UserRepository.findById(id);
        System.out.println(user);
    }

    static void bai4(int id) {
        System.out.println();
        System.out.println("----------------Bai4----------------");
        boolean deleted = UserRepository.deleteUser(id);
        if (deleted) {
            System.out.println("Delete thành công");
        } else {
            System.out.println("Delete thất bại");
        }
    }




    static void bai6() {
        System.out.println();
        System.out.println("----------------Bai6----------------");
        User user = new User();
        user.setFullName("Truong Nguyen");
        user.setEmail("truong@gmail.com");
        user.setPassword("ABC123");
        user.setRole(Role.EMPLOYEE);
        user.setExpInYear(2);
        user.setProSkill("java-sql");
        UserRepository.createUser(user);

    }


}


