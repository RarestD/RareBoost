package rare.creations.RareBoost.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataPair<I1, I2> {

    I1 item1;

    I2 item2;

}
