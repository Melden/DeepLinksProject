package com.example.cm.deeplinksproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumInbox;
import com.leanplum.LeanplumInboxMessage;
import com.leanplum.NewsfeedMessage;
import com.leanplum.callbacks.InboxChangedCallback;
import com.leanplum.callbacks.NewsfeedChangedCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm on 13/7/2017.
 */

public class InboxFragment extends Fragment {
    private InboxAdapter adapter;

    public interface Listener<T> {
        void onListFragmentInteraction(T item);
    }


    public void onListFragmentInteraction(AppInboxMessageData item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(item.details);
        builder.setTitle(item.title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private ArrayList<LeanplumInboxMessage> messages = new ArrayList<>();


    public static InboxFragment newInstance() {
        InboxFragment f = new InboxFragment();
        //Bundle args = new Bundle();
        //  args.putBoolean("Read", read);
        //f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        test();
//
//        adapter = new InboxAdapter(new InboxFragment.Listener<LeanplumInboxMessage>() {
//            @Override
//            public void onListFragmentInteraction(LeanplumInboxMessage item) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage(item.getSubtitle());
//                builder.setTitle(item.getTitle());
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
//
//            }
//        });
//
//
//        prepareMessages();

    }
    public void test(){
        adapter = new InboxAdapter(new InboxFragment.Listener<LeanplumInboxMessage>() {
            @Override
            public void onListFragmentInteraction(LeanplumInboxMessage item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(item.getSubtitle());
                builder.setTitle(item.getTitle());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });


        prepareMessages();

    }

    private void prepareMessages() {
        messages.clear();
        final LeanplumInbox inbox = Leanplum.getInbox();
        inbox.addChangedHandler(new InboxChangedCallback() {
            @Override
            public void inboxChanged() {
                List all = inbox.allMessages();
                messages = (ArrayList<LeanplumInboxMessage>) all;
                adapter.addMessages(messages);

                int count = adapter.getItemCount();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("messages", count);
                editor.commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_list_fragment, container, false);


        // Set the adapter

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // showToolbar(view);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
//        adapter = new InboxAdapter(new InboxFragment.Listener<LeanplumInboxMessage>() {
//            @Override
//            public void onListFragmentInteraction(LeanplumInboxMessage item) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage(item.getSubtitle());
//                builder.setTitle(item.getTitle());
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
//
//            }
//        });
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setAdapter(adapter);
//        }
////        Bundle args = getArguments();
////        final boolean read = args.getBoolean("Read", false);
//       // prepareMessages(read);
//        prepareMessages();
//
//    }
//
//    private void prepareMessages() {
//        messages.clear();
//        final LeanplumInbox inbox = Leanplum.getInbox();
//        inbox.addChangedHandler(new InboxChangedCallback() {
//            @Override
//            public void inboxChanged() {
//                List all = inbox.allMessages();
//                messages = (ArrayList<LeanplumInboxMessage>) all;
//                adapter.addMessages(messages);
//
//                int count = adapter.getItemCount();
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putInt("messages", count);
//                editor.commit();
//
//            }
//        });
//
    }


    public static class AppInboxMessageData {
        public final String title;
        public final String details;
        public final String messageId;


        public AppInboxMessageData(String title, String details, String messageId) {
            this.title = title;
            this.details = details;
            this.messageId = messageId;
        }
    }
}

