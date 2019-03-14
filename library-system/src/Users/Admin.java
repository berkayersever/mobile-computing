package Users;

import Library.Lib;
import Library.Book;

public class Admin extends User{

    public Admin(int id, String name) {
        super(id, name);
    }

    @Override
    public void printRentBooks(Lib lib) {
        lib.printRentBooks(this);
    }

    public void addBook(Lib lib, Book b) {
        lib.addBook(this,b);
    }

    @Override
    public String toString() {
        return "System user:" + this.getUserId() + " Name: " + this.getUserName() + " Registered on: " + this.getRegDate().toString() + " Status: Admin " ;
    }

    public void deleteBook​(Lib lib, Book b) {
        if(b.getUnits() > 0)    { lib.deleteBook(this, b); }
        else    { throw new IllegalArgumentException("No such book in the system"); }
    }
}