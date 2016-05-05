package com.worldline.techforum.obfuscation.api;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by Francois Lolom on 05/05/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
@Ignore
public class BaseUnitTest {

    @Before
    public void before() throws Exception {

        ShadowLog.stream = System.out;
    }


}
