package rare.creations.RareBoost.entity;

import lombok.AllArgsConstructor;

public class ModifierException extends RuntimeException{

    public ModifierException(String message) {
        super(message);
    }
}
