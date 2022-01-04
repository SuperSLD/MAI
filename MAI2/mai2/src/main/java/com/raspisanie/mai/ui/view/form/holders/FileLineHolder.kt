package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import com.raspisanie.mai.ui.view.form.FormLinesAdapter
import com.raspisanie.mai.ui.view.form.lines.FileLine
import kotlinx.android.synthetic.main.item_line_file.view.*
import online.jutter.diffadapter2.base.DFBaseHolder
import online.jutter.diffadapter2.base.HolderLayout
import org.koin.core.KoinComponent

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_file)
class FileLineHolder(itemView: View) : DFBaseHolder<FileLine>(itemView), KoinComponent {
    override fun bind(item: FileLine) {
        val fileLoader = (getAdapter() as FormLinesAdapter).fileLoader ?: throw IllegalArgumentException("fileLoader is null for FileLine")
        with(itemView) {
            toggleLoading(item.loading, item)
            tvFile.text = item.fileName
            tvFile.text = item.name

            tvRed.visibility = if (item.mandatory) View.VISIBLE else View.GONE

            setOnClickListener {
                fileLoader.pickFile(item.id, item.name)
            }

            if (item.data == null) {
                icFileDone.visibility = View.GONE
            } else {
                tvCount.visibility = View.GONE
            }

            fileLoader.onLoadChangeState(item.id) {
                toggleLoading(it, item)
                item.loading = it
            }

            fileLoader.onFinishLoading(item.id) { name, guid ->
                item.data = guid
                item.fileName = name
                //tvFile.text = file.fileName
                tvCount.visibility = View.GONE
                icFileDone.visibility = View.VISIBLE
                toggleLoading(false, item)
            }

            tvCount.text = item.position.toString()
        }
    }

    fun toggleLoading(show: Boolean, file: FileLine) {
        with(itemView) {
            pbLoading.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}