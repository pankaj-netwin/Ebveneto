package com.ebveneto.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mayur on 27-10-2017.
 */

public class Versameti implements Parcelable {
    private String OCT;

    private String AzNome;

    private String DEC;

    private String JAN;

    private String SEP;

    private String AUG;

    private String ebv_versamenti;

    private String INPS;

    private String MAR;

    private String idAZ;

    private String NOV;

    private String APR;

    private String MAY;

    private String JUL;

    private String JUN;

    private String FEB;

    private String AnnoCompetenza;

    private String cf;

    private String piva;

    public Versameti()
    {
    }

    public String getOCT ()
    {
        return OCT;
    }

    public void setOCT (String OCT)
    {
        this.OCT = OCT;
    }

    public String getAzNome ()
    {
        return AzNome;
    }

    public void setAzNome (String AzNome)
    {
        this.AzNome = AzNome;
    }

    public String getDEC ()
    {
        return DEC;
    }

    public void setDEC (String DEC)
    {
        this.DEC = DEC;
    }

    public String getJAN ()
    {
        return JAN;
    }

    public void setJAN (String JAN)
    {
        this.JAN = JAN;
    }

    public String getSEP ()
    {
        return SEP;
    }

    public void setSEP (String SEP)
    {
        this.SEP = SEP;
    }

    public String getAUG ()
    {
        return AUG;
    }

    public void setAUG (String AUG)
    {
        this.AUG = AUG;
    }

    public String getEbv_versamenti ()
    {
        return ebv_versamenti;
    }

    public void setEbv_versamenti (String ebv_versamenti)
    {
        this.ebv_versamenti = ebv_versamenti;
    }

    public String getINPS ()
    {
        return INPS;
    }

    public void setINPS (String INPS)
    {
        this.INPS = INPS;
    }

    public String getMAR ()
    {
        return MAR;
    }

    public void setMAR (String MAR)
    {
        this.MAR = MAR;
    }

    public String getIdAZ ()
    {
        return idAZ;
    }

    public void setIdAZ (String idAZ)
    {
        this.idAZ = idAZ;
    }

    public String getNOV ()
    {
        return NOV;
    }

    public void setNOV (String NOV)
    {
        this.NOV = NOV;
    }

    public String getAPR ()
    {
        return APR;
    }

    public void setAPR (String APR)
    {
        this.APR = APR;
    }

    public String getMAY ()
    {
        return MAY;
    }

    public void setMAY (String MAY)
    {
        this.MAY = MAY;
    }

    public String getJUL ()
    {
        return JUL;
    }

    public void setJUL (String JUL)
    {
        this.JUL = JUL;
    }

    public String getJUN ()
    {
        return JUN;
    }

    public void setJUN (String JUN)
    {
        this.JUN = JUN;
    }

    public String getFEB ()
    {
        return FEB;
    }

    public void setFEB (String FEB)
    {
        this.FEB = FEB;
    }

    public String getAnnoCompetenza ()
    {
        return AnnoCompetenza;
    }

    public void setAnnoCompetenza (String AnnoCompetenza)
    {
        this.AnnoCompetenza = AnnoCompetenza;
    }

    public String getCf ()
    {
        return cf;
    }

    public void setCf (String cf)
    {
        this.cf = cf;
    }

    public String getPiva ()
    {
        return piva;
    }

    public void setPiva (String piva)
    {
        this.piva = piva;
    }



    protected Versameti(Parcel in) {
        OCT = in.readString();
        AzNome = in.readString();
        DEC = in.readString();
        JAN = in.readString();
        SEP = in.readString();
        AUG = in.readString();
        ebv_versamenti = in.readString();
        INPS = in.readString();
        MAR = in.readString();
        idAZ = in.readString();
        NOV = in.readString();
        APR = in.readString();
        MAY = in.readString();
        JUL = in.readString();
        JUN = in.readString();
        FEB = in.readString();
        AnnoCompetenza = in.readString();
        cf = in.readString();
        piva = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OCT);
        dest.writeString(AzNome);
        dest.writeString(DEC);
        dest.writeString(JAN);
        dest.writeString(SEP);
        dest.writeString(AUG);
        dest.writeString(ebv_versamenti);
        dest.writeString(INPS);
        dest.writeString(MAR);
        dest.writeString(idAZ);
        dest.writeString(NOV);
        dest.writeString(APR);
        dest.writeString(MAY);
        dest.writeString(JUL);
        dest.writeString(JUN);
        dest.writeString(FEB);
        dest.writeString(AnnoCompetenza);
        dest.writeString(cf);
        dest.writeString(piva);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Versameti> CREATOR = new Parcelable.Creator<Versameti>() {
        @Override
        public Versameti createFromParcel(Parcel in) {
            return new Versameti(in);
        }

        @Override
        public Versameti[] newArray(int size) {
            return new Versameti[size];
        }
    };
}