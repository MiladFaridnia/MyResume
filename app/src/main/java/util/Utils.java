package util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import faridnia.milad.myresume.R;

/**
 * Created by Milad on 2016-04-17.
 */
public class Utils {

    public static final String PREFS_NAME = "UserInformation";
    public static final String ERROR = "Error";
    public static String ACCOUNT_TYPE = "com.iseemedia.account";

    public static void overrideFonts(final Context context, final View v) {

        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static void openURL(Context context, String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * Method for Setting the Height of the ListView dynamically.
     * *** Hack to fix the issue of not showing all the items of the ListView
     * *** when placed inside a ScrollView
     ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

//    public static void applyFontToMenuItem(Context context, MenuItem mi) {
//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf");
//        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        mNewTitle.setSpan(new ForegroundColorSpan(Color.WHITE), 0, mNewTitle.length(), 0);
//        mi.setTitle(mNewTitle);
//
//    }

    public static void animateRecyclerView(RecyclerView recyclerView) {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 50.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        recyclerView.setLayoutAnimation(controller);
    }


    //TODO Fix this for final versions
    public static String processException(Context context, String response, int responseCode) {
        if (response != null) {
            if (responseCode == 400)
                return response;
            if (response.contains("ConnectException"))
                return context.getString(R.string.no_network);
            else if (response.contains("SocketTimeoutException"))
                return context.getString(R.string.socket_timeout_exception);
            else if (responseCode == 401 || responseCode == 403)
                return context.getString(R.string.not_authorized);
            else if (response.contains("Unauthorized"))
                return context.getString(R.string.not_authorized);
            else if (responseCode == 500)
                return context.getString(R.string.internal_server_error);
            else
//                return context.getString(R.string.error);
                return response + " " + responseCode + "";
        }
        return context.getString(R.string.error);
    }

    //FIXME change parameters and get colors
    public static void showSnackbar(View coordinatorLayout, Context context, String message, int colorId) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, colorId));//R.color.colorToast
        TextView tv = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf");
        tv.setTextColor(Color.WHITE);
        tv.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(font);
        snackbar.show();
    }

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            //            System.out.println("encrypted string: "
//                    + encryptedString);

            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = Base64.decode(encrypted, Base64.DEFAULT);
            byte[] s = cipher.doFinal(original);

            return new String(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * format int or double value into separated decimal numbers
     *
     * @param i
     * @return
     */
    public static String formatCostValueFa(Object i) {
        try {
            NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("fa"));

            return nf.format(i) + " ریال ";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String formatNumberValueFa(Object i) {
        String number = "";
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("fa"));
            number = nf.format(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String formatPhoneNumberValue(Object i) {
        String formattedNumber = "";
        try {
            formattedNumber = PhoneNumberUtils.formatNumber(i.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedNumber;
    }

    public static String convertEnNumToFa(String str) {
        try {

            char[] persianChars = {'۰', '۱', '۲', '٣', '۴', '۵', '۶', '۷', '۸', '٩'};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                    builder.append(persianChars[(int) (str.charAt(i)) - 48]);
                } else {
                    builder.append(str.charAt(i));
                }
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void saveOnPrefrences(String prefKey, String prefValue,
                                        Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(prefKey, prefValue);
        editor.apply();
    }

    public static String retrivePrefrencesData(String prefKey, Context context) {
        String restoredText = "";
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        restoredText = prefs.getString(prefKey, "");
        return restoredText;
    }

    public static boolean stringMatcher(String inputPattern, String input) {
        Pattern pattern = Pattern.compile(inputPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public final static boolean isEmailValid(CharSequence target) {
        if (target != null) {
            return !TextUtils.isEmpty(target)
                    && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        } else return false;
    }

    public final static boolean isPasswordValid(String password) {
        return password != null && password.length() > 4;

    }

    /**
     * @param context
     * @param title
     * @param message Caution:Error--> android.view.WindowManager$BadTokenException:
     *                Unable to add window -- token null is not for an application
     *                <p/>
     *                --> Instead of getApplicationContext(), just use
     *                ActivityName.this
     */
//    public static Dialog getDialog(final Activity context, String title,
//                                   String message) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//
//        Rect displayRectangle = new Rect();
//        Window window = context.getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//
//        // inflate and adjust layout
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        assert inflater != null;
//        View layout = inflater.inflate(R.layout.dialog_layout, null);
//        layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
//        //layout.setMinimumHeight((int)(displayRectangle.height() * 0.3f));
//
//        dialog.setContentView(layout);
//        TextView dialogTitleTextView = (TextView) dialog.findViewById(R.id.dialogTitleTextView);
//        dialogTitleTextView.setText(title);
//        TextView dialogMessageTextView = (TextView) dialog.findViewById(R.id.dialogMessageTextView);
//        dialogMessageTextView.setText(message);
//        Utils.overrideFonts(context, layout);
//
//        return dialog;
//    }


    /**
     * Checks connection
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void exit(Activity activity) {
        exit((Context) activity);
    }

    public static void exit(Context context) {
        Activity activity = (Activity) context;
        activity.finish();
    }

    /**
     * @param activity
     * @param activityClass
     * @param key
     * @param value
     */
    public static void startActivityWithAnimation(Activity activity, Class activityClass, String key, Bundle value) {
        Intent intent = new Intent(activity, activityClass);
        if (key != null && value != null) {
            intent.putExtra(key, value);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit);
    }

    /**
     * @param activity
     * @param activityClass
     */
    public static void startActivityWithAnimation(Activity activity, Class activityClass) {
        startActivityWithAnimation(activity, activityClass, null, null);
    }
}
