package edu.washington.yujia1.quizdroid2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jia on 2015/5/17.
 */
public class UserSettingActivity extends PreferenceActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings);


    }

}
