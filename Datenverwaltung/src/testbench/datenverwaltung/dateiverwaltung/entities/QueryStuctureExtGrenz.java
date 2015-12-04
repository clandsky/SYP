package testbench.datenverwaltung.dateiverwaltung.entities;

/**
 * Created by CGrings on 03.12.2015.
 */
public class QueryStuctureExtGrenz
{
    private AIdNameSequenceGrenz aIdNameSequenceGrenz;
    private JoinDefSequenceGrenz joinDefSequenceGrenz;
    private SelAIdNameUnitIdSequenceGrenz selAIdNameUnitIdSequenceGrenz;
    private SelItemSequenceGrenz selItemSequenceGrenz;
    private SelOrderSequenceGrenz selOrderSequenceGrenz;

    public QueryStuctureExtGrenz(AIdNameSequenceGrenz aIdNameSequenceGrenz, JoinDefSequenceGrenz joinDefSequenceGrenz, SelAIdNameUnitIdSequenceGrenz selAIdNameUnitIdSequenceGrenz, SelItemSequenceGrenz selItemSequenceGrenz, SelOrderSequenceGrenz selOrderSequenceGrenz)
    {
        this.aIdNameSequenceGrenz = aIdNameSequenceGrenz;
        this.joinDefSequenceGrenz = joinDefSequenceGrenz;
        this.selAIdNameUnitIdSequenceGrenz = selAIdNameUnitIdSequenceGrenz;
        this.selItemSequenceGrenz = selItemSequenceGrenz;
        this.selOrderSequenceGrenz = selOrderSequenceGrenz;
    }

    public AIdNameSequenceGrenz getaIdNameSequenceGrenz()
    {
        return aIdNameSequenceGrenz;
    }

    public void setaIdNameSequenceGrenz(AIdNameSequenceGrenz aIdNameSequenceGrenz)
    {
        this.aIdNameSequenceGrenz = aIdNameSequenceGrenz;
    }

    public JoinDefSequenceGrenz getJoinDefSequenceGrenz()
    {
        return joinDefSequenceGrenz;
    }

    public void setJoinDefSequenceGrenz(JoinDefSequenceGrenz joinDefSequenceGrenz)
    {
        this.joinDefSequenceGrenz = joinDefSequenceGrenz;
    }

    public SelAIdNameUnitIdSequenceGrenz getSelAIdNameUnitIdSequenceGrenz()
    {
        return selAIdNameUnitIdSequenceGrenz;
    }

    public void setSelAIdNameUnitIdSequenceGrenz(SelAIdNameUnitIdSequenceGrenz selAIdNameUnitIdSequenceGrenz)
    {
        this.selAIdNameUnitIdSequenceGrenz = selAIdNameUnitIdSequenceGrenz;
    }

    public SelItemSequenceGrenz getSelItemSequenceGrenz()
    {
        return selItemSequenceGrenz;
    }

    public void setSelItemSequenceGrenz(SelItemSequenceGrenz selItemSequenceGrenz)
    {
        this.selItemSequenceGrenz = selItemSequenceGrenz;
    }

    public SelOrderSequenceGrenz getSelOrderSequenceGrenz()
    {
        return selOrderSequenceGrenz;
    }

    public void setSelOrderSequenceGrenz(SelOrderSequenceGrenz selOrderSequenceGrenz)
    {
        this.selOrderSequenceGrenz = selOrderSequenceGrenz;
    }
}
