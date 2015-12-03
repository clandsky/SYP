package testbench.datenverwaltung.dateiverwaltung.grenz;

/**
 * Created by CGrings on 03.12.2015.
 */
public class TS_UnionGrenz
{
    private Object object;
    private DataType dataType;

    public TS_UnionGrenz(Object data, DataType dataType)
    {
        this.object = data;
        this.dataType = dataType;
    }

    public Object getData()
    {
        return object;
    }

    public void setData(Object data, DataType dataType )
    {
        this.object = data;
        this.dataType = dataType;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public enum DataType
    {
        DT_STRING, DT_SHORT, DT_FLOAT, DT_BYTE, DT_BOOLEAN, DT_LONG, DT_DOUBLE,
        DT_LONGLONG, DT_COMPLEX, DT_DCOMPLEX, DT_DATE, DT_BYTESTR, DT_BLOB,
        DS_STRING, DS_SHORT, DS_FLOAT, DS_BYTE, DS_BOOLEAN, DS_LONG, DS_DOUBLE,
        DS_LONGLONG, DS_COMPLEX, DS_DCOMPLEX, DS_DATE, DS_BYTESTR,
        DT_EXTERNALREFERENCE, DS_EXTERNALREFERENCE, DT_ENUM, DS_ENUM
    }
}
