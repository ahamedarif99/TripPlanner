package sample;

import java.util.Comparator;

public class Stops implements Comparable
{
    String cityname, statename;
    int latdeg, latmin, longdeg, longmin;

    public Stops(String cityname, String statename, int latdeg, int latmin, int longdeg, int longmin)
    {
        this.cityname = cityname;
        this.statename = statename;
        this.latdeg = latdeg;
        this.latmin = latmin;
        this.longdeg = longdeg;
        this.longmin = longmin;
    }

    public int getLatdeg()
    {
        return latdeg;
    }

    public void setLatdeg(int latdeg)
    {
        this.latdeg = latdeg;
    }

    public int getLatmin()
    {
        return latmin;
    }

    public void setLatmin(int latmin)
    {
        this.latmin = latmin;
    }

    public String getStatename()
    {
        return statename;
    }

    public void setStatename(String statename)
    {
        this.statename = statename;
    }

    public int getLongdeg()
    {
        return longdeg;
    }

    public void setLongdeg(int longdeg)
    {
        this.longdeg = longdeg;
    }

    public int getLongmin()
    {
        return longmin;
    }

    public void setLongmin(int longmin)
    {
        this.longmin = longmin;
    }

    public String getCityname()
    {
        return cityname;
    }

    public void setCityname(String cityname)
    {
        this.cityname = cityname;
    }

    public int compareTo(Object obj) {
        Stops s = (Stops) obj;
        return cityname.compareToIgnoreCase(s.cityname);
    }

    public String toString()
    {
        return this.getCityname() +", " + this.statename;
    }

//    public static void main(String args[])
//    {
//        System.out.println(Math.pow(3,2));
//    }

}
