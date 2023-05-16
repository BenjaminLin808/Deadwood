package benlinkurgra.deadwood.controller;

public enum Action {
    INVALID(-1),
    MOVE(1),
    TAKE_ROLE(2),
    ACT(3),
    REHEARSE(4),
    UPGRADE(5),
    END_TURN(6);

    private final int value;

    Action(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Action valueOf(int value) {
        for (Action action : values()) {
            if (action.getValue() == value) {
                return action;
            }
        }
        System.out.println("\n\n\nValue: " + value);
        return INVALID;
    }
}
