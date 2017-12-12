package util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016-05-01.
 */

public class SolarCalendarTool {

    public String strWeekDay = "";
    public String strMonth = "";

    int date;
    int month;
    int year;

    public SolarCalendarTool() {
        Calendar MiladiDate = new GregorianCalendar();
        calcSolarCalendar(MiladiDate);
    }

    public SolarCalendarTool(Calendar MiladiDate) {
        calcSolarCalendar(MiladiDate);
    }

    private void calcSolarCalendar(Calendar MiladiDate) {

        int ld;

        int miladiYear = MiladiDate.get(Calendar.YEAR); //MiladiDate.getYear() + 1900;//This method was deprecated in API level 1. Use Calendar.get(Calendar.YEAR) - 1900 instead.
        int miladiMonth = MiladiDate.get(Calendar.MONTH) + 1; //MiladiDate.getMonth() + 1;//This method was deprecated in API level 1. Use Calendar.get(Calendar.MONTH) instead.
        int miladiDate = MiladiDate.get(Calendar.DATE);//MiladiDate.getTitle();This method was deprecated in API level 1. Use Calendar.get(Calendar.DATE) instead.
        int WeekDay = MiladiDate.get(Calendar.DATE); //MiladiDate.getDay();This method was deprecated in API level 1. Use Calendar.get(Calendar.DATE) instead.


//            Date d = new Date();
//            Log.i("Date", "************\nDate: " + d.getMonth() + " Cal: " + miladiMonth + "\n***************");
//            int miladiYear = Calendar.get + 1900;
//            int miladiMonth = MiladiDate.getMonth() + 1;
//            int miladiDate = MiladiDate.getTitle();
//            int WeekDay = MiladiDate.getDay();

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((miladiYear % 4) != 0) {
            date = buf1[miladiMonth - 1] + miladiDate;

            if (date > 79) {
                date = date - 79;
                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = date / 31;
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                date = date + ld;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }
        } else {
            date = buf2[miladiMonth - 1] + miladiDate;

            if (miladiYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date > ld) {
                date = date - ld;

                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = (date / 31);
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                date = date + 10;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }

        }

        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }

        switch (WeekDay) {

            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }

    }


    public String getCurrentShamsidate() {
        Locale loc = new Locale("en_US");
        SolarCalendarTool sc = new SolarCalendarTool();
        return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }

    /**
    ۱۴ ارديبهشت ۱۳۹۵*/
    public String getShamsiDateFormat1() {
        Locale loc = new Locale("fa");
        return String.format(loc, "%02d", this.date) + " " + this.strMonth
                + " " + String.format(loc, "%04d", this.year);
    }

    /**
     * ۱۳۹۵/۰۲/۱۴
     */
    public String getShamsiDateFormat2() {
        Locale loc = new Locale("fa");
        return String.format(loc, "%04d", this.year) + "/" + String.format(loc, "%02d",
                this.month) + "/" + String.format(loc, "%02d", this.date);
    }

    /**
     * چهارشنبه ۱۴ ارديبهشت ۱۳۹۵
     */
    public String getShamsiDateFormat3() {
        Locale loc = new Locale("fa");
        return this.strWeekDay + " " + String.format(loc, "%02d", this.date) + " " + this.strMonth
                + " " + String.format(loc, "%04d", this.year);
    }


}