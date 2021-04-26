package appclasses;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Sample {

    private static int samplesReceived = 0;
    private static final List<Sample> samples = new ArrayList<>();

    private final Patient patient;
    private final Specimen specimen;
    private final List<Test> tests;
    private final LocalDate received;
    private boolean evaluated = false;

    public Sample(Patient patient,
                  Specimen specimen,
                  List<Test> tests) {
        this.patient = patient;
        this.specimen = specimen;
        this.tests = tests;
        received = IsoChronology.INSTANCE.dateNow();

        ++samplesReceived;
        samples.add(this);
    }

    public static int getSamplesReceived()  { return samplesReceived;    }
    public static List<Sample> getSamples() { return samples;            }

    public Patient getPatient()             { return patient;            }
    public List<Test> getTests()            { return tests;              }
    public LocalDate getReceived()          { return received;           }
    public boolean isEvaluated()            { return evaluated;          }

    private String receivedFormatter() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy");
            String out = received.format(dtf);
            return out;
        } catch (DateTimeException exc) {
            System.out.printf("%s cannot be formatted", received);
            throw exc;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Sample))
            return false;
        Sample sm = (Sample) o;
        return sm.patient.equals(this.patient) &&
                sm.tests.equals(this.tests) &&
                sm.received.equals(this.received);
    }

    @Override
    public int hashCode() {
        int result = patient.hashCode();
        result = 31 * result + tests.hashCode();
        result = 31 * result + received.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Sample no. %d%nFrom patient: %s%n" +
             "Specimen: %s%nReceived on %s%nTests requested: %s%nTests approved: %s",
                             samplesReceived, patient.toString(), specimen.toString(),
                             receivedFormatter(), tests.toString(), );
    }
}
