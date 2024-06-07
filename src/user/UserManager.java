package user;

import tradable.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UserManager {
    private HashMap<String, User> users = new HashMap<>();

    private static UserManager instance;

    private UserManager() {
    }
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    public void init(String[] usersIn) throws InvalidInput, DataValidationException {
        for (String user : usersIn) {
            if(user == null || user.isEmpty()) {
                throw new DataValidationException("User cannot be null or empty");
            }
            users.put(user, new User(user));
        }
    }
    public User getRandomUser(){
        if(users.isEmpty()) {
            return null;
        }
        ArrayList<User> userList = new ArrayList<>(users.values());
        Random rand = new Random();
        int index = rand.nextInt(userList.size());
        return userList.get(index);
    }
    public void addToUser(String userId, TradableDTO o) throws DataValidationException {
        if(userId == null || userId.isEmpty()) {
            throw new DataValidationException("User ID cannot be null or empty");
        }
        if(!users.containsKey(userId)) {
            throw new DataValidationException("User not found");
        }
        if(o == null) {
            throw new DataValidationException("Tradable object cannot be null");
        }
        users.get(userId).addTradable(o);
    }
    public User getUser(String userId) {
        if(userId == null || userId.isEmpty()) {
            return null;
        }
        if(!users.containsKey(userId) ||  users.get(userId) == null) {
            return null;
        }
        return users.get(userId);
    }

    @Override
    public String toString() {
        String result = "";
        for(User user : users.values()) {
            result += user.toString() + "\n";
        }
        return result;
    }
}
