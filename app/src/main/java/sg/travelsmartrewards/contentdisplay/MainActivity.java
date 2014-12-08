package sg.travelsmartrewards.contentdisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static WebView webView;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
    }

    private void showProgress(final boolean show) {
        if (show)
            Log.e("Show Progress:", "True");
        else
            Log.e("Show Progress:", "False");
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            dataLoadProgressView.setVisibility(View.VISIBLE);
            dataLoadProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dataLoadProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            dataLoadProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }*/
    }

    public void onSectionAttached(int number) {
        /*switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }*/
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);*/
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int selectedDrawerMenuIndex;
        private String[] mDrawerMenuUrlArray;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            selectedDrawerMenuIndex = sectionNumber;
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mDrawerMenuUrlArray = getResources().getStringArray(R.array.drawer_menu_url);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final ProgressBar Pbar;
            final TextView txtview = (TextView)rootView.findViewById(R.id.tV1);
            Pbar = (ProgressBar) rootView.findViewById(R.id.pB1);

            webView = (WebView) rootView.findViewById(R.id.webview);

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress)
                {
                    if(progress < 100 && Pbar.getVisibility() == ProgressBar.GONE){
                        Pbar.setVisibility(ProgressBar.VISIBLE);
                        txtview.setVisibility(View.VISIBLE);
                    }
                    Pbar.setProgress(progress);
                    if(progress == 100) {
                        Pbar.setVisibility(ProgressBar.GONE);
                        txtview.setVisibility(View.GONE);
                    }
                }
            });

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String url = getResources().getString(R.string.default_url);
            // webView.loadUrl("https://www.travelsmartrewards.sg/");
            url = mDrawerMenuUrlArray[selectedDrawerMenuIndex];


            webView.loadUrl(url);

            return rootView;

            /*webView = (WebView) rootView.findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient() {

                ProgressDialog progressDialog;

                //If you will not use this method url links are opeen in new brower not in webview
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                //Show loader on url load
                public void onLoadResource(WebView view, String url) {
                    if (progressDialog == null) {
                        // in standard case YourActivity.this
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    try {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String url = getResources().getString(R.string.default_url);
            // webView.loadUrl("https://www.travelsmartrewards.sg/");
            url = mDrawerMenuUrlArray[selectedDrawerMenuIndex];


            webView.loadUrl(url);

            return rootView;*/
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}
