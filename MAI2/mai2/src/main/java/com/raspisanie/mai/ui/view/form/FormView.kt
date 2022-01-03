package com.raspisanie.mai.ui.view.form

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.raspisanie.mai.R
import com.raspisanie.mai.extesions.getColor
import kotlinx.android.synthetic.main.view_form.view.*
import pro.midev.supersld.extensions.addSystemBottomPadding

/**
 *  Конструктор для форм.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
class FormView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private val fragments : MutableList<Fragment> = mutableListOf()
    private var position = 0
    private var lastPosition = position

    private var form: Form? = null

    private var exitL: (()->Unit)? = null
    private var finishL: ((Form)->Unit)? = null
    private var fileLoader: FileLoader? = null

    init {
        View.inflate(context, R.layout.view_form, this)
        this.addSystemBottomPadding()
    }


    /**
     * Инициализация формы.
     * Создаются все страницы и линии по
     * указанному шаблону. Затем пользователь все заполняет.
     *
     * @param form
     * @param
     */
    fun init(
        form: Form,
        childFragmentManager: FragmentManager
    ) {
        this.form = form
        if (form.name != null) {
            tvName.text = form.name
            tvName.visibility = VISIBLE
        } else {
            tvName.visibility = GONE
        }

        if (form.title != null) {
            vgTitle.visibility = VISIBLE
            tvTitle.text = form.title.title
            tvDesk.text = form.title.description
        } else {
            vgTitle.visibility = GONE
        }

        with(vLine) {
            setColor(getColor(R.color.colorPrimary))
            setBackgroundColor(getColor(R.color.colorBorder))
            setMaxProgress(form.pages.size)
            setProgress(1)
        }

        fragments.clear()
        for (i in form.pages.indices) {
            val page = FormPageFragment.create(
                page = form.pages[i],
                position = if (i != form.pages.size -1) i else FormPage.LAST_PAGE,
                bottomText = form.pages[i].buttonText,
                finishString = form.finishText
            )
            page.onNext(this::next)
            page.onPrevious(this::previous)
            page.onFinish(this::finish)
            page.setFileLoader(fileLoader)
            page.setRootFragmentManager(childFragmentManager)
            fragments.add(page)
        }
        vpFormPager.setPagingEnabled(false)
        vpFormPager.adapter = ExpanseProfitPageAdapter(childFragmentManager, fragments)
        updateLine()
    }

    fun setFileLoader(fileLoader: FileLoader) {
        this.fileLoader = fileLoader
    }

    private fun next() {
        if (position < fragments.size - 1)
            if (form!!.pages[position].isValid()) {
                vpFormPager.setCurrentItem(vpFormPager.currentItem + 1, true)
            } else {
                Toast.makeText(context, "Пожалуйста заполните все обязательные поля чтобы продолжить", Toast.LENGTH_SHORT).show()
            }
        position = vpFormPager.currentItem
        updateLine()
    }

    fun previous() {
        if (position > 0) {
            vpFormPager.setCurrentItem(vpFormPager.currentItem - 1, true)
        } else {
            exit()
        }
        position = vpFormPager.currentItem
        updateLine()
    }

    fun exit() {
        exitL?.let { it() }
    }

    /**
     * Нажатие на кнопку назад на первой странице,
     * или нажатие на кнопку на главную внизу страницы.
     */
    fun onExit(exit: ()->Unit) {
        exitL = exit
    }

    /**
     * Получение загрузчика файдлв.
     */
    fun getFileLoader() = this.fileLoader

    private fun finish() {
        if (this.form?.isValid() == true) {
            finishL?.let { it(this.form!!) }
        } else {
            Toast.makeText(context, "Пожалуйста заполните все поля перед отправкой", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Завершение заполнения формы.
     */
    fun onFinish(finish: (Form)->Unit) {
        finishL = finish
    }

    @SuppressLint("SetTextI18n")
    private fun updateLine() {
        tvCounter.text = "${position+1}/${fragments.size}"
        vLine.addProgress(position - lastPosition)
        lastPosition = position
    }

    inner class ExpanseProfitPageAdapter(fm: FragmentManager,
                                         private val fragments: MutableList<Fragment>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragments[position].tag
        }
    }
}