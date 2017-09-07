package in.apptozee.locman.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.apptozee.locman.R;
import in.apptozee.locman.utiles.Networking;

/**
 * Created by amareshjana on 04/08/16.
 * this is used for the feedback and suggestions
 */
public class FeedbacknSuggestionsActivity extends BaseActivity implements View.OnClickListener {

    private EditText mFeedbackEt;
    private Button mSendBtn;
    private String mFeedbackStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacknsuggestion);
        setToolbar("Feedback n Suggestions", 1);
        findViews();
    }

    private void findViews() {
        mFeedbackEt = (EditText) findViewById(R.id.feedback_et);
        mSendBtn = (Button) findViewById(R.id.feedback_btn);

        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mSendBtn) {
            makeToast("Thanks For Your Feedback!!!", 1);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Networking.isNetworkAvailable(FeedbacknSuggestionsActivity.this)) {
            showNoNetworkDialogue(FeedbacknSuggestionsActivity.this);
        }
    }
}
