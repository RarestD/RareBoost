package rare.creations.RareBoost.view;

import org.springframework.context.ConfigurableApplicationContext;
import rare.creations.RareBoost.entity.Status;

import java.util.ArrayList;
import java.util.Scanner;

public class MainView {


    ConfigurableApplicationContext applicationContext;

    ConstellationView constellationView;


    Scanner scanner = new Scanner(System.in);

    public MainView(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.constellationView = applicationContext.getBean(ConstellationView.class);
    }

    public void MainView(String username, String password){
        menuloop :
        while (true) {
            System.out.println("Welcome to RareBoost");
            System.out.println("Created by Rare");
            System.out.println("Menu");
            System.out.println("1. List of Constellations");
            System.out.println("2. Add a Constellations");
            System.out.println("3. Remove a Constellations");
            System.out.println("4. Edit a Constellations");
            System.out.println("5. Remove all Constellations");
            System.out.println("6. Start all Constellations");
            System.out.println("7. Stop all Constellations");
            System.out.println("8. Start a Constellations");
            System.out.println("9. Stop a Constellations");
            System.out.println("0. About The Application");
            System.out.println("x. Exit");

            String menu = inputText("Pilihlah sebuah menu");
            switch (menu) {
                case "0" -> {
                    System.out.println("<================================>");
                    System.out.println("RareBoost is an application that made for promoting in discord automaticly");
                    System.out.println("RareBoost is a paid application that base monthly");
                    System.out.println("RareBoost is created by rare.envy in discord");
                    System.out.println("if you want to you can contact 081384330998 for external info");
                    System.out.println("<================================>");
                }
                case "1" -> constellationView.showAllConstellation();
                case "2" -> constellationView.addConstellation();
                case "3" -> constellationView.removeConstellation();
                case "4" -> constellationView.editConstellation();
                case "5" -> constellationView.removeAllConstellation();
                case "6" -> constellationView.startAllConstellation();
                case "7" -> constellationView.stopAllConstellation();
                case "8" -> constellationView.startConstellation();
                case "9" -> constellationView.stopConstellation();
                case "x" -> {
                    break menuloop;
                }
            }
        }
    }

    public String inputText(String info){
        System.out.print(info + " : ");
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public Long inputLong(String info){
        System.out.print(info + " : ");
        scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    public Integer inputInt(String info){
        System.out.print(info + " : ");
        scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
