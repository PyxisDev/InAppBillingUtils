package com.github.windsekirun.inappbillingutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.windsekirun.inappbillingtest.InAppBillingUtils;
import com.github.windsekirun.inappbillingtest.model.Sku;
import com.github.windsekirun.inappbillingtest.model.Transaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InAppBillingUtils.OnInAppBillingCallback, InAppBillingUtils.OnInAppConsumeCallback {
    InAppBillingUtils utils;
    String item1Sku = "item_1";
    String item2Sku = "item_2";
    String item3Sku = "item_3";

    Button btn1;
    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        utils = new InAppBillingUtils(this, "");
        utils.setOnInAppBillingCallback(this);
        utils.setOnInAppConsumeCallback(this);

        utils.initInAppBilling();

        ArrayList<String> items = new ArrayList<>();
        items.add(item1Sku);
        items.add(item2Sku);
        items.add(item3Sku);

        ArrayList<Sku> availablePackage = utils.getAvailableInappPackage(items);
        for (Sku sku : availablePackage) {
            Log.d(sku.getProductId(), sku.getPrice() + ": " + sku.getTitle() + " : " + sku.getType());
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.purchase(item1Sku);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.purchase(item2Sku);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.purchase(item3Sku);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.unBindInAppBilling();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (utils != null) {
            utils.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void purchaseDone(Transaction transaction) {
        Log.d("Purchase", "Success!!");
        Log.d("Purchase", transaction.getPurchaseInfo());
        Log.d("Purchase", transaction.getDataSignature());
        Log.d("Purchase", transaction.getPurchaseToken());
    }

    @Override
    public void purchaseFailed(int responseCode) {
        Log.d("Purchase", "Failed!!");
    }

    @Override
    public void consumeDone(Transaction transaction) {
        Log.d("Consume", "Consume success!!");
        Log.d("Consume", transaction.getPurchaseInfo());
        Log.d("Consume", transaction.getDataSignature());
        Log.d("Consume", transaction.getPurchaseToken());
    }

    @Override
    public void consumeFailed(int responseCode, Transaction transaction) {
        Log.d("Consume", "Failed!!");
    }
}
