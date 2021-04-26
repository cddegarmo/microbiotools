package appclasses;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.time.Period;
import java.util.stream.Collectors;

public class Sample {

    private static int samplesReceived = 0;
    private static final List<Sample> SAMPLES = new ArrayList<>();

    private final Patient patient;
    private final Specimen specimen;
    private final List<Test> tests;
    private final LocalDate received;
    private boolean evaluated = false;
    private final Predicate<Test> conditions = (Test t) -> {
        LocalDate obtained = getReceived();
        LocalDate collected = getSpecimen().getCollected();
        Period between = Period.between(collected, obtained);
        int days = between.getDays();
        boolean expired = days > t.getExam().getMaxDays();
        boolean notEnough = getSpecimen().getAmount() < t.getExam().getMassReqd();
        if (expired || notEnough)
            return false;
        else
            return true;
    };

    public Sample(Patient patient,
                  Specimen specimen,
                  List<Test> tests) {
        this.patient = patient;
        this.specimen = specimen;
        this.tests = new ArrayList<>(tests);
        if (tests.size() == 0)
            throw new IllegalArgumentException("At least one test must be requested.");
        received = IsoChronology.INSTANCE.dateNow();

        ++samplesReceived;
        SAMPLES.add(this);
    }

    public static int getSamplesReceived()  { return samplesReceived; }
    public static List<Sample> getSamples() { return SAMPLES;         }

    public Patient getPatient()    { return patient;   }
    public Specimen getSpecimen()  { return specimen;  }

    public List<Test> getTests() {
        return new ArrayList<>(tests);
    }

    public LocalDate getReceived() { return received;  }
    public boolean isEvaluated()   { return evaluated; }

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

    private List<Test> testsApproved() {
        List<Test> approved = tests.stream()
             .filter(conditions)
             .collect(Collectors.toList());
        return new ArrayList<>(approved);
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
                             receivedFormatter(), tests.toString(), testsApproved().toString());
    }
}
