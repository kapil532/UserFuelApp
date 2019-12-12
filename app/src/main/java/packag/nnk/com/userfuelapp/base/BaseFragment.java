package packag.nnk.com.userfuelapp.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import packag.nnk.com.userfuelapp.R;

public class BaseFragment extends Fragment
{
    private AlertDialog alert;

    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showProgressDialog()
    {
        try
        {
            if (mProgressDialog == null)
            {
                if (getActivity() != null)
                {
                    mProgressDialog = new ProgressDialog(getActivity(), R.style.MyProgressDialogTheme);
                    mProgressDialog.setCancelable(false);
                /*mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.setMessage("Please Wait....");*/

                    Log.e("fsd","check");
                }
            }

            mProgressDialog.show();
        }
        catch (Exception e)
        {

        }
    }

    public void hideProgressDialog()
    {
        if (mProgressDialog != null)
        {
            if (mProgressDialog.isShowing())
            {
                mProgressDialog.hide();
            }
        }
    }

    public void showAlert(String message)
    {
        try
        {
            if (getActivity() != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alert!");
                builder.setMessage(message);
                builder.setCancelable(false);
            /*TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            Typeface face=Typeface.createFromAsset(getAssets(),"fonts/GOTHAM-ROUNDED-BOO.OTF");
            textView.setTypeface(face);*/
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        alert.dismiss();
                    }
                });
                alert = builder.create();
                alert.show();
            }
        }
        catch (Exception e)
        {

        }

    }
}
