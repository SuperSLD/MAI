package com.raspisanie.mai.ui.main.info.news.holders

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getIsDayTheme
import com.raspisanie.mai.domain.models.NewsLocal
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
            ivWarning.visibility = if (item.warning) View.VISIBLE else View.GONE

            ivImage.visibility = if (item.image.isNullOrEmpty()) View.GONE else View.VISIBLE
            Glide.with(context)
                .load(if (context.getIsDayTheme()) item.image else item.imageDark)
                .into(ivImage)
        }
    }
}