package Werewolf;

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
            return START;
        }
    },
    START {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            return SETPLAYERROLES;
        }
    },
    SETPLAYERROLES {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            werwolf.initRollen();
            return NACHT;
        }
    },
    NACHT {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            if (werwolf.isFirstNight()) {
                return ARMOR;
            }
            return WERWOLF;
        }
    },
    WERWOLF {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Hexe
            return HEXE;
        }
    },
    ARMOR {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zum Werwolf
            return WERWOLF;
        }
    },
    HEXE {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Seher
            return SEHER;
        }
    },
    SEHER {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Voting wird aufgerufen, erst wenn das fertig ist, gehe ich zur Tag
            return TAG;
        }
    },
    TAG {
        @Override
        public StateMachine newState(Werwolf werwolf) {
            //TODO Hier wird auf Ende gecheckt!
            if (werwolf.bgTot()) {
                return VOTING_BUERGERMEISTER;
            }
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
