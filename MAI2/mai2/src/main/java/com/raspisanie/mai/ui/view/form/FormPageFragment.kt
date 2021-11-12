package com.raspisanie.mai.ui.view.form
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raspisanie.mai.R
import kotlinx.android.synthetic.main.view_form_fragment.*
import pro.midev.supersld.common.base.BaseFragment

class FormPageFragment : BaseFragment(R.layout.view_form_fragment) {

    private var next: (()->Unit)? = null
    private var previous: (()->Unit)? = null
    private var finish: (()->Unit)? = null

    private val adapter by lazy { FormLinesAdapter(rootFragmentManager!!) }
    private var fileLoader: FileLoader? = null
    private var rootFragmentManager: FragmentManager? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showPage()
    }

    override fun onBackPressed() {}

    private fun showPage() {
        val position = arguments?.getInt(ARG_POSITION)!!
        val page = arguments?.getParcelable<FormPage>(ARG_PAGE)!!
        adapter.fileLoader = fileLoader
        adapter.addData(page.lines)
        with(rvRecycler) {
            adapter = this@FormPageFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        val bottomText = page.buttonText
        val finishText = arguments?.getString(ARG_FINISH_TEXT) ?: "Завершить"
        btnNext.text = bottomText
        btnPrevious.text = "Назад"
        btnPrevious.visibility = if (position == 0) View.GONE else View.VISIBLE
        btnPrevious.setOnClickListener {
            previous?.let { it1 -> it1() }
        }

        if (position == FormPage.LAST_PAGE) {
            btnNext.text = finishText
            btnNext.setOnClickListener {
                finish?.let { it1 -> it1() }
            }
        } else {
            btnNext.setOnClickListener {
                next?.let { it1 -> it1() }
            }
        }
    }

    fun onNext(next: ()->Unit) {
        this.next = next
    }

    fun onPrevious(previous: ()->Unit) {
        this.previous = previous
    }

    fun onFinish(finish: ()->Unit) {
        this.finish = finish
    }

    fun setFileLoader(fileLoader: FileLoader?) {
        this.fileLoader = fileLoader
    }

    fun setRootFragmentManager(fragmentManager: FragmentManager) {
        this.rootFragmentManager = fragmentManager
    }

    companion object {
        const val ARG_PAGE = "arg_page"
        const val ARG_POSITION = "arg_position"
        const val ARG_BOTTOM_TEXT = "arg_bottom_text"
        const val ARG_FINISH_TEXT = "arg_finish_text"

        fun create(
            page: FormPage,
            position: Int,
            bottomText: String?,
            finishString: String?
        ): FormPageFragment {
            val fragment = FormPageFragment()
            val args = Bundle()
            args.putParcelable(ARG_PAGE, page)
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_BOTTOM_TEXT, bottomText)
            args.putString(ARG_FINISH_TEXT, finishString)
            fragment.arguments = args
            return fragment
        }
    }
}