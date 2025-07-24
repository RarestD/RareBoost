package rare.creations.RareBoost.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import rare.creations.RareBoost.entity.Constellation;
import rare.creations.RareBoost.service.Asterism;

import java.util.Scanner;

@Component
public class ConstellationView {

    ConfigurableApplicationContext applicationContext;
    Asterism asterism;

    public ConstellationView(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.asterism = applicationContext.getBean(Asterism.class);
    }

    private Scanner scanner = new Scanner(System.in);

    public void addConstellation(){
        while (true) {
            String constellationName = inputText("Massukan nama constellation (opsional)");

            String auth = inputText("Massukan authorization key anda");
            if (auth.isEmpty() | auth.isBlank()) {
                System.out.println("Authorization tidak boleh kosong");
                continue;
            }

            String channel = inputText("Massukan id channelnya");
            if (channel.isBlank() | channel.isEmpty()) {
                System.out.println("Id channel tidak boleh kosong");
                continue;
            }

            Long delay = inputLong("Massukan delay promote (in seconds)");
            if (delay == null) {
                System.out.println("delay tidak boleh kosong");
                continue;
            }

            String message = inputText("Massukan message yang ingin di send");
            if (message.isBlank() | message.isEmpty()) {
                System.out.println("message tidak boleh kosong");
                continue;
            }
            System.out.println("===============================");

            Constellation constellation = new Constellation();
            constellation.setName(constellationName);
            constellation.setAuth(auth);
            constellation.setDestination(channel);
            //constellation.setDelay(delay);
            constellation.setMessage(message);
            //asterism.addConstellation(constellation);
            break;

        }
    }


    public void editConstellation(){
        while (true) {
            System.out.println("<================================>");
            Integer index = inputInt("Massukan nomor constellation mana yang ingin diganti");
            if (index == null) {
                System.out.println("index tidak boleh kosong");
                continue;
            }
            Constellation constellation = asterism.getAsterismConfig().get(index);

            String constellationName = inputText("Massukan nama constellation (Kosongkan jika tidak ingin diganti)");
            if (!constellationName.isBlank() | !constellationName.isEmpty()){
                constellation.setName(constellationName);
            }

            String auth = inputText("Massukan authorization key anda (Kosongkan jika tidak ingin diganti)");
            if (!auth.isEmpty() | !auth.isBlank()) {
                constellation.setAuth(auth);
            }

            String channel = inputText("Massukan id channelnya (Kosongkan jika tidak ingin diganti)");
            if (!channel.isBlank() | !channel.isEmpty()) {
                constellation.setDestination(channel);
            }

            String delay = inputText("Massukan delay promote (in seconds) (Kosongkan jika tidak ingin diganti)");
            if (!delay.isBlank() | !delay.isEmpty()) {
                //constellation.setDelay(Long.parseLong(delay));
            }

            String message = inputText("Massukan message yang ingin di send (Kosongkan jika tidak ingin diganti)");
            if (!message.isBlank() | !message.isEmpty()) {
                constellation.setMessage(message);
            }

            //asterism.editFullConstellation(index, constellation);
            asterism.getAsterismConfig().set(index, constellation);
            System.out.println("<================================>");
            break;
        }
    }

    public void showAllConstellation(){
        System.out.println("<================================>");
        System.out.println("Here's all The Constellations");
        asterism.checkAllConstellation();
        System.out.println("<================================>");
    }

    public void startConstellation(){
        while (true) {
            System.out.println("<================================>");
            Integer index = inputInt("Massukan nomor constellation mana yang ingin diganti");
            if (index == null) {
                System.out.println("index tidak boleh kosong");
                continue;
            }

            asterism.startConstellation(index);
            System.out.println("<================================>");
            break;
        }
    }

    public void stopConstellation(){
        while (true) {
            System.out.println("<================================>");
            Integer index = inputInt("Massukan nomor constellation mana yang ingin diganti");
            if (index == null) {
                System.out.println("index tidak boleh kosong");
                continue;
            }

            asterism.stopConstellation(index);
            System.out.println("<================================>");
            break;
        }
    }

    public void removeConstellation(){
        while (true) {
            System.out.println("<================================>");
            Integer index = inputInt("Massukan nomor constellation mana yang ingin diganti");
            if (index == null) {
                System.out.println("index tidak boleh kosong");
                continue;
            }

            asterism.removeConstellation(index);
            System.out.println("<================================>");
            break;
        }
    }

    public void startAllConstellation(){
        System.out.println("<================================>");
        System.out.println("Starting All Constellations.....~!");
        asterism.startAllConstellation();
        System.out.println("<================================>");
    }

    public void stopAllConstellation(){
        System.out.println("<================================>");
        asterism.stopAllConstellation();
        System.out.println("All constellations has been stopped..!");
        System.out.println("<================================>");
    }

    public void removeAllConstellation(){
        System.out.println("<================================>");
        asterism.removeAllConstellation();
        System.out.println("All constellation has been removed.....!");
        System.out.println("<================================>");
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
