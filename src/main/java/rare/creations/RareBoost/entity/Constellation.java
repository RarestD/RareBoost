package rare.creations.RareBoost.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Setter
@Getter
public class Constellation {

    private String name = "Constellation "; // constellation name
    private String auth; // authorization key
    private String destination; // for a channel id in server
    private Duration delay; // for a delay (seconds) each messages
    private String message; // clearly what it means
    private int delayMin;
    private int delayMax;
    private String delayDuration;



    public void setName(String name){
        this.name = "Constellation " + name;
    }


}
