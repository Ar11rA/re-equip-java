package shared;

public class ExamplePOJO {
    public int field1;
    public String field2;

    public ExamplePOJO() {
    }

    public ExamplePOJO(int field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
