import Operation.Operation;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamMraz on 3/16/2020.
 */
public class Source {
    public static ArrayList<Operation> operations = new ArrayList<>();
    public static ArrayList<String> expenseList = new ArrayList<>();
    public static Path dateOfOperations = Paths.get("data\\movementList.csv");

    public static void pars () {
        try {
            List<String> lines = Files.readAllLines(dateOfOperations);
            for (String line : lines) {
                String[] detales = line.split(",");
                if (detales[0].equals("Тип счёта")) {
                    continue;
                }
                if (detales.length == 9) {
                    if (detales[6].charAt(0) == '\"') {
                        detales[6] = detales[6].replaceAll("\"", "");
                        detales[6] = detales[6] + "." + detales[7].replaceAll("\"", "");
                        detales[7] = "0";
                    }
                    else {
                        detales[7] = detales[7].replaceAll("\"", "");
                        detales[7] = detales[7] + "." + detales[8].replaceAll("\"", "");
                        detales[8] = "0";
                    }
                }
                else if (detales.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                Double summ = Double.parseDouble(detales[6]) + Double.parseDouble(detales[7]) * (-1);
                int i = 0;
                String[] detalesOfExpTemp = detales[5].split("[\\\\/]");
                String[] detalesOfExp = detalesOfExpTemp[detalesOfExpTemp.length - 1].split("  +");
                detalesOfExp[0] = detalesOfExp[0].trim();
                detales[5] = detalesOfExp[0];
                final Double index = 0.0;
                if (summ.compareTo(index) < 0) {
                    for (String exp : expenseList) {
                        if (exp.equals(detales[5])) {
                            i = 1;
                        }
                    }
                    if (i == 0) {
                        expenseList.add(detales[5]);
                    }
                }
                operations.add(new Operation(detales[3], detales[5], summ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Double getAllIncome () {
        Double resullt = 0.0;
        for (Operation op : operations) {
            resullt += (op.getSumm() > 0) ? op.getSumm() : 0.0;
        }
        return resullt;
    }

    public static Double getAllExpense () {
        Double resullt = 0.0;
        for (Operation op : operations) {
            resullt += (op.getSumm() < 0) ? op.getSumm() : 0.0;
        }
        return (resullt * (-1));
    }

    public static Double getSummExpense (String description) {
        Double result = 0.0;
        final Double index = 0.0;
        for (Operation op : operations) {
            result += ((op.getDescription().equals(description)) && (op.getSumm() < index)) ? op.getSumm() : 0.0;
        }
        return (result * (-1));
    }

    public static void main (String[] args) {
        pars();
        System.out.println("Общий приход:\t" + getAllIncome() + "\nОбщий расход:\t" + getAllExpense());
        System.out.println("\nРасходы по категориям:");
        for (String exp : expenseList) {
            System.out.println(exp + ": " + getSummExpense(exp));
        }
    }
}
