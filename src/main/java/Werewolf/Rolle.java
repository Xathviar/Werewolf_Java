package Werewolf;

public enum Rolle {
    WERWOLF("Werwolf", StateMachine.WERWOLF),
    HEXE("Hexe", StateMachine.HEXE),
    SEHER("Seher", StateMachine.SEHER),
    ARMOR("Armor", StateMachine.ARMOR),
    DORFBEWOHNER("Dorfbewohner", StateMachine.VOTING);

    private String name;
    private StateMachine state;

    Rolle(String name, StateMachine state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }

    public StateMachine getState() {
        return state;
    }
}
