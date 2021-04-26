package appclasses;

public class Test {

    public enum Microbe {
        VIRUS, PARASITE, BACTERIUM, FUNGUS
    }

    public enum Type {
        CULTURE (50, 7),
        SCREEN (50, 10),
        DIRECT (30, 4),
        STAIN (45, 14);

        private final int massReqd;   // in grams
        private final int maxAge;     // in days

        Type(int mass, int age) {
            massReqd = mass;
            maxAge = age;
        }
        public int getMassReqd() { return massReqd; }
        public int getMaxDays()  { return maxAge;   }
    }

    private final Microbe potentialCause;
    private final Type exam;

    public Test(Microbe checkFor, Type procedure) {
        potentialCause = checkFor;
        exam = procedure;
    }

    public Microbe getPotentialCause() { return potentialCause; }
    public Type getExam()              { return exam;           }

    public String toString() {
        return String.format("An exam is requested to check for a %s: %s%n",
                potentialCause, exam);
    }
}
