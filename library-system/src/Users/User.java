package Users;

import Library.Lib;

import java.util.Date;


public abstract class User {
   
    private int userID;
    private String userName;
    private Date regDate;

    public User(int id, String name)
    {
        this.userID = id;
        this.userName = name;
        this.regDate = new Date();
    }


    abstract public void printRentBooks(Lib lib);

    public void setUserName(String name)
    {
      this.userName = name;
    }

    public String getUserName()
    {
       return userName;
    }

    public void setUserId(int id)
    {
        this.userID = id;
    }

    public int getUserId()
    {
        return userID;
    }

    public String toString() {
         return  "System user:" + userID + " Name: " + userName + " Registered on: " + regDate.toString() ;
    }
}
