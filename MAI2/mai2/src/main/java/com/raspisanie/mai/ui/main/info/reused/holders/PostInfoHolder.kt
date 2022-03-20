package com.raspisanie.mai.ui.main.info.reused.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getDrawable
import com.raspisanie.mai.domain.models.PostLocal
import kotlinx.android.synthetic.main.item_post_information.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_post_information)
class PostInfoHolder(itemView: View) : DFBaseHolder<PostLocal>(itemView) {

    companion object {
        const val LIKE_CLICK_EVENT = -1002
    }

    override fun bind(item: PostLocal) {
        with(itemView) {
            if (item.isLiked()) {
                tvLikes.setTextColor(Color.parseColor("#FF5353"))
                tvLikes.setCompoundDrawablesWithIntrinsicBounds(
                    getDrawable(R.drawable.ic_liked), null, null, null
                )
                tvLikes.setOnClickListener(null)
            }
            tvLikes.text = item.getLikes().toString()
            tvLikes.setOnClickListener {
                makeEvent(LIKE_CLICK_EVENT, item.getRecordId())
            }
            tvViews.text = item.getViews().toString()
            tvDate.text = item.getDate()
        }
    }
}