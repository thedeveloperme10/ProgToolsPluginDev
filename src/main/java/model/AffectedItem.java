package model;

public class AffectedItem {

    String affected;

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    @Override
    public String toString() {
        return "AffectedItem{" +
                "affected='" + affected + '\'' +
                '}';
    }
}
