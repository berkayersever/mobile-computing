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

    //rentBook method comes here
    public void rentBook​(Lib lib, Book b)
    {
        if(bookMap.containsKey(b)){
            bookMap.remove(b);
        }
        else
            throw new IllegalArgumentException("No such book in the system");
    }


    //printRentBooks method 1 comes here

    //printRentBooks method 2 comes here
    

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



   //method printAllUsers comes here

   //method printAllBooks comes here

}



















