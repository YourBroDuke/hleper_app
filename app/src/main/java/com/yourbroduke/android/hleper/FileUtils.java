package com.yourbroduke.android.hleper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yourbroduke.android.hleper.data.HleperUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    @SuppressLint("StaticFieldLeak")
    static private Context mMainContext;

    static {
        mMainContext = null;
    }

    public FileUtils(Context mainContext) {
        mMainContext = mainContext;
    }

    static public HleperUser readUserInfo() throws JSONException {
        String userJson = readFileData("user.json");

        if (userJson.equals("")) return new HleperUser();
        else {
            JSONObject userObject = new JSONObject(userJson);
            HleperUser crtUser = new HleperUser();

            crtUser.setmID(userObject.getInt("id"));
            crtUser.setmName(userObject.getString("name"));
            crtUser.setmEmail(userObject.getString("email"));
            crtUser.setmPhoneNumber(userObject.getString("phone"));
            crtUser.setmBalance(userObject.getDouble("balance"));

            return crtUser;
        }
    }

    static public void writeUserInfo(HleperUser crtUser) throws JSONException, IOException {
        JSONObject fileObj = new JSONObject();

        fileObj.put("id", crtUser.getmID());
        fileObj.put("name", crtUser.getmName());
        fileObj.put("email", crtUser.getmEmail());
        fileObj.put("phone", crtUser.getmPhoneNumber());
        fileObj.put("balance", crtUser.getmBalance());

        writeFileData("user.json", fileObj.toString());
    }

    static public void cleanUserInfo() throws IOException {
        writeFileData("user.json", "");
    }

    static private String readFileData(String fileName) {
        String result = "";

        try {
            FileInputStream fis = mMainContext.openFileInput(fileName);

            int length = fis.available();

            byte[] buffer = new byte[length];

            fis.read(buffer);

            result = new String(buffer, "UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    static private void writeFileData(String fileName, String content) throws IOException {
        FileOutputStream fos = mMainContext.openFileOutput(fileName, Context.MODE_PRIVATE);

        byte[] bytes = content.getBytes();

        fos.write(bytes);

        fos.close();
    }

    static public void setmMainContext(Context mainContext) {
        mMainContext = mainContext;
    }
}
