import java.util.Scanner;

public class Index {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to JobiFy");

        String choose = "test";

        while (!choose.equals("exit")) {

            System.out.println("New User? Enter 1 to Register yourself...");
            System.out.println("Already a user? Enter 2 login into your account");
            System.out.println("Want to make changes to your profile? Enter 3");
            System.out.println("Want to delete your account? Enter 4");
            System.out.println("Type 'exit' to finish :");

            if (sc.hasNext()) {
                int choice = Integer.parseInt(sc.next());

                if (choice == 1) {
                    Register.Signup();
                    Login.log();
                    JobAPply.apply();
                } else if (choice == 2) {
                    Login.log();
                    JobAPply.apply();
                } else if (choice == 3) {
                    Login.log();
                    UpdateUser.execute();
                } else if (choice == 4) {
                    Login.log();
                    DeleteUser.execute();
                }
                
//                FinalStore.store();
            }

            System.out.println("Type something to continue or type 'exit' to finish :");
            if (sc.hasNext()) {
                choose = sc.next();
            } else {
                choose = "exit"; // If there is no input, consider it as 'exit'.
            }
        }
    }
}
