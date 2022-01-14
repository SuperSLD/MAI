package com.raspisanie.mai.ui.main.info.news.holders

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.fromFormatToFormat
import com.raspisanie.mai.extesions.getIsDayTheme
import com.raspisanie.mai.models.local.AdvLocal
import com.raspisanie.mai.models.local.NewsLocal
import com.raspisanie.mai.ui.main.info.adv_list.AdvListAdapter.Companion.OPEN_LINK_EVENT
import kotlinx.android.synthetic.main.item_adv_holder.view.*
import kotlinx.android.synthetic.main.item_adv_holder.view.tvDate
import kotlinx.android.synthetic.main.item_adv_holder.view.tvText
import kotlinx.android.synthetic.main.item_news.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_news)
class NewsHolder(itemView: View) : DFBaseHolder<NewsLocal>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bind(item: NewsLocal) {
        with(itemView) {
            tvTitle.text = "${item.title} "
            tvAuthor.text = item.author
            tvText.text = item.text
            tvDate.text = item.createdAt.split(".")[0].fromFormatToFormat("yyyy-MM-dd HH:mm:ss", "HH:mm dd.MM.yyyy")
            ivWarning.visibility = if (item.warning) View.VISIBLE else View.GONE

            ivImage.visibility = if (item.image.isNullOrEmpty()) View.GONE else View.VISIBLE
            Glide.with(context)
                .load(if (context.getIsDayTheme()) item.image else item.imageDark)
                .into(ivImage)
        }
    }
}