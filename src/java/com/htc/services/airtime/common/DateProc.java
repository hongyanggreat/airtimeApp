package com.htc.services.airtime.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

public class DateProc {

    public DateProc() {
    }
    public static String createDate(String typeFormat){
        String timeStamp = new SimpleDateFormat(typeFormat).format(Calendar.getInstance().getTime());
        
        return timeStamp;
    }
    public static String createDDMMYYYY() {
        return Timestamp2DDMMYYYY(createTimestamp());
    }

    public static String createDDMMYYYY_Start01() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(System.currentTimeMillis()));

        String strTemp = "01";
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            return strTemp + "/0" + (calendar.get(Calendar.MONTH) + 1)
                    + "/" + calendar.get(Calendar.YEAR);
        } else {
            return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                    + calendar.get(Calendar.YEAR);
        }
    }

    public static String createHHMMSS() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(System.currentTimeMillis()));

        String strTemp;

        strTemp = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));

        if (strTemp.length() < 2) {
            strTemp = "0" + strTemp;
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            strTemp += "0" + calendar.get(Calendar.MINUTE);
        } else {
            strTemp += calendar.get(Calendar.MINUTE);
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            strTemp += "0" + calendar.get(Calendar.SECOND);
        } else {
            strTemp += calendar.get(Calendar.SECOND);
        }
        return strTemp;
    }

    public static double getTimer() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        double realTime = (double) h + (double) m / 60;
        return realTime;
    }

    public static Timestamp createTimestamp() {
        Calendar calendar = Calendar.getInstance();
        return new Timestamp((calendar.getTime()).getTime());
    }

    public static Timestamp createDateTimestamp(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new Timestamp((calendar.getTime()).getTime());
    }

    public static Timestamp String2Timestamp(String strInputDate) {
        String strDate = strInputDate;
        int i, nYear, nMonth, nDay;
        String strSub = null;
        i = strDate.indexOf("/");
        if (i < 0) {
            return createTimestamp();
        }
        strSub = strDate.substring(0, i);
        nDay = (new Integer(strSub.trim()));
        strDate = strDate.substring(i + 1);
        i = strDate.indexOf("/");
        if (i < 0) {
            return createTimestamp();
        }
        strSub = strDate.substring(0, i);
        nMonth = (new Integer(strSub.trim())) - 1; // Month begin from 0 value
        strDate = strDate.substring(i + 1);
        if (strDate.length() < 4) {
            if (strDate.substring(0, 1).equals("9")) {
                strDate = "19" + strDate.trim();
            } else {
                strDate = "20" + strDate.trim();
            }
        }
        nYear = (new Integer(strDate));
        Calendar calendar = Calendar.getInstance();
        calendar.set(nYear, nMonth, nDay);
        return new Timestamp((calendar.getTime()).getTime());
    }

    public static Timestamp String2Timestamp(String strInputDate, String forMat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(forMat);
            Timestamp date = new Timestamp(dateFormat.parse(strInputDate).getTime());
            return date;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }

    public static Timestamp StringMMDDYYYYHHMMSS2Timestamp(String strInputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            Timestamp date = new Timestamp(dateFormat.parse(strInputDate).getTime());
            return date;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }

    public static Timestamp StringyyyyMMddHHMMSS2Timestamp(String strInputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
            Timestamp date = new Timestamp(dateFormat.parse(strInputDate).getTime());
            return date;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }

    public static String getDateTimeString(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return Timestamp2DDMMYYYY(ts) + " " + Timestamp2HHMMSS(ts, 1);
    }

    /*return date with format: dd/mm/yyyy */
    public static String getDateString(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return Timestamp2DDMMYYYY(ts);
    }

    public static String getTimeString(Timestamp ts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        return calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":"
                + calendar.get(Calendar.SECOND);
    }

    /*return date with format: dd/mm/yyyy */
    public static String Timestamp2DDMMYYYY(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                return strTemp + "/0" + (calendar.get(Calendar.MONTH) + 1)
                        + "/" + calendar.get(Calendar.YEAR);
            } else {
                return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.YEAR);
            }
        }
    }

    public static String getDisplayTimeNews(Timestamp newsTime) {
        try {
            Calendar current = Calendar.getInstance();
            Calendar input = Calendar.getInstance();
            if (newsTime == null) {
                newsTime = DateProc.createTimestamp();
            }
            input.setTime(new java.util.Date(newsTime.getTime()));
            if (input.get(Calendar.YEAR) < current.get(Calendar.YEAR)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (input.get(Calendar.MONTH) < current.get(Calendar.MONTH)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (input.get(Calendar.DAY_OF_MONTH) < current.get(Calendar.DAY_OF_MONTH)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) >= 5) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) > 0 && current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) < 5) {
                return (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY)) + " giờ trước";
            } else {
                return (current.get(Calendar.MINUTE) - input.get(Calendar.MINUTE)) + " phút trước";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getyyyyMMddhh24missNoSpace() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(System.currentTimeMillis()));

        String strTemp = calendar.get(Calendar.YEAR) + "";

        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            strTemp += "0" + (calendar.get(Calendar.MONTH) + 1);
        } else {
            strTemp += "" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            strTemp += "0" + (calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            strTemp += calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            strTemp += "0" + calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            strTemp += calendar.get(Calendar.HOUR_OF_DAY);
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            strTemp += "0" + calendar.get(Calendar.MINUTE);
        } else {
            strTemp += calendar.get(Calendar.MINUTE);
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            strTemp += "0" + calendar.get(Calendar.SECOND);
        } else {
            strTemp += calendar.get(Calendar.SECOND);
        }
        return strTemp;
    }
    public static String getDDMMYYYYHH24MissNoSpace() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(System.currentTimeMillis()));

        String strTemp = calendar.get(Calendar.YEAR) + "";

        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            strTemp = "0" + strTemp;
        } else {
            strTemp += calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            strTemp += "0" + (calendar.get(Calendar.MONTH) + 1);
        } else {
            strTemp += "" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            strTemp += "0" + calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            strTemp += calendar.get(Calendar.HOUR_OF_DAY);
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            strTemp += "0" + calendar.get(Calendar.MINUTE);
        } else {
            strTemp += calendar.get(Calendar.MINUTE);
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            strTemp += "0" + calendar.get(Calendar.SECOND);
        } else {
            strTemp += calendar.get(Calendar.SECOND);
        }
        return strTemp;
    }

    public static String Timestamp2DDMMYYYYHH24MiSS(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                strTemp += "/0" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            } else {
                strTemp += "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            }
            strTemp += " ";
            if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
                strTemp += "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":";
            } else {
                strTemp += calendar.get(Calendar.HOUR_OF_DAY) + ":";
            }
            if (calendar.get(Calendar.MINUTE) < 10) {
                strTemp += "0" + calendar.get(Calendar.MINUTE) + ":";
            } else {
                strTemp += calendar.get(Calendar.MINUTE) + ":";
            }
            if (calendar.get(Calendar.SECOND) < 10) {
                strTemp += "0" + calendar.get(Calendar.SECOND);
            } else {
                strTemp += calendar.get(Calendar.SECOND);
            }
            return strTemp;

        }
    }

    public static String Timestamp2DDMMYYYYHH24Mi(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                strTemp += "/0" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            } else {
                strTemp += "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            }
            strTemp += " ";
            if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
                strTemp += "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":";
            } else {
                strTemp += calendar.get(Calendar.HOUR_OF_DAY) + ":";
            }
            if (calendar.get(Calendar.MINUTE) < 10) {
                strTemp += "0" + calendar.get(Calendar.MINUTE);
            } else {
                strTemp += calendar.get(Calendar.MINUTE);
            }
            return strTemp;

        }
    }

    /*return date with format: mm/dd/yyyy */
    public static String Timestamp2MMDDYYYY(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                return "0" + (calendar.get(Calendar.MONTH) + 1) + "/" + strTemp
                        + "/" + calendar.get(Calendar.YEAR);
            } else {
                return (calendar.get(Calendar.MONTH) + 1) + "/" + strTemp + "/"
                        + +calendar.get(Calendar.YEAR);
            }
        }
    }

    /*return date with format: dd/mm/yy */
    public static String Timestamp2DDMMYY(Timestamp ts) {
        int endYear;
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            endYear = calendar.get(Calendar.YEAR) % 100;
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                if (endYear < 10) {
                    return strTemp + "/0" + (calendar.get(Calendar.MONTH) + 1)
                            + "/0" + endYear;
                } else {
                    return strTemp + "/0" + (calendar.get(Calendar.MONTH) + 1)
                            + "/" + endYear;
                }
            } else {
                if (endYear < 10) {
                    return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1)
                            + "/0" + endYear;
                } else {
                    return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1)
                            + "/" + endYear;
                }
            }
        }
    }

    /*return date with format: d/m/yy */
    public static String Timestamp2DMYY(Timestamp ts) {
        int endYear = 0;
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));
            endYear = calendar.get(Calendar.YEAR) % 100;
            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (endYear < 10) {
                return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1)
                        + "/0" + endYear;
            } else {
                return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + endYear;
            }
        }
    }

    /*return date with format: d/m/yyyy */
    public static String Timestamp2DMYYYY(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            return strTemp + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                    + calendar.get(Calendar.YEAR);
        }
    }

    /*return date with format: YYYY/MM/DD to sort*/
    public static String Timestamp2YYYYMMDD(Timestamp ts) {
        int endYear;
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            endYear = calendar.get(Calendar.YEAR);
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                return endYear + "/0" + (calendar.get(Calendar.MONTH) + 1)
                        + "/" + strTemp;
            } else {
                return endYear + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + strTemp;
            }
        }
    }

    /**
     * Author: toantt
     *
     * @param ts Timestapm to convert
     * @param iStyle 0: 24h, otherwise 12h clock
     * @return
     */
    public static String Timestamp2HHMMSS(Timestamp ts, int iStyle) {
        if (ts == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));

        String strTemp;
        if (iStyle == 0) {
            strTemp = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        } else {
            strTemp = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        }

        if (strTemp.length() < 2) {
            strTemp = "0" + strTemp;
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            strTemp += ":0" + calendar.get(Calendar.MINUTE);
        } else {
            strTemp += ":" + calendar.get(Calendar.MINUTE);
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            strTemp += ":0" + calendar.get(Calendar.SECOND);
        } else {
            strTemp += ":" + calendar.get(Calendar.SECOND);
        }

        if (iStyle != 0) {
            if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                strTemp += " AM";
            } else {
                strTemp += " PM";
            }
        }
        return strTemp;
    }

    /**
     * return date time used for 24 hour clock
     */
    public static String getDateTime24hString(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return Timestamp2DDMMYYYY(ts) + " " + Timestamp2HHMMSS(ts, 0);
    }

    /**
     * return date time used for 12 hour clock
     */
    public static String getDateTime12hString(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return Timestamp2DDMMYYYY(ts) + " " + Timestamp2HHMMSS(ts, 1);
    }

    /**
     * return string dd/mm/yyyy from a Timestamp + a addtional day
     *
     * @param ts
     * @param iDayPlus number of day to add
     * @return
     */
    public static String TimestampPlusDay2DDMMYYYY(Timestamp ts,
            int iDayPlus) {
        if (ts == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay + iDayPlus);

        Timestamp tsNew = new Timestamp((calendar.getTime()).getTime());
        return Timestamp2DDMMYYYY(tsNew);
    }

    public static Timestamp getPreviousDate(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay - 1);

        Timestamp tsNew = new Timestamp((calendar.getTime()).getTime());
        return tsNew;
    }

    public static Timestamp getNextDate(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay + 1);

        Timestamp tsNew = new Timestamp((calendar.getTime()).getTime());
        return tsNew;
    }

    public static int getDayOfWeek(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_WEEK);
        return iDay;
    }

    public static int getDay(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        return iDay;
    }

    public static int getMonth(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iMonth = calendar.get(Calendar.MONTH);
        return iMonth + 1;
    }

    public static int getYear(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iYear = calendar.get(Calendar.YEAR);
        return iYear;
    }

    public static int getHour(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iHour = calendar.get(Calendar.HOUR);
        return iHour;
    }

    public static int getMinute(Timestamp ts) {
        if (ts == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iMinute = calendar.get(Calendar.MINUTE);
        return iMinute;
    }

    public static Timestamp getTimeInDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm");
            Timestamp date = new Timestamp(dateFormat.parse(dateStr,
                    new ParsePosition(0)).getTime());
            return date;
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean compareDate(Timestamp timeComp) {
        if (timeComp == null) {
            return false;
        }
        System.out.println("time stamp: " + timeComp);
        boolean result = true;
        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new java.util.Date(timeComp.getTime()));

        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        int currMonth = cal.get(Calendar.MONTH) + 1;
        int currYear = cal.get(Calendar.YEAR);
        int currHour = cal.get(Calendar.HOUR_OF_DAY);
        int currMinute = cal.get(Calendar.MINUTE);

        int creDay = cal1.get(Calendar.DAY_OF_MONTH);
        int creMonth = cal1.get(Calendar.MONTH) + 1;
        int creYear = cal1.get(Calendar.YEAR);
        int creHour = cal1.get(Calendar.HOUR_OF_DAY);
        int creMinute = cal1.get(Calendar.MINUTE);

        if (creYear < currYear) {
            result = false;
        } else if (creYear == currYear && creMonth < currMonth) {
            result = false;
        } else if (creYear == currYear && creMonth == currMonth
                && creDay < currDay) {
            result = false;
        } else if (creYear == currYear && creMonth == currMonth
                && creDay == currDay && creHour < currHour) {
            result = false;
        } else if (creYear == currYear && creMonth == currMonth
                && creDay == currDay && creHour == currHour
                && creMinute < currMinute) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * return the dd/mm/yyyy of current month eg: 05/2002 --> 31/05/2002
     *
     * @param strMonthYear : input string mm/yyyy
     * @return
     */
    public static String getLastestDateOfMonth(String strMonthYear) {
        String strDate = strMonthYear;
        int i, nYear, nMonth, nDay;
        String strSub = null;

        i = strDate.indexOf("/");
        if (i < 0) {
            return "";
        }
        strSub = strDate.substring(0, i);
        nMonth = (new Integer(strSub)).intValue(); // Month begin from 0 value
        strDate = strDate.substring(i + 1);
        nYear = (new Integer(strDate)).intValue();

        boolean leapyear = false;
        if (nYear % 100 == 0) {
            if (nYear % 400 == 0) {
                leapyear = true;
            }
        } else if ((nYear % 4) == 0) {
            leapyear = true;
        }

        if (nMonth == 2) {
            if (leapyear) {
                return "29/" + strDate;
            } else {
                return "28/" + strDate;
            }
        } else {
            if ((nMonth == 1) || (nMonth == 3) || (nMonth == 5) || (nMonth == 7)
                    || (nMonth == 8) || (nMonth == 10) || (nMonth == 12)) {
                return "31/" + strDate;
            } else if ((nMonth == 4) || (nMonth == 6) || (nMonth == 9)
                    || (nMonth == 11)) {
                return "30/" + strDate;
            }
        }
        return "";
    }

    public static Timestamp getFriday(Timestamp ts) {
        if (ts == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        int iDoW = getDayOfWeek(ts);
        if (iDoW == Calendar.SUNDAY) {
            iDoW = 8;
        }
        int k = Calendar.FRIDAY - iDoW;
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay + k);
        Timestamp tsNew = new Timestamp((calendar.getTime()).getTime());
        return tsNew;
    }

    public static boolean isFriday(Timestamp ts) {
        if (ts == null) {
            return false;
        }
        if (getDayOfWeek(ts) == Calendar.FRIDAY) {
            return true;
        }
        return false;
    }

    public static Timestamp getTimestamp(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy kk:mm:ss");
            Timestamp date = new Timestamp(dateFormat.parse(dateStr).getTime());
            return date;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }

    public static Timestamp getNextDateN(Timestamp ts, int n) {
        if (ts == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Timestamp tsNew = new Timestamp((calendar.getTime()).getTime());
        return tsNew;
    }

    public static String getTimeStringFull(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));

            String strTemp = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                strTemp = "0" + strTemp;
            }
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                strTemp = strTemp + "/0" + (calendar.get(Calendar.MONTH) + 1)
                        + "/" + calendar.get(Calendar.YEAR);
            } else {
                strTemp = strTemp + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.YEAR);
            }
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            int m = calendar.get(Calendar.MINUTE);
            if (h < 10) {
                strTemp += " 0" + h;
            } else {
                strTemp += " " + h;
            }
            if (m < 10) {
                strTemp += ":0" + m;
            } else {
                strTemp += ":" + m;
            }

            return strTemp;
        }
    }

    public static String getDateTimeForName() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Timestamp ts = new Timestamp((calendar.getTime()).getTime());
        String str = ts.toString();
        str = str.replaceAll("-", "");
        str = str.replaceAll(":", "");
        str = str.replaceAll(" ", "");
        str = str.replaceAll("\\.", "");
        return str;
    }

    public static String extractInfo(Timestamp date) {
        String dayOfWeek = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = cal.get(7);
        switch (d) {
            case 2: // '\002'
                dayOfWeek = "Thứ Hai";
                break;

            case 3: // '\003'
                dayOfWeek = "Thứ Ba";
                break;

            case 4: // '\004'
                dayOfWeek = "Thứ Tư";
                break;

            case 5: // '\005'
                dayOfWeek = "Thứ Năm";
                break;

            case 6: // '\006'
                dayOfWeek = "Thứ Sáu";
                break;

            case 7: // '\007'
                dayOfWeek = "Thứ Bẩy";
                break;

            case 1: // '\001'
                dayOfWeek = "Chủ Nhật";
                break;
            default:
                dayOfWeek = "";
                break;
        }
        return dayOfWeek;
    }

    public static String getCurrentTimestampString() {
        Timestamp timestamp;
        timestamp = new Timestamp(new Date().getTime());
        int hour = timestamp.getHours();
        String a = (hour < 12) ? " AM" : " PM";
        return timestamp.toString() + a;
    }

    public static Timestamp getCurrentTimestamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return timestamp;
    }

//    public static void main(String[] args) {
//        DateProc date = new DateProc();
//                System.out.println(date.getCurrentTimestampString());
//    }
}
