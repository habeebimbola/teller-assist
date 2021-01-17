package ng.tellerassist.tellerassist.enums;

public enum LoginTimeEnum {

    FIRST_TIME_LOGIN(1);

    public static LoginTimeEnum getFIRST_TIME_LOGIN() {
        return FIRST_TIME_LOGIN;
    }

    private LoginTimeEnum(int value) {
        this.value = value;
    }
    private int value;
}
