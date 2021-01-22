package Werewolf;

public enum Rolle {
    WERWOLF("Werwolf", StateMachine.WERWOLF),
    HEXE("Hexe", StateMachine.HEXE),
    SEHER("Seher", StateMachine.SEHER),
    ARMOR("Armor", StateMachine.ARMOR),
    DORFBEWOHNER("Dorfbewohner");

    private String name;
    private StateMachine[] state;

    Rolle(String name, StateMachine... state) {
        this.name = name;
        this.state = state;
    }
}
