/*
 * Copyright 2015-2016 TakWolf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zftlive.android.sample.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.widget.gesture.Lock9View;

public class LStyleActivity extends Activity {

    protected Lock9View lock9View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_style_lock);

        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        lock9View.setCallBack(new Lock9View.CallBack() {

            @Override
            public void onFinish(String password) {
                Toast.makeText(LStyleActivity.this, password, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
