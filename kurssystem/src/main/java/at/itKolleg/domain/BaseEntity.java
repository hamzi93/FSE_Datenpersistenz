package at.itKolleg.domain;

public abstract class BaseEntity {

    private Long id;

    public BaseEntity(Long id) {
        setId(id);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id == null || id >= 0) //null -> es soll möglich sein, Domaenenklasse zu erzeugen um über die Datenbank eine id zu bekommen (insert)
        {
            this.id = id;
        } else {
            throw new InvalidValueExeption("Kurs-ID muss größer gleich 0 sein!");
        }
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
