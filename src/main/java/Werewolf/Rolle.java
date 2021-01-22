package Werewolf;

public enum Rolle {
    WERWOLF("Werwolf", StateMachine.WERWOLF, "<:Werwolf:802194079983206411>"),
    HEXE("Hexe", StateMachine.HEXE, "<:Hexe:802194078656888843>"),
    SEHER("Seher", StateMachine.SEHER, "<:Seher:802194080314556516>"),
    ARMOR("Armor", StateMachine.ARMOR, "<:Armor:802194079919767632>"),
    DORFBEWOHNER("Dorfbewohner", StateMachine.VOTING, "<:Dorfbewohner:802194080590856252>");

    private String name;
    private StateMachine state;
    private String emoji;

    Rolle(String name, StateMachine state, String emoji) {
        this.name = name;
        this.state = state;
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return name;
    }

    public StateMachine getState() {
        return state;
    }

    public String getEmoji() {
        return emoji;
    }
}
