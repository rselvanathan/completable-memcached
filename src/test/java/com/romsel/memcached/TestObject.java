package com.romsel.memcached;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Romesh Selvan
 */
public class TestObject implements Serializable {
    private String value;

    public TestObject(String value) {this.value = value;}

    public String getValue() {return value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestObject object = (TestObject) o;
        return Objects.equals(value, object.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TestObject{");
        sb.append("value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
