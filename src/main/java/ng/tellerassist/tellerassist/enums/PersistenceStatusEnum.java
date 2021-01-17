
package ng.tellerassist.tellerassist.enums;

public enum PersistenceStatusEnum {
     SAVED(1);
    private PersistenceStatusEnum(int value )
    {
        this.value = value;
    }
    private final int value;

    public int getValue() {
        return value;
    }
    
}
