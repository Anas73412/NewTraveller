package in.binplus.travel.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


import in.binplus.travel.LoginActivity;
import in.binplus.travel.MainActivity;

import static in.binplus.travel.Config.Constants.IS_LOGIN;
import static in.binplus.travel.Config.Constants.KEY_CAT;
import static in.binplus.travel.Config.Constants.KEY_CNT;
import static in.binplus.travel.Config.Constants.KEY_DATE;
import static in.binplus.travel.Config.Constants.KEY_EMAIL;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_ADD;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_NAME;
import static in.binplus.travel.Config.Constants.KEY_HOUSE;
import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_IMAGE;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_NAME;
import static in.binplus.travel.Config.Constants.KEY_PASSWORD;
import static in.binplus.travel.Config.Constants.KEY_PAYMENT_METHOD;
import static in.binplus.travel.Config.Constants.KEY_PINCODE;
import static in.binplus.travel.Config.Constants.KEY_REWARDS_POINTS;
import static in.binplus.travel.Config.Constants.KEY_SOCITY_ID;
import static in.binplus.travel.Config.Constants.KEY_SOCITY_NAME;
import static in.binplus.travel.Config.Constants.KEY_TIME;
import static in.binplus.travel.Config.Constants.KEY_USER_TYPE;
import static in.binplus.travel.Config.Constants.KEY_WALLET_Ammount;
import static in.binplus.travel.Config.Constants.PREFS_NAME;
import static in.binplus.travel.Config.Constants.PREFS_NAME2;
import static in.binplus.travel.Config.Constants.TOTAL_AMOUNT;


public class Session_management {

    SharedPreferences prefs;
    SharedPreferences prefs2;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;

    Context context;

    int PRIVATE_MODE = 0;

    public Session_management(Context context) {

        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();

        prefs2 = context.getSharedPreferences(PREFS_NAME2, PRIVATE_MODE);
        editor2 = prefs2.edit();

    }

    public void createLoginSession(String id, String email, String name
            , String mobile, String image, String wallet_ammount, String reward_point, String pincode, String franchise,
                                   String franchise_add, String type, String password) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_WALLET_Ammount, wallet_ammount);
        editor.putString(KEY_REWARDS_POINTS, reward_point);
        editor.putString(KEY_PINCODE, pincode);
        editor.putString(KEY_FRANCHISE_NAME,franchise) ;
        editor.putString( KEY_FRANCHISE_ADD,franchise_add );
        editor.putString(KEY_USER_TYPE,type);
        editor.putString(KEY_PASSWORD, password);



        editor.commit();
    }

    public void checkLogin() {

        if (!this.isLoggedIn()) {
            Intent loginsucces = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            loginsucces.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            loginsucces.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(loginsucces);
        }
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, prefs.getString(KEY_ID, null));
        // user email id
        user.put(KEY_EMAIL, prefs.getString(KEY_EMAIL, null));
        // user name
        user.put(KEY_NAME, prefs.getString(KEY_NAME, null));
        user.put(KEY_MOBILE, prefs.getString(KEY_MOBILE, null));
        user.put(KEY_IMAGE, prefs.getString(KEY_IMAGE, null));
        user.put(KEY_WALLET_Ammount, prefs.getString(KEY_WALLET_Ammount, null));
        user.put(KEY_REWARDS_POINTS, prefs.getString(KEY_REWARDS_POINTS, null));
        user.put(KEY_PAYMENT_METHOD, prefs.getString(KEY_PAYMENT_METHOD, ""));
        user.put(TOTAL_AMOUNT, prefs.getString(TOTAL_AMOUNT, null));
        user.put(KEY_PINCODE, prefs.getString(KEY_PINCODE, null));
        user.put(KEY_SOCITY_ID, prefs.getString(KEY_SOCITY_ID, null));
        user.put(KEY_SOCITY_NAME, prefs.getString(KEY_SOCITY_NAME, null));
        user.put(KEY_USER_TYPE, prefs.getString(KEY_USER_TYPE, null));
        user.put(KEY_PASSWORD, prefs.getString(KEY_PASSWORD, null));
        user.put( KEY_FRANCHISE_NAME,prefs.getString( KEY_FRANCHISE_NAME, null ) );
        user.put( KEY_FRANCHISE_ADD,prefs.getString( KEY_FRANCHISE_ADD,null ) );

        // return user
        return user;
    }

    public void updateData(String name, String mobile, String pincode
            , String socity_id, String image, String wallet, String rewards, String house) {

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_PINCODE, pincode);
        editor.putString(KEY_SOCITY_ID, socity_id);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_WALLET_Ammount, wallet);
        editor.putString(KEY_REWARDS_POINTS, rewards);
        editor.putString(KEY_HOUSE, house);

        editor.apply();
    }

    public void updateSocity(String socity_name, String socity_id) {
        editor.putString(KEY_SOCITY_NAME, socity_name);
        editor.putString(KEY_SOCITY_ID, socity_id);

        editor.apply();
    }
    public void updateWallet(String wallet) {
        editor.putString(KEY_WALLET_Ammount, wallet);

        editor.apply();
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();

        cleardatetime();

        Intent logout = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        logout.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        logout.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(logout);
    }

    public void logoutSessionwithchangepassword() {
        editor.clear();
        editor.commit();

        cleardatetime();

        Intent logout = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        logout.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        logout.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(logout);
    }

    public void creatdatetime(String date, String time) {
        editor2.putString(KEY_DATE, date);
        editor2.putString(KEY_TIME, time);

        editor2.commit();
    }

    public void cleardatetime() {
        editor2.clear();
        editor2.commit();
    }

    public HashMap<String, String> getdatetime() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_DATE, prefs2.getString(KEY_DATE, null));
        user.put(KEY_TIME, prefs2.getString(KEY_TIME, null));

        return user;
    }

    // Get Login State
    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }
    public void updateProfile(String image, String name, String cnt,String f_name ,String f_add ,String mobile ,String email)
    {
        editor.putString(KEY_IMAGE,image);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_CNT,cnt);
        editor.putString(KEY_FRANCHISE_NAME,f_name);
        editor.putString(KEY_FRANCHISE_ADD,f_add);
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    public HashMap<String, String> getUpdateProfile()
    {
        HashMap<String, String> map=new HashMap<>();
        map.put(KEY_IMAGE,prefs.getString(KEY_IMAGE,null));
        map.put(KEY_NAME,prefs.getString(KEY_NAME,null));
        map.put(KEY_EMAIL,prefs.getString(KEY_EMAIL,null));
        map.put(KEY_FRANCHISE_ADD,prefs.getString(KEY_FRANCHISE_ADD,null));
        map.put(KEY_FRANCHISE_NAME,prefs.getString(KEY_FRANCHISE_NAME,null));
        map.put(KEY_MOBILE,prefs.getString(KEY_MOBILE,null));
        map.put(KEY_CNT,prefs.getString(KEY_CNT,null));
        return map;
    }

    public void updateUserName(String name)
    {
        editor.putString(KEY_NAME,name);
        editor.commit();
    }
    public void setCategoryId(String id)
    {
        editor.putString(KEY_CAT,id);
        editor.commit();
    }

    public String getCategoryId()
    {
       return prefs.getString(KEY_CAT,"");
    }

}
