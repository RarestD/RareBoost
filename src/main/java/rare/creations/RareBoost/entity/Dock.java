package rare.creations.RareBoost.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Dock<I, V1, V2>{

    private I i;
    private V1 v1;
    private V2 v2;

    Dock<I, V1, V2> of(I i, V1 v1, V2 v2){
        return new Dock<>(i, v1, v2);
    }

}
