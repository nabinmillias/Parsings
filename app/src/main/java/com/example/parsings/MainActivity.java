package com.example.parsings;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList sender_id,reciever_id,username,friend_id,pic,messages,send_time;
    ListView listView;
    AsyncHttpClient client;
    RequestParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();
        params = new RequestParams();
        listView=findViewById(R.id.lv);
        sender_id = new ArrayList<String>();
        reciever_id = new ArrayList<String>();
        username = new ArrayList<String>();
        friend_id = new ArrayList<String>();

        pic = new ArrayList<String>();
        messages = new ArrayList<String>();
        send_time = new ArrayList<String>();


        params.put("user_id","9");
        client.get("https://www.sixsquaretechnologies.com/messenger/friendListFinal.php?",params,new AsyncHttpResponseHandler()
          {
              @Override
              public void onSuccess(String content) {
                  super.onSuccess(content);
                  try{

                      JSONObject jsonObject=new JSONObject(content);

                      String result = jsonObject.getString("Result");

                      if(result.equalsIgnoreCase("Success"))
                      {
                          JSONArray jsonArray=jsonObject.getJSONArray("Details");
                          for (int i = 0; i < jsonArray.length(); i++) {
                              JSONObject obj = jsonArray.getJSONObject(i);

                              String send=obj.getString("sender_id");
                              sender_id.add(send);

                              String rec=obj.getString("receiver_id");
                              reciever_id.add(rec);
                              String user=obj.getString("username");
                              username.add(user);
                              String fri=obj.getString("friend_id");
                              friend_id.add(fri);
                              String pics=obj.getString("pic");
                              pic.add(pics);
                              String mes=obj.getString("message");
                              messages.add(send);
                              String time=obj.getString("send_time");
                              send_time.add(time);



                          }


                      }
                      else
                      {
                          Toast.makeText(MainActivity.this, "Result failurr", Toast.LENGTH_SHORT).show();
                      }


                      adapter adpt = new adapter();
                      listView.setAdapter(adpt);


                  }catch (Exception e)
                  {

                      Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                  }
              }
          });
    }
    class adapter extends BaseAdapter{
        LayoutInflater Inflater;
        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= Inflater.inflate(R.layout.listitem,null);
            Viewholder holder=new Viewholder();

            holder.sendid=(TextView)convertView.findViewById(R.id.senerid);
            holder.sendid.setText((CharSequence) sender_id.get(position));

            holder.recid=(TextView)convertView.findViewById(R.id.recid);
            holder.recid.setText((CharSequence) reciever_id.get(position));

            holder.frendid=(TextView)convertView.findViewById(R.id.friendid);
            holder.frendid.setText((CharSequence) friend_id.get(position));

            holder.messages=(TextView)convertView.findViewById(R.id.message);
            holder.messages.setText((CharSequence) messages.get(position));

            holder.sendtime=(TextView)convertView.findViewById(R.id.sendtime);
            holder.sendtime.setText((CharSequence) send_time.get(position));

            holder.pic=(ImageView) convertView.findViewById(R.id.pic);
            // holder.rg.setText(reg.get(position));
            Glide
                    .with(MainActivity.this)
                    .load(pic.get(position))
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground);
            //     .into(holder.pic);
            holder.username=(TextView)convertView.findViewById(R.id.username);
            holder.username.setText((CharSequence) username.get(position));
            return convertView;
        }

        private class Viewholder {
            TextView sendid;
            TextView recid;
            TextView username;
            TextView messages;
            TextView sendtime;
            TextView frendid;
            ImageView pic;
        }
    }
}
