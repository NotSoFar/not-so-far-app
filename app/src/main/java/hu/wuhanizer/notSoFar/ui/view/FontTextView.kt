package hu.wuhanizer.notSoFar.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class FontTextView : AppCompatTextView {
    var ttfName: String? = null

    constructor(
        context: Context?,
        attrs: AttributeSet
    ) : super(context, attrs) {
        if (!this.isInEditMode) {
            ttfName =
                attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "customFont")
            if (ttfName == null) {
                ttfName = DEFAULT_FONT
            }
            init()
        }
    }

    constructor(context: Context?, font: String?) : super(context) {
        ttfName = font
        init()
    }

    private fun init() {
        typeface = getFont(context, ttfName)!!
    }

    override fun setTypeface(tf: Typeface) {
        super.setTypeface(tf)
    }

    fun setFont(fontName: String?) {
        ttfName = fontName
        init()
    }

    companion object {
        private const val DEFAULT_FONT = "OMNESBOLD-ROMAN.OTF"
        var fontCache =
            HashMap<String?, Typeface?>(4)

        fun getFont(context: Context, fontName: String?): Typeface? {
            var font = fontCache[fontName]
            if (font == null) {
                font = Typeface.createFromAsset(context.assets, fontName)
                fontCache[fontName] = font
            }
            return font
        }
    }
}