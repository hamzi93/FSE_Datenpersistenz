package at.itKolleg.domain;

public class InvalidValueExeption extends RuntimeException { // Runtime, wir möchten eine unchecked Exception haben
    public InvalidValueExeption(String message) {
        super(message);
    }
}
