package com.dropwizard.GameAuth.resources.dao;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import com.dropwizard.GameAuth.resources.representations.GameUserInfo;

//Database of all users which are constructed into a hashmap by ID and name
public class GameUserDB {

	public static HashMap<Integer, GameUserInfo> gameUserInfos = new HashMap<>();
    static{
        gameUserInfos.put(1, new GameUserInfo(1, "Lokesh", "Gupta", "India"));
        gameUserInfos.put(2, new GameUserInfo(2, "John", "Gruber", "USA"));
        gameUserInfos.put(3, new GameUserInfo(3, "Melcum", "Marshal", "AUS"));
    }
    //public list of the user info
    public static List<GameUserInfo> getGameUsers(){
        return new ArrayList<GameUserInfo>(gameUserInfos.values());
    }
    //process to obtain the id of the specified game user
    public static GameUserInfo getGameUser(Integer id){
        return gameUserInfos.get(id);
    }
    //updates the game user at specific id and gameuserinfo
    public static void updateGameUser(Integer id, GameUserInfo gameUserInfo){
        gameUserInfos.put(id, gameUserInfo);
    }
    //removes the game user at the specified id
    public static void removeGameUser(Integer id){
        gameUserInfos.remove(id);
    }
}
