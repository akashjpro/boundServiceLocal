package com.adida.aka.boundservicelocal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Aka on 7/13/2017.
 */

public class SumNumberService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SumBinder();
    }

    public int add(int numA, int numB ){
        return numA + numB;
    }

    public class SumBinder extends Binder{
        public SumNumberService getService(){
            return SumNumberService.this;
        }
    }
}
