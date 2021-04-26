package user;

import appclasses.*;
import appclasses.Specimen.*;
import appclasses.Test.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestFilter {
   public static void main(String[] args) {
      Patient patient = Patient.addRecord("Wilson");
      LocalDate collected = LocalDate.of(2021, 4, 11);
      Specimen specimen = Specimen.harvest(Source.BRAIN, Matter.TISSUE, collected, 48);
      Test test1 = new Test(Microbe.BACTERIUM, Type.CULTURE);
      Test test2 = new Test(Microbe.PARASITE, Type.STAIN);
      Test test3 = new Test(Microbe.VIRUS, Type.DIRECT);
      Test test4 = new Test(Microbe.VIRUS, Type.SCREEN);
      List<Test> tests = new ArrayList<>();
      tests.add(test1);
      tests.add(test2);
      tests.add(test3);
      tests.add(test4);
      Sample sample = new Sample(patient, specimen, tests);
      System.out.println(sample.toString());
   }
}
