package ng.tellerassist.tellerassist.enums;

public enum UserRoleEnum {

    ADMINISTRATOR(1,"ROLE_ADMIN"), USER(0,"ROLE_USER");

    private UserRoleEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    private String description;
    private int value;
}
