package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String Name,String Mobile) throws Exception{
        if(userMobile.contains(Mobile)){
            throw new Exception("User already exists");
        }
        userMobile.add(Mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        if(users.size()==2){
            String nm= users.get(1).toString();
            int num=users.size();
            Group  g=new Group(nm,num);
            groupUserMap.put(g,users);
            return g;
        }
        customGroupCount++;
        String nm="Group "+customGroupCount;
        int num=users.size();
        Group g=new Group(nm, num);
        groupUserMap.put(g,users);
        return g;
    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.
        messageId++;
        String mid=""+messageId;
        //Message m=new Message(messageId,content);
        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(groupUserMap.containsKey(group) && !groupUserMap.get(group).contains(sender)){
            throw new Exception("You are not allowed to send message");
        }
        if(!groupMessageMap.containsKey(group)){
            List<Message> n=new ArrayList<Message>();
            n.add(message);
            groupMessageMap.put(group,n);
            return groupMessageMap.get(group).size();
        }
        groupMessageMap.get(group).add(message);
        //groupMessageMap.put(group,n);
        return groupMessageMap.get(group).size();

    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and
        // the admin rights are transferred from approver to user.

        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(approver==groupUserMap.get(group).get(1)){
            throw new Exception("Approver does not have rights");
        }
        if(!groupUserMap.get(group).contains(user)){
            throw new Exception("User is not a participant");
        }

        groupUserMap.get(group).add(0,user);
        return "SUCCESS";

    }

    public int removeUser(User user) throws Exception{
        //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)

        return 1;
    }

    public String findMessage(Date start, Date end, int K) throws Exception{
        //This is a bonus problem and does not contains any marks
        // Find the Kth latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception

        return "";
    }
}