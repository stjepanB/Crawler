package util;

public class FakeStorage implements IStorage {

    @Override
    public void save(String title, String author, String text) {
        System.out.println("Spremam ove podatke: ");
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
        System.out.println("===================================");

    }

    @Override
    public void save(String title, String author, String text, String imgLink) {

    }
}
