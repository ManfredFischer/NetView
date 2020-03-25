package de.netview.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Objects;

@Entity
@Table(name = "DOCUMENT")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, precision = 10)
    private Long did;
    private Long hid;
    private String number;
    private String date;
    private Blob file;

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(did, document.did) &&
                Objects.equals(number, document.number) &&
                Objects.equals(date, document.date) &&
                Objects.equals(file, document.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(did, number, date, file);
    }
}
