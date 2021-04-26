package appclasses;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Specimen {

    public enum Source {
        SKIN, MUSCLE, BONE, ORGAN
    }

    public enum Matter {
        FLUID, TISSUE
    }

    private final Source from;
    private final Matter material;
    private final LocalDate collected;
    private final int amount;   // in grams

    private Specimen(Source from,
                     Matter material,
                     LocalDate collected,
                     int mass) {
        this.from = from;
        this.material = material;
        this.collected = collected;
        amount = mass;
    }

    public static Specimen harvest(Source from, Matter material,
                                   LocalDate collected, int mass) {
        return new Specimen(from, material, collected, mass);
    }

    public Source getSource()       { return from;      }
    public Matter getMaterial()     { return material;  }
    public LocalDate getCollected() { return collected; }
    public int getAmount()          { return amount;    }

    private String collectedFormatter() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy");
            String out = collected.format(dtf);
            return out;
        } catch (DateTimeException exc) {
            System.out.printf("%s cannot be formatted", collected);
            throw exc;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s collected on %s", from, material, collectedFormatter());
    }
}
