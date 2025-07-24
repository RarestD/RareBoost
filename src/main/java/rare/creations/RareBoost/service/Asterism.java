package rare.creations.RareBoost.service;


import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import rare.creations.RareBoost.controllers.ConstellationScreenController;
import rare.creations.RareBoost.entity.Constellation;
import rare.creations.RareBoost.entity.ModifierException;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Asterism {

    private Random random =  new Random();

    @Getter
    public List<Thread> asterism = new ArrayList<>(4);

    @Getter
    public List<Constellation> asterismConfig =  new ArrayList<>(4);

    ConfigurableApplicationContext applicationContext;

    RequestServices requestServices;

    @Autowired
    public Asterism(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.requestServices = applicationContext.getBean(RequestServices.class);
    }

    private Thread assertTask(Constellation constellation, ConstellationScreenController controller){
        return new Thread(() -> {
            while (true){
                try {
                    requestServices.sendConstellation(constellation);
                    Duration delay = constellation.getDelay();
                    Thread.sleep(delay.toMillis());
                } catch (InterruptedException e) {
                    System.out.println("A constellation has been stopped...!");
                    break;
                } catch (WebClientResponseException e){
                    Platform.runLater(() -> {
                        if (e.getMessage().contains("401")){
                            controller.changeStatus(controller.exclamation, "Authorization is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("404")){
                            controller.changeStatus(controller.exclamation, "Channel is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("403")){
                            controller.changeStatus(controller.exclamation, "Channel is Forbidden", "noteBoxes", controller.exclamationFill);
                        }else  {
                            System.out.println(e.getMessage());
                            controller.changeStatus(controller.exclamation, "Unkown Error", "noteBoxes", controller.exclamationFill);
                        }

                    });
                    break;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
        });
    }

    private Thread assertTaskScheduler(Constellation constellation, ConstellationScreenController controller, LocalTime endTime, ZoneId zoneId){
        return new Thread(() -> {
            while (true){
                try {
                    if (isFinnishTime(endTime, zoneId)){
                        controller.changeStatus(controller.stickNotes, "Scheduler is Complete", "noteBoxesInfo", controller.stickFill);
                        break;
                    }
                    requestServices.sendConstellation(constellation);
                    Duration delay = constellation.getDelay();
                    if (isFinnishTimeExtended(endTime, zoneId, delay)){
                        controller.changeStatus(controller.stickNotes, "Scheduler is Complete", "noteBoxesInfo", controller.stickFill);
                        break;
                    }
                    Thread.sleep(delay.toMillis());
                } catch (InterruptedException e) {
                    System.out.println("A constellation has been stopped...!");
                    break;
                } catch (WebClientResponseException e){
                    Platform.runLater(() -> {
                        if (e.getMessage().contains("401")){
                            controller.changeStatus(controller.exclamation, "Authorization is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("404")){
                            controller.changeStatus(controller.exclamation, "Channel is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("403")){
                            controller.changeStatus(controller.exclamation, "Channel is Forbidden", "noteBoxes", controller.exclamationFill);
                        }else  {
                            System.out.println(e.getMessage());
                            controller.changeStatus(controller.exclamation, "Unkown Error", "noteBoxes", controller.exclamationFill);
                        }
                        // UI-related code that modifies the JavaFX components
                        // This code will be executed on the JavaFX Application thread

                    });
                    break;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
        });
    }

    private Thread assertTaskRandom(Constellation constellation, ConstellationScreenController controller){
        return new Thread(() -> {
            while (true){
                try {
                    int i = random.nextInt(constellation.getDelayMin(), constellation.getDelayMax());
                    Duration delay = null;
                    switch (constellation.getDelayDuration()){
                        case "S" -> {delay = Duration.ofSeconds(i);}
                        case "M" -> {delay = Duration.ofMinutes(i);}
                        case "H" -> {delay = Duration.ofHours(i);}
                        default -> {
                            System.out.println("not found");
                        }
                    }
                    requestServices.sendConstellation(constellation);
                    if (delay != null) {
                        System.out.println(delay);
                        Thread.sleep(delay.toMillis());
                    }else {
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println("A constellation has been stopped...!");
                    break;
                } catch (WebClientResponseException e){
                    Platform.runLater(() -> {
                        if (e.getMessage().contains("401")){
                            controller.changeStatus(controller.exclamation, "Authorization is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("404")){
                            controller.changeStatus(controller.exclamation, "Channel is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("403")){
                            controller.changeStatus(controller.exclamation, "Channel is Forbidden", "noteBoxes", controller.exclamationFill);
                        }else  {
                            System.out.println(e.getMessage());
                            controller.changeStatus(controller.exclamation, "Unkown Error", "noteBoxes", controller.exclamationFill);
                        }
                        // UI-related code that modifies the JavaFX components
                        // This code will be executed on the JavaFX Application thread

                    });
                    break;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
        });
    }

    private Thread assertTaskRandomScheduler(Constellation constellation, ConstellationScreenController controller, LocalTime endTime, ZoneId zoneId){
        return new Thread(() -> {
            while (true){
                try {
                    if (isFinnishTime(endTime, zoneId)){
                        controller.changeStatus(controller.stickNotes, "Scheduler is Complete", "noteBoxesInfo", controller.stickFill);
                        break;

                    }
                    int i = random.nextInt(constellation.getDelayMin(), constellation.getDelayMax());
                    Duration delay = null;
                    switch (constellation.getDelayDuration()){
                        case "S" -> {delay = Duration.ofSeconds(i);}
                        case "M" -> {delay = Duration.ofMinutes(i);}
                        case "H" -> {delay = Duration.ofHours(i);}
                        default -> {
                            System.out.println("not found");
                        }
                    }
                    requestServices.sendConstellation(constellation);
                    if (delay != null) {
                        if (isFinnishTimeExtended(endTime, zoneId, delay)){
                            controller.changeStatus(controller.stickNotes, "Scheduler is Complete", "noteBoxesInfo", controller.stickFill);
                            break;
                        }
                        System.out.println(delay);
                        Thread.sleep(delay.toMillis());
                    }else {
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println("A constellation has been stopped...!");
                    break;
                } catch (WebClientResponseException e){
                    Platform.runLater(() -> {
                        if (e.getMessage().contains("401")){
                            controller.changeStatus(controller.exclamation, "Authorization is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("404")){
                            controller.changeStatus(controller.exclamation, "Channel is Invalid", "noteBoxes", controller.exclamationFill);
                        }else if (e.getMessage().contains("403")){
                            controller.changeStatus(controller.exclamation, "Channel is Forbidden", "noteBoxes", controller.exclamationFill);
                        }else  {
                            System.out.println(e.getMessage());
                            controller.changeStatus(controller.exclamation, "Unkown Error", "noteBoxes", controller.exclamationFill);
                        }
                        // UI-related code that modifies the JavaFX components
                        // This code will be executed on the JavaFX Application thread

                    });
                    break;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    controller.changeStatus(controller.exclamation, "Unkown Error", "noteBoxes", controller.exclamationFill);
                    break;
                }
            }
        });
    }

    public void addConstellation(Constellation constellation, ConstellationScreenController controller){
        Thread newConstellation = assertTask(constellation, controller);
        if (constellation.getName().equals("Constellation ")){
            int size = asterism.size() + 1;
            constellation.setName(String.valueOf(size));
        }
        newConstellation.setName(constellation.getName());
        asterism.add(newConstellation);
        asterismConfig.add(constellation);
    }

    private Thread schedulerStart(int index, Constellation constellation, ConstellationScreenController controller, LocalTime targetTime, LocalTime endTime, Boolean randomness){
        return new Thread(() -> {
            ZoneId zoned = ZoneId.of("Asia/Jakarta");
            ZonedDateTime now = ZonedDateTime.now(zoned);
            if (isWithinRange(now.toLocalTime(), targetTime, endTime)) {
                System.out.println("The current time is within the specified range.");
                if (!randomness) {
                    Thread newThread = assertTaskScheduler(constellation, controller, endTime, zoned);
                    asterism.set(index, newThread);
                    asterismConfig.set(index, constellation);
                    startConstellation(index);
                    controller.changeStatus(controller.stickNotes, "Constellation Running", "noteBoxesRun", controller.stickStart);
                }else {
                    Thread newThread = assertTaskRandomScheduler(constellation, controller, endTime, zoned);
                    asterism.set(index, newThread);
                    asterismConfig.set(index, constellation);
                    startConstellation(index);
                    controller.changeStatus(controller.stickNotes, "Constellation Running", "noteBoxesRun", controller.stickStart);
                }
                return;
            } else {
                System.out.println("The current time is outside the specified range.");
            }
            ZonedDateTime tDT = ZonedDateTime.of(now.toLocalDate(), targetTime, zoned).plusSeconds(2);
            if (now.toLocalTime().isAfter(targetTime)){
                tDT = tDT.plusDays(1);
            }
            Duration between = Duration.between(now, tDT);
            try {
                controller.changeStatus(controller.stickNotes, "Scheduler is Waiting", "noteBoxesInfo", controller.stickFill);
                Thread.sleep(between.toMillis());
                if (!randomness) {
                    Thread newThread = assertTaskScheduler(constellation, controller, endTime, zoned);
                    asterism.set(index, newThread);
                    asterismConfig.set(index, constellation);
                    startConstellation(index);
                    controller.changeStatus(controller.stickNotes, "Constellation Running", "noteBoxesRun", controller.stickStart);
                }else {
                    Thread newThread = assertTaskRandomScheduler(constellation, controller, endTime, zoned);
                    asterism.set(index, newThread);
                    asterismConfig.set(index, constellation);
                    startConstellation(index);
                    controller.changeStatus(controller.stickNotes, "Constellation Running", "noteBoxesRun", controller.stickStart);
                }
            } catch (InterruptedException e) {
                System.out.println("A constellation has been stopped...!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }

    public void checkAllConstellation(){
        for (int i = 1; i < asterism.size()+1; i++) {
            System.out.println(i+". " + asterism.get(i-1).getName());
        }
    }

    public void editFullConstellationSchduler(int index, Constellation constellation, ConstellationScreenController controller, LocalTime targetTime, LocalTime endTime){
        Thread thread = asterism.get(index);
        if (!thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }
        thread.setName(constellation.getName());
        Thread newThread = schedulerStart(index, constellation, controller,targetTime, endTime, false);
        asterism.set(index, newThread);
        asterismConfig.set(index, constellation);
    }

    public void editFullConstellationSchdulerRandom(int index, Constellation constellation, ConstellationScreenController controller, LocalTime targetTime, LocalTime endTime){
        Thread thread = asterism.get(index);
        if (!thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }
        thread.setName(constellation.getName());
        Thread newThread = schedulerStart(index, constellation, controller,targetTime, endTime, true);
        asterism.set(index, newThread);
        asterismConfig.set(index, constellation);
    }

    public void editFullConstellation(int index, Constellation constellation, ConstellationScreenController controller){
        Thread thread = asterism.get(index);
        if (!thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }
        thread.setName(constellation.getName());
        Thread newThread = assertTask(constellation, controller);
        asterism.set(index, newThread);
        asterismConfig.set(index, constellation);
    }

    public void editFullConstellationRandom(int index, Constellation constellation, ConstellationScreenController controller){
        Thread thread = asterism.get(index);
        if (!thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }
        thread.setName(constellation.getName());
        Thread newThread = assertTaskRandom(constellation, controller);
        asterism.set(index, newThread);
        asterismConfig.set(index, constellation);
    }

    public void setConstellationName(int index, String name){
        String finalName = "Constellation " + name;
        asterismConfig.get(index).setName(name);
        asterism.get(index).setName(finalName);
    }

    public void startAllConstellation(){
        asterism.forEach(Thread::start);
    }

    public void stopAllConstellation(){
        asterism.forEach(Thread::interrupt);
    }

    public void removeAllConstellation(){
        for (int i = 0; i < asterism.size(); i++) {
            asterism.remove(i);
            asterismConfig.remove(i);
        }
    }

    public void startConstellation(int index){
        asterism.get(index).start();
    }

    public void stopConstellation(int index){
        if (!asterism.get(index).isInterrupted()){
            asterism.get(index).interrupt();
        }
    }

    public void removeConstellation(int index){
        asterism.remove(index);
        asterismConfig.remove(index);
    }

    private boolean isWithinRange(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    private static boolean isFinnishTime(LocalTime endTime, ZoneId zoneId){
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        if (now.toLocalTime().isAfter(endTime)){
            return true;
        }
        return false;
    }

    private static boolean isFinnishTimeExtended(LocalTime endTime, ZoneId zoneId, Duration delay){
        ZonedDateTime now = ZonedDateTime.now(zoneId).plusSeconds(delay.toSeconds());
        if (now.toLocalTime().isAfter(endTime)){
            return true;
        }
        return false;
    }

}
