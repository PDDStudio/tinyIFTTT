package com.pddstudio.tinyifttt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.pddstudio.tinyifttt.R;
import com.pddstudio.tinyifttt.models.TinyAction;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class TinyActionItem extends AbstractItem<TinyActionItem, TinyActionItem.ViewHolder> {

    private final TinyAction mTinyAction;

    public TinyActionItem(TinyAction tinyAction) {
        this.mTinyAction = tinyAction;
    }

    public TinyAction getTinyAction() {
        return mTinyAction;
    }

    @Override
    public int getType() {
        return R.id.tiny_action_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_tiny_action;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.actionTitle.setText(mTinyAction.getActionTitle());
        viewHolder.actionDescription.setText(mTinyAction.getActionDescription());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView actionTitle;
        TextView actionDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            this.actionTitle = (TextView) itemView.findViewById(R.id.actionName);
            this.actionDescription = (TextView) itemView.findViewById(R.id.actionDescription);
        }

    }
}
