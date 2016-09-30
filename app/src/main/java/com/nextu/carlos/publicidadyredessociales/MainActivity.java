package com.nextu.carlos.publicidadyredessociales;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private LoginButton loginB;
    private CallbackManager cM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        cM = CallbackManager.Factory.create();

        getFbKeyHash("2jmj7l5rSw0yVb/vlWAYkK/YBwk=\n");

        setContentView(R.layout.activity_main);
        loginB = (LoginButton)findViewById(R.id.login_button);

        loginB.registerCallback(cM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String answer = "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Intento de inicio de sesión fallido.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFbKeyHash(String packageName) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cM.onActivityResult(requestCode, resultCode, data);
    }
}
