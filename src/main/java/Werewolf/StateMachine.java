package Werewolf;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public enum StateMachine {
    IDLE {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            return LOBBY;
        }
    },
    LOBBY {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            werwolf.initRollen();
            if (werwolf.isFirstNight()) {
                werwolf.sendNarrator("Das Dorf schläft ein. Es war ein langer Tag. Die Müdigkeit überkommt euch schnell");
                werwolf.getVote().setRolle(Rolle.ARMOR);
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Der Armor");
                eb.setDescription("Als Armor bestimmst du ein Liebespaar. Wenn bei dem Liebespaar eine Person stirbt, stirbt auch die andere. " +
                        "Wenn das Liebespaar am Ende als Einzige überleben haben sie gewonnen");
                eb.setColor(Color.MAGENTA);
                eb.setFooter("Holen Sie sich Cisco Packet Tracer für Outlook!");
                werwolf.armorChannel.sendMessage(eb.build()).queue();
                werwolf.getVote().initVoting();
                return ARMOR;
            }
            werwolf.sendNarrator("Das Dorf schläft ein. Es war ein langer Tag für euch");
            werwolf.getVote().setRolle(Rolle.WERWOLF);
            werwolf.getVote().initVoting();
            return WERWOLF;
        }
    },
    NACHT {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            return this;
        }
    },
    WERWOLF {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Hexe
            werwolf.getVote().setRolle(Rolle.HEXE);
            werwolf.getVote().initVoting();
            return HEXE;
        }
    },
    ARMOR {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zum Werwolf
            werwolf.getVote().setRolle(Rolle.WERWOLF);
            werwolf.getVote().initVoting();
            return WERWOLF;
        }
    },
    HEXE {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Seher
            werwolf.getVote().setRolle(Rolle.SEHER);
            werwolf.getVote().initVoting();
            return SEHER;
        }
    },
    SEHER {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Tag
            if (werwolf.bgTot()) {
                return VOTING_BUERGERMEISTER;
            }
            werwolf.getVote().setRolle(Rolle.DORFBEWOHNER);
            werwolf.getVote().initVoting();
            return VOTING;
        }
    },
    TAG {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Hier wird auf Ende gecheckt!
            if (werwolf.bgTot()) {
                return VOTING_BUERGERMEISTER;
            }
            werwolf.getVote().setRolle(Rolle.DORFBEWOHNER);
            werwolf.getVote().initVoting();
            return VOTING;
        }
    },
    VOTING {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Nacht "CHECK WIN"
            werwolf.incRounds();
            return NACHT;
        }
    },
    VOTING_BUERGERMEISTER {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Voting
            return VOTING;
        }
    };


    public abstract StateMachine newState(Werwolf werwolf);
}
