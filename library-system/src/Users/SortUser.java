package Users;

public class SortUser {

    public static int compare​(User user1, User user2){

        int result = user1.getUserName().compareTo(user2.getUserName());
        if(result > 0)      {   return 1;   }
        else if(result < 0) {   return -1;  }
        else                {   return 0;   }
    }
}