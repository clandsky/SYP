package testbench.datenverwaltung.dateiverwaltung.entities;

/**
 * Created by CGrings on 03.12.2015.
 */
public class JoinDefGrenz
{
    private LongLongGrenz fromAId;
    private LongLongGrenz toAId;
    private String regName;
    private JoinType joinType;

    public JoinDefGrenz(LongLongGrenz fromAId, LongLongGrenz toAId, String regName, JoinType joinType)
    {
        this.fromAId = fromAId;
        this.toAId = toAId;
        this.regName = regName;
        this.joinType = joinType;
    }

    public JoinType getJoinType()
    {
        return joinType;
    }

    public void setJoinType(JoinType joinType)
    {
        this.joinType = joinType;
    }

    public String getRegName()
    {
        return regName;
    }

    public void setRegName(String regName)
    {
        this.regName = regName;
    }

    public LongLongGrenz getToAId()
    {
        return toAId;
    }

    public void setToAId(LongLongGrenz toAId)
    {
        this.toAId = toAId;
    }

    public LongLongGrenz getFromAId()
    {
        return fromAId;
    }

    public void setFromAId(LongLongGrenz fromAId)
    {
        this.fromAId = fromAId;
    }

    public enum JoinType
    {
        JTDEFAILT, JTOUTER
    }
}
