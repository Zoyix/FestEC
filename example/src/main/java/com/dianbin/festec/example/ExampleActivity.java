package com.dianbin.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dianbin.latte.activities.ProxyActivity;
import com.dianbin.latte.app.Latte;
import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.launcher.LauncherDelegate;
import com.dianbin.latte.ec.launcher.LauncherScrollDelegate;

public class ExampleActivity extends ProxyActivity {
    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}

