package Library;

import java.util.Arrays;

public class Book implements Comparable<Book> {
  
    private String bookName;
 
    private String[] Authors;
 
    private int units;

    public Book(String name, String[] Authors, int units)
    {
      this.bookName = name;
      this.Authors  = Authors;
      if (units < 0) throw new IllegalArgumentException("Units can not be negative");
      else this.units = units;
    }

    public void setBookName(String name)
    {
        this.bookName = name;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setAuthors(String[] authors)
    {
        this.Authors = authors;
    }

    public String[] getAuthors()
    {
        return Authors;
    }

    public void setUnits(int units)
    {
        this.units = units;
    }

    public int getUnits()
    {
        return units;
    }

    @Override
    public String toString()
    {
        return "BookName: " + this.bookName + ". Authors: " + Arrays.toString(this.getAuthors()).replace("[", "").replace("]", "");
    }

    @Override
    public int compareTo(Book b)
    {
        int result = b.getBookName().compareTo(this.getBookName());
        if(result < 0)      {   return 1;   }
        else if(result > 0) {   return -1;  }
        else                {   return 0;   }
    }
}