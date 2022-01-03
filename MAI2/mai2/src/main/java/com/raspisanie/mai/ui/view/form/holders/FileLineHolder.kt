package com.raspisanie.mai.ui.view.form.holders

import android.annotation.SuppressLint
import android.view.View
import com.raspisanie.mai.R
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import org.koin.core.KoinComponent
import com.raspisanie.mai.ui.view.form.FormLinesAdapter
import com.raspisanie.mai.ui.view.form.lines.FileLine
import com.yandex.metrica.impl.ob.V
import kotlinx.android.synthetic.main.item_line_file.view.*
import java.lang.IllegalArgumentException

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_line_file)
class FileLineHolder(itemView: View) : DFBaseHolder(itemView), KoinComponent {
    override fun bind(data: Any?) {
        val file = data as FileLine
        val fileLoader = (getAdapter() as FormLinesAdapter).fileLoader ?: throw IllegalArgumentException("fileLoader is null for FileLine")
        with(itemView) {
            toggleLoading(file.loading, file)
            tvFile.text = file.fileName
            tvFile.text = file.name

            tvRed.visibility = if (file.mandatory) View.VISIBLE else View.GONE

            setOnClickListener {
                fileLoader.pickFile(file.id, file.name)
            }

            if (file.data == null) {
                icFileDone.visibility = View.GONE
            } else {
                tvCount.visibility = View.GONE
            }

            fileLoader.onLoadChangeState(file.id) {
                toggleLoading(it, file)
                file.loading = it
            }

            fileLoader.onFinishLoading(file.id) { name, guid ->
                file.data = guid
                file.fileName = name
                //tvFile.text = file.fileName
                tvCount.visibility = View.GONE
                icFileDone.visibility = View.VISIBLE
                toggleLoading(false, file)
            }

            tvCount.text = file.position.toString()
        }
    }

    fun toggleLoading(show: Boolean, file: FileLine) {
        with(itemView) {
            pbLoading.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}