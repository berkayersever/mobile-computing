package Library;

import Users.Admin;
import Users.Reader;
import Users.SortUser;
import Users.User;

import java.util.*;

public class Lib {

    private HashMap<Book, HashSet<User>> bookMap;
    private HashMap<User, HashSet<Book>> userMap;

    public Lib() {
        bookMap = new HashMap<>();
        userMap = new HashMap<>();
    }

    public void rentBook(User u, Book b) {
        if(bookMap.containsKey(b)) { bookMap.get(b).add(u); }
        else    { throw new IllegalArgumentException("No such book in the system"); }
    }

    public void printRentBooks​(Reader u) {
        if(u.getClass() == Reader.class) {
            for(Map.Entry<Book, HashSet<User>> entry : bookMap.entrySet()) {
                if(entry.getValue().contains(u))
                    System.out.println(entry.getKey());
            }
        }
    }

    public void printRentBooks(Admin u) {
        if(u.getClass() == Admin.class) {
            for(Map.Entry<Book, HashSet<User>> entry : bookMap.entrySet())
                System.out.println(entry.getKey().getBookName() + ' ' + entry.getValue());
        }
    }

    public void addBook(User u,Book b)
    {
        if(bookMap.containsKey(b)){
            b.setUnits(b.getUnits()+1);
        }
        else
            bookMap.put(b,new HashSet<User>());
    }

    
    public void addUser(User u)
    {
        if(!userMap.containsKey(u)){
            userMap.put(u,new HashSet<Book>());
        }
        else
            throw new IllegalArgumentException("User already exists");
    }


    public void deleteBook(User u,Book b)
    {
        if(bookMap.containsKey(b)){
            bookMap.remove(b);
        }
        else
            throw new IllegalArgumentException("No such book in the system");
    }

   public void printAllUsers()
   {
       List<User> users = new ArrayList<>(userMap.keySet());
       Collections.sort(users, SortUser::compare​);
       for(User u: users)   { System.out.println(u); }
   }

    public void printAllBooks()
    {
        List<Book> books = new ArrayList<>(bookMap.keySet());
        Collections.sort(books, Book::compareTo);
        for(Book b: books) { System.out.println(b); }
    }
}