package testbench.datenverwaltung.dateiverwaltung.grenz;

/**
 * Created by CGrings on 03.12.2015.
 */
public class SelItemGrenz
{
    private SelOpCodeGrenz operator;
    private SelValueExtGrenz value;

    public SelItemGrenz(SelOpCodeGrenz operator, SelValueExtGrenz value)
    {
        this.operator = operator;
        this.value = value;
    }

    public SelOpCodeGrenz getOperator()
    {
        return operator;
    }

    public void setOperator(SelOpCodeGrenz operator)
    {
        this.operator = operator;
    }

    public SelValueExtGrenz getValue()
    {
        return value;
    }

    public void setValue(SelValueExtGrenz value)
    {
        this.value = value;
    }
}
