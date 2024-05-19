package ru.mavist.roulette;

import android.util.DisplayMetrics;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.feed.*;
import org.jetbrains.annotations.Nullable;
import ru.mavist.roulette.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private ImageView rul;
    private Random random;
    private int old_deegre = 0;
    private int deegre = 0;
    private static final float FACTOR = 4.86f;

    private final String[] numbers = {"32 КРАСНЫЙ","15 ЧЕРНЫЙ","19 КРАСНЫЙ","4 ЧЕРНЫЙ",
            "21 КРАСНЫЙ","2 ЧЕРНЫЙ","25 КРАСНЫЙ","17 ЧЕРНЫЙ", "34 КРАСНЫЙ",
            "6 ЧЕРНЫЙ","27 КРАСНЫЙ","13 ЧЕРНЫЙ","36 КРАСНЫЙ","11 ЧЕРНЫЙ","30 КРАСНЫЙ",
            "8 ЧЕРНЫЙ","23 КРАСНЫЙ","10 ЧЕРНЫЙ","5 КРАСНЫЙ","24 ЧЕРНЫЙ","16 КРАСНЫЙ","33 ЧЕРНЫЙ",
            "1 КРАСНЫЙ","20 ЧЕРНЫЙ","14 КРАСНЫЙ","31 ЧЕРНЫЙ","9 КРАСНЫЙ","22 ЧЕРНЫЙ","18 КРАСНЫЙ",
            "29 ЧЕРНЫЙ","7 КРАСНЫЙ","28 ЧЕРНЫЙ","12 КРАСНЫЙ","35 ЧЕРНЫЙ","3 КРАСНЫЙ","26 ЧЕРНЫЙ","0"};


    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        setreklama("R-M-8742376-1", 450);
        SetLenta();
    }

    public void onClickStart(View view)
    {
        old_deegre = deegre % 360;
        deegre = random.nextInt(3600) + 720;
        RotateAnimation rotate = new RotateAnimation(old_deegre,deegre,RotateAnimation.RELATIVE_TO_SELF,
                0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(3600);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvResult.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvResult.setText(getResult(360 - (deegre % 360)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rul.startAnimation(rotate);

    }

    public void SetLenta(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int feedMarginDp = 24;
        int screenWidthDp = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        int cardWidthDp = screenWidthDp - 2 * feedMarginDp;
        double cardCornerRadius = 16.0;
        FeedAdAppearance feedAdAppearance = new FeedAdAppearance.Builder(cardWidthDp).setCardCornerRadius(cardCornerRadius).build();
        String AD_UNIT_ID = "demo-feed-yandex"; // For debugging reasons you can use "demo-feed-yandex"
        // R-M-8742376-2
        FeedAdRequestConfiguration feedAdRequestConfiguration = new FeedAdRequestConfiguration.Builder(AD_UNIT_ID).build();

        FeedAdLoadListener feedAdLoadListener = new FeedAdLoadListener() {
            @Override
            public void onAdLoaded() {
                // Called when additional ads are received for display
                Log.d("KUKUYAN", "loaded");
            }

            @Override
            public void onAdFailedToLoad(AdRequestError error) {
                // Called when additional ads request fails
                Log.d("KUKUYAN", "failed");

            }
        };

        FeedAd feedAd = new FeedAd.Builder(MainActivity.this, feedAdRequestConfiguration, feedAdAppearance).build();
        feedAd.setLoadListener(feedAdLoadListener);
        feedAd.preloadAd();

        FeedAdEventListener feedAdEventListener = new FeedAdEventListener() {
            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {
                Log.d("KUKUYAN", "impression");

            }

            @Override
            public void onAdClicked() {
                // Called when user clicked on the ad
                Log.d("KUKUYAN", "clicked");
            }

            public void onAdImpression(ImpressionData impressionData) {
                // Called when impression was observed
            }
        };

        FeedAdAdapter feedAdAdapter = new FeedAdAdapter(feedAd);
        feedAdAdapter.setEventListener(feedAdEventListener);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.feedRecyclerView.setAdapter(feedAdAdapter);
    }

    private void init()
    {
//        tvResult = findViewById(R.id.tvResult);
//        rul = findViewById(R.id.rul);
        random = new Random();

    }
    private String getResult(int deegre)
    {
        String text = "";

        int factor_x = 1;
        int factor_y = 3;
        for(int i = 0;i < 37; i++){
            if(deegre >= (FACTOR * factor_x) && deegre < (FACTOR * factor_y))
            {
                text = numbers[i];
            }
            factor_x += 2;
            factor_y += 2;
        }
        if(deegre >= (FACTOR * 73) && deegre < 360 || deegre >= 0 && deegre < (FACTOR * 1)) text = numbers[numbers.length - 1];

        return text;
    }

    public void setreklama(String baner, int size){
//        BannerAdView mBannerAdView = findViewById(R.id.banner_ad_view);
//        mBannerAdView.setAdUnitId(baner);
//        mBannerAdView.setAdSize(BannerAdSize.stickySize(this, size));
//        final AdRequest adRequest = new AdRequest.Builder().build();
//        mBannerAdView.loadAd(adRequest);
    }

}