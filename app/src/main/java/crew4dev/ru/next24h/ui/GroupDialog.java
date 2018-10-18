package crew4dev.ru.next24h.ui;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.data.TaskGroup;

import static crew4dev.ru.next24h.Tools.toCharSequenceArray;

public class GroupDialog extends DialogFragment {

    private static final String TAG = "GroupDialog";

    private static final String ARG_TITLE = "Отображать катерогии";
    private static CharSequence[] charSequences;
    private static AlertDialog dialog;

    //List<Integer> mSelectedItems = new ArrayList<>();
    private static boolean[] checkedItems;

    public GroupDialog() {}

    public static void close() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public static void showSelectGroups(final Context context, List<TaskGroup> groups, final OnSelectClickListener listener) {
        if (context != null) {
            checkedItems = new boolean[groups.size()];
            for (int i = 0; i < groups.size(); i++) {
                checkedItems[i] = groups.get(i).isVisible();
                Log.d(TAG, "isVisible " + groups.get(i).isVisible() + " item: " + i);
            }

            charSequences = toCharSequenceArray(groups);

            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            final List<Integer> mSelectedItems = new ArrayList<>();
            ad.setTitle(ARG_TITLE)
                    .setMultiChoiceItems(charSequences, checkedItems, (dialog, which, isChecked) -> {
//                                    if (isChecked) {
//                                        mSelectedItems.add(which);
//                                    } else if (mSelectedItems.contains(which)) {
//                                        mSelectedItems.remove(Integer.valueOf(which));
//                                    }
                    });
            ad.setPositiveButton(R.string.ok, (dialog, whichButton) -> ((MainActivity) context).doPositiveClick(checkedItems));
            ad.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
            });
            ad.setNeutralButton(R.string.enable_all, (dialog, id) -> {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = true;
                }
                ((MainActivity) context).doPositiveClick(checkedItems);
            });
            ad.setCancelable(true);
            try {
                dialog = ad.show();
            } catch (Exception e) {
                Log.d(TAG, "Exception", e);
            }
        }
    }

    public static void showNewGroupName(final Context context, String oldName, final OnSelectClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((TaskDetailsActivity) context).getLayoutInflater();
        View main = inflater.inflate(R.layout.group_name, null);
        AutoCompleteTextView groupname = main.findViewById(R.id.groupname);

        builder.setView(main)
                .setPositiveButton(R.string.ok, (dialog, id) -> ((TaskDetailsActivity) context)
                        .doPositiveClick(groupname.getText().toString()))
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });

        if (!oldName.isEmpty())
            groupname.setText(oldName);

        builder.setCancelable(true);
        try {
            dialog = builder.show();
        } catch (Exception e) {
            Log.d(TAG, "Exception", e);
        }
    }

    public void setData(List<String> groups) {
        charSequences = groups.toArray(new CharSequence[groups.size()]);
    }

    public void doPositiveClick(boolean[] mSelectedItems) {

    }
}
