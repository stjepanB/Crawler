package util;

public interface IStorage
{

     void save(String title,String author,String text);

     void save(String title,String author,String text,String imgLink);
}
