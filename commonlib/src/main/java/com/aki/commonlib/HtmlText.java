package com.aki.commonlib;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public class HtmlText
{
    public static Spanned from(String html)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }
        else
        {
            return Html.fromHtml(html);
        }
    }
}