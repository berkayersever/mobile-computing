import Library.Book;
import Library.Lib;
import Users.Admin;
import Users.Reader;
import Users.User;


public class Main {

    public static void main (String[] arg)
    {
        Lib lib = new Lib();
        String[] au = {"Ercan","Issa","Raul"};
        String[] an = {"Paul",};
        Book book1 = new Book("Alps History", au, 1);
        Book book2 = new Book( "Horizontal Motion", au, 3);
        Book book3 = new Book("Operating Systems", an, 4 );
        Book book4 = new Book( "Mobile Programming", an, 1);
        Book book5 = new Book( "Quantum Entanglement", au, 2 );


        User user1 = new Admin(1,"admin1" );
        User user2 = new Reader(2,"normal1");
        User user3 = new Reader(3,"normal2");

        lib.addUser(user1);
        lib.addUser(user2);
        lib.addUser(user3);

        ((Admin) user1).addBook(lib,book1);
        ((Admin) user1).addBook(lib,book2);
        ((Admin) user1).addBook(lib,book3);
        ((Admin) user1).addBook(lib,book4);
        ((Reader) user2).rentBook(lib,book1);
        ((Admin) user1).deleteBook(lib,book3);

        try {
            ((Reader) user3).rentBook(lib,book3);
        }catch(IllegalArgumentException e) {
            System.out.println("No such book in the system");
        }

        try {
            ((Reader) user2).rentBook(lib,book5);
        }catch(IllegalArgumentException e) {
            System.out.println("No such book in the system");
        }

        System.out.println("--------********-------");

        user1.printRentBooks(lib);

        System.out.println("--------********-------");

        user2.printRentBooks(lib);

        System.out.println("--------********-------");

        lib.printAllBooks();

        System.out.println("--------********-------");

        lib.printAllUsers();

    }
}
