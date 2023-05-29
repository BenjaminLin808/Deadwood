package benlinkurgra.deadwood.controller;

public enum ActionsEnum {
    INVALID(-1),
    MOVE(1),
    TAKE_ROLE(2),
    ACT(3),
    REHEARSE(4),
    UPGRADE(5),
    END_TURN(6);

    private final int value;

    ActionsEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ActionsEnum valueOf(int value) {
        for (ActionsEnum action : values()) {
            if (action.getValue() == value) {
                return action;
            }
        }
        System.out.println("\n\n\nValue: " + value);
        return INVALID;
    }
}
