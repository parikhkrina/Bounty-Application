package app.com.loyaltyapp.bounty.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.MenuModel;
import app.com.loyaltyapp.bounty.interfaceListeners.onMenuItemClickInterface;

public class CategoryMenuAdapter extends RecyclerView.Adapter<CategoryMenuAdapter.MenuViewHolder> {


    MenuModel menuModel;

    ArrayList<MenuModel> modelsList;


    Context mContext;

    onMenuItemClickInterface clickInterface;

    public CategoryMenuAdapter(ArrayList<MenuModel> menuModelList, Context mContext, onMenuItemClickInterface onMenuItemClickInterface) {
        this.modelsList = menuModelList;
        this.mContext = mContext;
        this.clickInterface = onMenuItemClickInterface;

    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.drawer_menu_item, null);
        MenuViewHolder menuViewHolder = new MenuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryMenuAdapter.MenuViewHolder menuViewHolder, final int i) {


        menuModel = modelsList.get(i);
        System.out.print("Position --> " + i);

        System.out.println("Item - " + menuModel);

        menuViewHolder.menu_name.setText(menuModel.getMenu_name());

//        menuViewHolder.menu_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.print("Position --> " + getItemViewType(i));
//               // clickInterface.oninterfaceClick(menuModel,v,getItemViewType(i));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return modelsList.size();
    }

    public void setClickListener(onMenuItemClickInterface clickListener) {

        this.clickInterface = clickListener;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView menu_name;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_name = (TextView) itemView.findViewById(R.id.tv_menu_name);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            clickInterface.oninterfaceClick(view, getAdapterPosition()); // call the onClick in the OnItemClickListener
        }
    }


}
