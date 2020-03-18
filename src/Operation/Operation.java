package Operation;

/**
 * Created by kadko on 3/16/2020.
 */
public class Operation {
    private String date;
    private String description;
    private Double summ;

    public Operation(String date, String description, Double summ) {
        this.date = date;
        this.description = description;
        this.summ = summ;
    }

    public String getString() {
        return date;
    }

    public void setString (String String) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Double getSumm() {
        return summ;
    }

    public void setSumm (Double summ) {
        this.summ = summ;
    }
}
