package com.ebveneto.models;

/**
 * Created by Bharat on 25-10-2017.
 */
public class MenuHome
{
    String title;
    int imageRes;
    boolean isSelected;
    int bgColor;
    int textColor;

    public MenuHome(String title, int imageRes, boolean isSelected)
    {
        this.title = title;
        this.imageRes = imageRes;
        this.isSelected = isSelected;
    }

    public MenuHome(String title, int imageRes, boolean isSelected, int bgColor, int textColor)
    {
        this.title = title;
        this.imageRes = imageRes;
        this.isSelected = isSelected;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }

    public int getBgColor()
    {
        return bgColor;
    }

    public void setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }



    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getImageRes()
    {
        return imageRes;
    }

    public void setImageRes(int imageRes)
    {
        this.imageRes = imageRes;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
}
