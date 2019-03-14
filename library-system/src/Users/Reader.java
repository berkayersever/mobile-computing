package Users;

import Library.Lib;
import Library.Book;

public class Reader extends User{

    public Reader(int id, String name)      { super(id, name); }

    public void printRentBooks(Lib lib)     { lib.printRentBooks​(this); }

    public void rentBook​(Lib lib, Book b) {
        if(b.getUnits() > 0)    { lib.rentBook(this, b); }
        else    { throw new IllegalArgumentException("No such book in the system"); }
    }

    @Override
    public String toString() {
        return "System user:" + this.getUserId() + " Name: " + this.getUserName() + " Registered on: " + this.getRegDate().toString() + " Status: Reader " ;
    }
}