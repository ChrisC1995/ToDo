package com.campbellapps.christiancampbell.todolist2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class taskList extends AppCompatActivity {

    private Button button;
    ArrayList<groceryListItem> arrayOfItems = new ArrayList<groceryListItem>();

    String filename = "helloFile";
    List<groceryListItem> todos = new ArrayList<>();
    Gson gson = new Gson();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery__list);
        setupNotes();



        final taskArrayAdapter adapter = new taskArrayAdapter(this, arrayOfItems);
        ListView listView = (ListView) findViewById(R.id.grocery_listview);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groceryListItem item = arrayOfItems.get(position);
                Intent detailScreen = new Intent(taskList.this, userInput.class);
                detailScreen.putExtra("titleResult", item.getTitle());
                detailScreen.putExtra("infoResult", item.getText());
                detailScreen.putExtra("categoryResult", item.getCategory());
                detailScreen.putExtra("dayResult", item.getDay());
                detailScreen.putExtra("monthResult", item.getMonth());
                detailScreen.putExtra("timeResult", item.getTime());

                adapter.remove(arrayOfItems.get(position));
                adapter.notifyDataSetChanged();


                startActivityForResult(detailScreen, 1);



            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(taskList.this);
                alertBuilder.setTitle("Delete/Complete This Task");
                alertBuilder.setMessage("Are you sure you are done with it?");
                alertBuilder.setNegativeButton("Get Me Outta Here", null);
                alertBuilder.setPositiveButton("Delete/Mark As Completed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        groceryListItem item = arrayOfItems.get(position);

                        arrayOfItems.remove(item);
                        adapter.notifyDataSetChanged();
                        writeTodos();


                    }
                });
                alertBuilder.create().show();
                return true;

            }
        });



        button = (Button) findViewById(R.id.grocery_addButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent();
            }
        });
    }


    public void addIntent() {
        Intent i;
        i = new Intent(taskList.this, userInput.class);
        startActivityForResult(i, 1);
    }


    protected void onActivityResult(int requestCode, final int resultCode, Intent view) {
        super.onActivityResult(requestCode, resultCode, view);
        getIntent();

        if (resultCode == RESULT_OK) {
            final String title = view.getStringExtra("titleResult");
//            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

            String text = view.getStringExtra("textResult");
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

            String date = view.getStringExtra("dateResult");
//            Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

            String category = view.getStringExtra("categoryResult");

            String day = view.getStringExtra("dayResult");
            String month = view.getStringExtra("monthResult");
            String time = view.getStringExtra("timeResult");






            final taskArrayAdapter adapter = new taskArrayAdapter(this, arrayOfItems);
            ListView listView = (ListView) findViewById(R.id.grocery_listview);
            listView.setAdapter(adapter);


            Date date2 = new Date();
            groceryListItem newItem = new groceryListItem(title, text, date, category, date2, day, month, time);
            adapter.add(newItem);




            Collections.sort(arrayOfItems);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    groceryListItem item = arrayOfItems.get(position);
                    Intent detailScreen = new Intent(taskList.this, userInput.class);
                    detailScreen.putExtra("titleResult", item.getTitle());
                    detailScreen.putExtra("infoResult", item.getText());
                    detailScreen.putExtra("categoryResult", item.getCategory());
                    detailScreen.putExtra("dayResult", item.getDay());
                    detailScreen.putExtra("monthResult", item.getMonth());
                    detailScreen.putExtra("timeResult", item.getTime());







                    adapter.remove(arrayOfItems.get(position));
                    adapter.notifyDataSetChanged();


                    startActivityForResult(detailScreen, 1);
//                    adapter.remove(arrayOfItems.get(position));
//                    adapter.notifyDataSetChanged();



                }
            });


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(taskList.this);
                    alertBuilder.setTitle("Delete/Complete This Task");
                    alertBuilder.setMessage("Are you sure you are done with it?");
                    alertBuilder.setNegativeButton("Get Me Outta Here", null);
                    alertBuilder.setPositiveButton("Delete/Mark As Completed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            groceryListItem item = arrayOfItems.get(position);

                            arrayOfItems.remove(item);
                            adapter.notifyDataSetChanged();
                            writeTodos();


                        }
                    });
                    alertBuilder.create().show();
                    return true;




//                    adapter.remove(arrayOfItems.get(position));
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(taskList.this, "Task Completed or Deleted, one of the two.", Toast.LENGTH_SHORT).show();
//
//                    return false;
                }
            });


        } else {
            Toast.makeText(this, "Add Canceled", Toast.LENGTH_SHORT).show();
        }
        writeTodos();
    }


    public void setupNotes() {
        arrayOfItems = new ArrayList<>();

        File filesDir = this.getFilesDir();
        File todoFile = new File(filesDir + File.separator + filename);
        if (todoFile.exists()) {
            readTodos(todoFile);

            for (groceryListItem todo : todos) {
                arrayOfItems.add(todo);


            }
        } else {
            // If the file doesn't exist, create it
//            Date date = new Date();
//            todos.add(new groceryListItem("todo 1","A todo", "thing", "thing", date));
//            todos.add(new groceryListItem("todo 2","Another todo", "thing", "thing", date));
//            todos.add(new groceryListItem("todo 3","One more todo", "thing", "thing", date));

            writeTodos();
        }
    }


    private void readTodos(File todoFile) {
        FileInputStream inputStream = null;
        String todosText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {}
            todosText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            groceryListItem[] todoList = gson.fromJson(todosText, groceryListItem[].class);
            todos = Arrays.asList(todoList);
        }
    }

    private void writeTodos() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(arrayOfItems);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {}
        }
    }





}
