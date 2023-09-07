package models;

public interface IParseModel<T> {
    T parse(String line);
}
