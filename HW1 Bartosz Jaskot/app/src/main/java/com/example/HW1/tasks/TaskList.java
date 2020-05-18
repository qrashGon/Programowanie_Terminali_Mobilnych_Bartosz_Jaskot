package com.example.HW1.tasks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskList {

    public static final List<Task> ITEMS = new ArrayList<Task>();
    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();


    static {
        addItem ( new Task ( String.valueOf ( 1 ), "Bartosz", "621380573", "Jaskot", "21/08/1997"));
        addItem ( new Task ( String.valueOf ( 2 ), "Jan", "573157897", "Kowalski", "26/12/1995"));
        addItem ( new Task ( String.valueOf ( 3 ), "Robert", "987654321", "Trebor", "12/01/1998"));
        addItem ( new Task ( String.valueOf ( 4 ), "Tomasz", "379652376", "Kot", "13/04/1987"));
        addItem ( new Task ( String.valueOf ( 5 ), "Piotr", "956432654", "Lis", "18/11/1999"));
    }

    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class Task implements Parcelable {
        public final String id;
        public String picPath;
        public final String name;
        public final String phone;
        public final String surname;
        public final String birthday;

        public Task(String id, String name, String phone, String surname, String birthday) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.phone = phone;
            this.birthday = birthday;
            this.picPath = "";

        }

        protected Task(Parcel in) {
            id = in.readString();
            picPath = in.readString();
            name = in.readString();
            phone = in.readString();
            surname = in.readString();
            birthday = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(picPath);
            dest.writeString(name);
            dest.writeString(phone);
            dest.writeString(surname);
            dest.writeString(birthday);
        }
    }

        public static void removeItem(int position) {
            String itemId = ITEMS.get(position).id;

            ITEMS.remove(position);

            ITEM_MAP.remove(itemId);
        }

        public static void clearList() {
            ITEMS.clear();
            ITEM_MAP.clear();
        }
    }

