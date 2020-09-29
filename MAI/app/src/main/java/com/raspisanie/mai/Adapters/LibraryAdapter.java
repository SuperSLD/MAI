package com.raspisanie.mai.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raspisanie.mai.Classes.DataModels.LibraryObject;
import com.raspisanie.mai.Classes.SimpleTree;
import com.raspisanie.mai.R;

import java.util.ArrayList;

/**
 * Адаптер для отображения списка библиотек.
 */
public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibItem> {
    private ArrayList<LibraryObject> objects;

    /**
     * Класс ViewHolder для хранения ссылок на View компоненты.
     */
    protected class LibItem extends RecyclerView.ViewHolder {

        private TextView titleText;
        private LinearLayout linearLayout;
        private View view;

        public LibItem(@NonNull View itemView) {
            super(itemView);
            this.titleText = itemView.findViewById(R.id.tableText);
            this.linearLayout = itemView.findViewById(R.id.tableList);
            this.view = itemView;
        }

        /**
         * Передача параметров в view элементы.
         * @param libraryObject дерево элементов.
         */
        public void bind(LibraryObject libraryObject) {
            titleText.setText(libraryObject.getName());

            LibSubAdapter adapter = new LibSubAdapter(view.getContext(), libraryObject.getSections());

            LinearLayout newLinearLayout = new LinearLayout(view.getContext());
            newLinearLayout.setOrientation(LinearLayout.VERTICAL);

            newLinearLayout.removeAllViews();
            for (int j = 0 ; j < libraryObject.getSections().size(); j++)
                newLinearLayout.addView(adapter.getView(j, null, newLinearLayout));

            linearLayout.removeAllViews();
            linearLayout.addView(newLinearLayout);
        }
    }

    /**
     * Конструктор адаптера.
     * @param korpus список элементов для отображения.
     */
    public LibraryAdapter(ArrayList korpus) {
        this.objects = korpus;
    }

    /**
     * Создание ViewHolder списка c пустыми элементами.
     * @param viewGroup
     * @param i позиция элемениа.
     * @return элемент списка с View элементами.
     */
    @NonNull
    @Override
    public LibItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sport_group, viewGroup, false);
        return new LibraryAdapter.LibItem(view);
    }

    /**
     * Передача параметров в элемент списка итемов.
     * @param libItem эоемент списка.
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull LibItem libItem, int i) {
        libItem.bind(objects.get(i));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private class LibSubAdapter extends BaseAdapter {
        private Context ctx;
        private LayoutInflater lInflater;
        private ArrayList<String> objects;

        public LibSubAdapter(Context context, ArrayList list) {
            this.ctx = context;
            this.objects = list;
            this.lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Object getItem(int i) {
            return objects.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.item_lib_sub_list, parent, false);
            }

            try {
                System.out.println(i);
                String[] text = objects.get(i).split("<!>");
                ((TextView) view.findViewById(R.id.t1)).setText(text[0]);
                ((TextView) view.findViewById(R.id.t2)).setText(text[1]);
            } catch (IndexOutOfBoundsException ex) {}
            return view;
        }
    }
}
