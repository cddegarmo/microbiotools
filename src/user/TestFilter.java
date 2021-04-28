package user;

import appclasses.*;
import appclasses.Specimen.*;
import appclasses.Test.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class serving as user interface through command line
public class TestFilter {

   private static LocalDate dateInput(String input) {
      try {
         DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d, yyyy");
         LocalDate ld = LocalDate.parse(input, df);
         return ld;
      } catch (DateTimeParseException exc) {
         System.out.printf("%s is not parsable!%n", input);
         throw exc;
      }
   }

   private static Source getSource(int n) {
      return Source.values()[n - 1];
   }

   private static Matter getMatter(int n) {
      return Matter.values()[n - 1];
   }

   private static Microbe getMicrobe(int n) {
      return Microbe.values()[n - 1];
   }

   private static Type getType(int n) {
      return Type.values()[n - 1];
   }

   public static void main(String[] args) {
      Scanner s = new Scanner(System.in);

      System.out.print("Please enter patient's last name: ");
      String lastName = s.nextLine();
      Patient patient = Patient.addRecord(lastName);

      System.out.print("Enter the source of specimen\n(1 for Skin)\n(2 for Muscle)\n" +
           "(3 for Bone)\n(4 for Organ)\n");
      int source = s.nextInt();
      Source sr = getSource(source);

      System.out.print("Enter the material type\n(1 for Fluid)\n(2 for Tissue)\n");
      int material = s.nextInt();
      Matter mt = getMatter(material);

      System.out.print("How many tests have been requested? ");
      int tests = s.nextInt();
      int no = 1;
      List<Test> tsts = new ArrayList<>();
      while (tests > 0) {
         System.out.format("TEST NO. %d%n", no++);
         System.out.print("Enter the microbe to check for\n(1 for Virus)\n(2 for Parasite)\n" +
              "(3 for Bacterium)\n(4 for Fungus)\n");
         int microbe = s.nextInt();
         Microbe mb = getMicrobe(microbe);

         System.out.print("Enter the type of test to conduct\n(1 for Culture)\n(2 for Screen)\n" +
              "(3 for Direct)\n(4 for Stain)\n");
         int type = s.nextInt();
         Type tp = getType(type);

         Test ts = new Test(mb, tp);
         tsts.add(ts);
         tests--;
      }

      System.out.println("Battery of tests requested: ");
      System.out.println(tsts.toString());

      System.out.print("What is the mass of the sample? ");
      int mass = s.nextInt();
      s.nextLine();

      System.out.print("What date was the specimen collected (ex. Jun 4, 1991)? ");
      LocalDate collected = dateInput(s.nextLine());

      Specimen sp = Specimen.harvest(sr, mt, collected, mass);
      Sample sm = new Sample(patient, sp, tsts);
      System.out.println(sm.toString());
   }
}
